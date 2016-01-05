package ch.zhaw.iwi.cis.pews.service.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.AuthenticationTokenService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationResultExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2POneExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PosterExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.SimplyPrototypingExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.You2MeExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopServiceImpl extends WorkflowElementServiceImpl implements WorkshopService
{
	private WorkshopDao workshopDao;
	private ExerciseDataService exerciseDataService;
	private SessionService sessionService;
	private WorkshopTemplateService workshopTemplateService;
	private ExerciseService exerciseService;
	private ExerciseTemplateService exerciseTemplateService;
	private AuthenticationTokenService authenticationTokenService;

	private static final Map< String, Class< ? extends ExerciseImpl > > EXERCISETEMPLATESUBCLASSESMAP = new HashMap< String, Class< ? extends ExerciseImpl >>();

	static
	{
		EXERCISETEMPLATESUBCLASSESMAP.put( PinkLabsTemplate.class.getSimpleName(), PinkLabsExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( You2MeTemplate.class.getSimpleName(), You2MeExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( P2POneTemplate.class.getSimpleName(), P2POneExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( P2PTwoTemplate.class.getSimpleName(), P2PTwoExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( SimplyPrototypingTemplate.class.getSimpleName(), SimplyPrototypingExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( XinixTemplate.class.getSimpleName(), XinixExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( CompressionTemplate.class.getSimpleName(), CompressionExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( EvaluationTemplate.class.getSimpleName(), EvaluationExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( PosterTemplate.class.getSimpleName(), PosterExercise.class );
		EXERCISETEMPLATESUBCLASSESMAP.put( EvaluationResultTemplate.class.getSimpleName(), EvaluationResultExercise.class );
	}

	private Class< ? > getExerciseSubClass( String exerciseTemplateClassName )
	{
		return EXERCISETEMPLATESUBCLASSESMAP.get( exerciseTemplateClassName );
	}

	public WorkshopServiceImpl()
	{
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
		exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		sessionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		workshopTemplateService = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopTemplateServiceImpl.class.getSimpleName() );
		exerciseService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		exerciseTemplateService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
		authenticationTokenService = ZhawEngine.getManagedObjectRegistry().getManagedObject( AuthenticationTokenServiceImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return workshopDao;
	}

	@Override
	public void stop( String id )
	{
		super.stop( id );
		// remove authentication tokens on all sessions
		this.removeAuthenticationTokens( id );
	}

	@Override
	public void renew( String id )
	{
		super.renew( id );
		// remove authentication tokens on all sessions
		this.removeAuthenticationTokens( id );
	}

	private void removeAuthenticationTokens( String workshopID )
	{
		WorkshopImpl workshop = findWorkshopByID( workshopID );
		for ( SessionImpl session : workshop.getSessions() )
		{
			authenticationTokenService.removeBySessionID( session.getID() );
		}
	}

	// TODO: find more elegant way to remove duplicates caused by sql query than putting through HashMap
	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopImpl > findAllWorkshopsSimple()
	{
		List< WorkshopImpl > workshops = workshopDao.findByAllSimple( UserContext.getCurrentUser().getClient().getID() );

		( (EntityManager)ZhawEngine.getManagedObjectRegistry().getManagedObject( "pews" ) ).clear();
		for ( WorkshopImpl ws : workshops )
		{
			ws.setExercises( new ArrayList< ExerciseImpl >( new HashSet< ExerciseImpl >( ws.getExercises() ) ) );
		}

		return (List< WorkshopImpl >)simplifyOwnerInObjectGraph( workshops );
	}

	// TODO: find more elegant way to remove duplicates caused by sql query than putting through HashMap
	@Override
	public WorkshopImpl findWorkshopByID( String id )
	{
		WorkshopImpl ws = workshopDao.findWorkshopByID( id );

		( (EntityManager)ZhawEngine.getManagedObjectRegistry().getManagedObject( "pews" ) ).clear();
		ws.setExercises( new ArrayList< ExerciseImpl >( new HashSet< ExerciseImpl >( ws.getExercises() ) ) );

		return (WorkshopImpl)simplifyOwnerInObjectGraph( ws );
	}

	@Override
	public void reset( String workshopID )
	{
		WorkshopImpl ws = workshopDao.findById( workshopID );
		List< ExerciseImpl > exercises = ws.getExercises();
		Collections.reverse( exercises );
		for ( ExerciseImpl ex : exercises )
		{
			for ( ExerciseDataImpl data : exerciseDataService.findByExerciseID( ex.getID() ) )
			{
				exerciseDataService.remove( data );
			}
		}

		for ( SessionImpl session : ws.getSessions() )
		{
			session.setCurrentExercise( ws.getExercises().get( 0 ) );
			sessionService.setCurrentExercise( session );
			// remove authentication tokens
			authenticationTokenService.removeBySessionID( session.getID() );
		}
	}

	@Override
	public String generateFromTemplate( WorkshopImpl obj )
	{
		// object to be persisted, init with null
		PinkElefantWorkshop workshop = null;
		boolean templateIsValid = false;
		PinkElefantTemplate template = null;

		// check if obj in request references an existing workshop template and utilize accordingly
		// if workshop to be based on existing template, use template's values
		// else use values given in obj
		if ( obj.getDerivedFrom() != null )
		{
			template = workshopTemplateService.findByID( obj.getDerivedFrom().getID() );

			// check if referenced template actually exists
			if ( template != null )
			{
				templateIsValid = true;
			}
		}

		// if template is valid make workshop instance based on template / derivedFrom
		// else take information contained in obj
		if ( templateIsValid )
		{
			workshop = new PinkElefantWorkshop( obj.getName(), obj.getPosterDescription(), (PinkElefantTemplate)template );
		}
		else
		{
			workshop = new PinkElefantWorkshop();
			workshop.setName( obj.getName() );
			workshop.setPosterDescription( obj.getPosterDescription() );
			workshop.setEmailText( ( (PinkElefantTemplate)obj.getDerivedFrom() ).getDefaultEmailText() );
			workshop.setProblem( ( (PinkElefantTemplate)obj.getDerivedFrom() ).getProblem() );
			workshop.setDerivedFrom( null );
		}

		// persist operation
		WorkshopImpl persistedWorkshop = findByID( persist( workshop ) );

		// handle creation of exercises based on exercise templates which are
		// attached to the workshop template (derivedFrom of persistedWorkshop)
		// only do this, of course, if derivedFrom is not null
		if ( persistedWorkshop.getDerivedFrom() != null )
		{
			for ( ExerciseTemplate exerciseTemplate : ( (WorkshopTemplate)persistedWorkshop.getDerivedFrom() ).getExerciseTemplates() )
			{
				Constructor< ? > constructor = null;
				Constructor< ? >[] constructors = getExerciseSubClass( exerciseTemplate.getClass().getSimpleName() ).getConstructors();

				for ( int i = 0; i < constructors.length; i++ )
				{
					if ( constructors[ i ].getParameterTypes().length > 0 )
					{
						constructor = constructors[ i ];
					}
				}

				try
				{
					exerciseService.generateFromTemplate( (ExerciseImpl)constructor.newInstance(
						"generated for workshop " + persistedWorkshop.getID(),
						"generated from template " + exerciseTemplate.getID(),
						exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() ),
						persistedWorkshop ) );
				}
				catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e )
				{
					throw new RuntimeException( e );
				}
			}
		}

		return persistedWorkshop.getID();
	}

	@Override
	public void updateOrderOfExercises( WorkshopImpl wrapper )
	{
		for ( int i = 0; i < wrapper.getExercises().size(); i++ )
		{
			ExerciseImpl exercise = exerciseService.findExerciseByID( wrapper.getExercises().get( i ).getID() );
			exercise.setOrderInWorkshop( i );
			exerciseService.persistExercise( exercise );
		}
	}

	@Override
	public void updateBasicInformation( WorkshopImpl workshop )
	{
		PinkElefantWorkshop mergeable = (PinkElefantWorkshop)workshopDao.findWorkshopByIDForBasicUpdate( workshop.getID() );
		mergeable.setPosterDescription( workshop.getPosterDescription() );
		mergeable.setName( workshop.getName() );
		mergeable.setEmailText( ( (PinkElefantWorkshop)workshop ).getEmailText() );
		mergeable.setProblem( ( (PinkElefantWorkshop)workshop ).getProblem() );

		persist( mergeable );
	}
}
