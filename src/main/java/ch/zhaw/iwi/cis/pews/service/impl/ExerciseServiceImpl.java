package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.ParticipantDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.ParticipantDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.CompressionExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.EvaluationExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.EvaluationResultExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.P2POneExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.P2PTwoExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.PinkLabsExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.PosterExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.SimplePrototypingExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.XinixExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.You2MeExerciseService;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseServiceImpl extends WorkflowElementServiceImpl implements ExerciseService
{
	private ObjectMapper objectMapper;
	private ExerciseDataService exerciseDataService;
	private ExerciseTemplateService exerciseTemplateService;

	private ExerciseDao exerciseDao;
	private ExerciseDataDao exerciseDataDao;
	private ParticipantDao participantDao;
	private WorkshopDao workshopDao;
	private static final Map< Class< ? extends ExerciseImpl >, Class< ? extends ExerciseServiceImpl > > EXERCISESPECIFICSERVICES = new HashMap< Class< ? extends ExerciseImpl >, Class< ? extends ExerciseServiceImpl >>();

	// TODO move this from manual entries to an automatic solution, preferably using annotation @ExerciseSpecificService (which is already implemented)
	static
	{
		EXERCISESPECIFICSERVICES.put( PinkLabsExercise.class, PinkLabsExerciseService.class );
		EXERCISESPECIFICSERVICES.put( You2MeExercise.class, You2MeExerciseService.class );
		EXERCISESPECIFICSERVICES.put( P2POneExercise.class, P2POneExerciseService.class );
		EXERCISESPECIFICSERVICES.put( P2PTwoExercise.class, P2PTwoExerciseService.class );
		EXERCISESPECIFICSERVICES.put( SimplyPrototypingExercise.class, SimplePrototypingExerciseService.class );
		EXERCISESPECIFICSERVICES.put( XinixExercise.class, XinixExerciseService.class );
		EXERCISESPECIFICSERVICES.put( CompressionExercise.class, CompressionExerciseService.class );
		EXERCISESPECIFICSERVICES.put( EvaluationExercise.class, EvaluationExerciseService.class );
		EXERCISESPECIFICSERVICES.put( PosterExercise.class, PosterExerciseService.class );
		EXERCISESPECIFICSERVICES.put( EvaluationResultExercise.class, EvaluationResultExerciseService.class );
	}

	private Class< ? > getExerciseSpecificService( Class< ? extends ExerciseImpl > exerciseClass )
	{
		return EXERCISESPECIFICSERVICES.get( exerciseClass );
	}

	public ExerciseServiceImpl()
	{
		exerciseDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDaoImpl.class.getSimpleName() );
		exerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataDaoImpl.class.getSimpleName() );
		participantDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ParticipantDaoImpl.class.getSimpleName() );
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
		objectMapper = new ObjectMapper();
		exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		exerciseTemplateService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
	}

	public ObjectMapper getObjectMapper()
	{
		return objectMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.iwi.cis.pews.service.impl.WorkshopObjectServiceImpl#persist(ch.zhaw.iwi.cis.pews.model.WorkshopObject)
	 * 
	 * specialized persist method for handling orderInWorkshop correctly.
	 */
	@Override
	public String persistExercise( ExerciseImpl exercise )
	{
		WorkshopImpl workshop = workshopDao.findById( exercise.getWorkshop().getID() );
		List< String > workshopExerciseIDs = new ArrayList< String >();
		for ( ExerciseImpl e : workshop.getExercises() )
		{
			workshopExerciseIDs.add( e.getID() );
		}

		// if no argument for orderInWorkshop provided (i.e. null),
		// place at the end of workshop's exercise queue
		// else handle order of other exercises
		if ( exercise.getOrderInWorkshop() == null )
		{
			// place at end of queue
			exercise.setOrderInWorkshop( workshop.getExercises().size() );

			// special case: if persisting / updating existing exercise and no
			// argument for orderInWorkshop provided, exercise keeps existing orderInWorkshop
			ExerciseImpl check = exerciseDao.findById( exercise.getID() );
			if ( null != check )
			{
				exercise.setOrderInWorkshop( check.getOrderInWorkshop() );
			}
		}
		else
		{
			// handle order of exercises
			for ( int i = exercise.getOrderInWorkshop(); i < workshopExerciseIDs.size(); i++ )
			{
				ExerciseImpl ex = exerciseDao.findById( workshopExerciseIDs.get( i ) );
				ex.setOrderInWorkshop( i + 1 );
				super.persist( ex );
			}
		}

		return super.persist( exercise );
	}

	@Override
	public String generateFromTemplate( ExerciseImpl obj )
	{
		// TODO: discuss with John how to use deflector in this case
		try
		{
			// check if obj in request references an existing exercise template and utilize accordingly
			ExerciseTemplate template = exerciseTemplateService.findByID( obj.getDerivedFrom().getID() );
			if ( template != null )
			{
				obj.setDerivedFrom( template );
			}

			// make exercise instance based on template / derivedFrom
			// Constructor< ? > constructor = ClassWrapper.getConstructor( obj.getClass(), new Class[] { String.class, String.class, ExerciseImpl.class, WorkshopImpl.class } );
			Constructor< ? > constructor = null;
			Constructor< ? >[] constructors = obj.getClass().getConstructors();
			for ( int i = 0; i < constructors.length; i++ )
			{
				if ( constructors[ i ].getParameterTypes().length > 0 )
				{
					constructor = constructors[ i ];
				}
			}

			ExerciseImpl exercise = (ExerciseImpl)constructor.newInstance( obj.getName(), obj.getDescription(), obj.getDerivedFrom(), obj.getWorkshop() );

			// if template not null, use default name and description from template
			if ( template != null )
			{
				exercise.setName( template.getDefaultName() );
				exercise.setDescription( template.getDefaultDescription() );
			}

			return persistExercise( exercise );
		}
		catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e )
		{
			throw new RuntimeException( e );
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseImpl > findAllExercises()
	{
		return (List< ExerciseImpl >)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override
	public ExerciseImpl findExerciseByID( String id )
	{
		return (ExerciseImpl)simplifyOwnerInObjectGraph( findByID( id ) );
	}

	@Override
	public void suspend( SuspensionRequest suspensionRequest )
	{
		ExerciseImpl exercise = findByID( suspensionRequest.getSuspendedElementid() );
		exercise.setCurrentState( WorkflowElementStatusImpl.SUSPENDED );

		exercise.setElapsedSeconds( suspensionRequest.getElapsedSeconds() );
		persist( exercise );
	}

	@Override
	public double resume( String exerciseID )
	{
		ExerciseImpl exercise = findByID( exerciseID );
		exercise.setCurrentState( WorkflowElementStatusImpl.RUNNING );

		double ellapsedSeconds = exercise.getElapsedSeconds();

		exercise.setElapsedSeconds( 0 );
		persist( exercise );

		return ellapsedSeconds;
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseDao;
	}

	@Override
	public Input getInput()
	{
		return ( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject(
			getExerciseSpecificService( UserContext.getCurrentUser().getSession().getCurrentExercise().getClass() ).getSimpleName() ) ).getInput();
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		return ( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject( getExerciseSpecificService( ( (ExerciseImpl)findByID( exerciseID ) ).getClass() ).getSimpleName() ) )
			.getInputByExerciseID( exerciseID );
	}

	@Override
	public String getInputAsString()
	{
		try
		{
			Input input = getInput();
			return objectMapper.writeValueAsString( input );
		}
		catch ( JsonProcessingException e )
		{
			throw new RuntimeException( "error in converting getInput() to String" );
		}
	}

	@Override
	public String getInputByExerciseIDAsString( String exerciseID )
	{
		try
		{
			Input input = getInputByExerciseID( exerciseID );
			return objectMapper.writeValueAsString( input );
		}
		catch ( JsonProcessingException e )
		{
			throw new RuntimeException( "error in converting getInputByExerciseID() to String" );
		}
	}

	@Override
	public void setOutput( String output )
	{
		( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject(
			getExerciseSpecificService( UserContext.getCurrentUser().getSession().getCurrentExercise().getClass() ).getSimpleName() ) ).setOutput( output );
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			JsonNode rootNode = objectMapper.readTree( outputRequestString );
			String exerciseID = rootNode.path( "exerciseID" ).asText();

			if ( exerciseID.equalsIgnoreCase( "" ) )
			{
				throw new RuntimeException( "error performing setOutputByExerciseID: please provide a valid argument for exerciseID" );
			}

			ExerciseImpl exercise = findByID( exerciseID );
			if ( exercise == null )
			{
				throw new RuntimeException( "error performing setOutputByExerciseID: exercise with ID " + exerciseID + " could not be found" );
			}

			( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject( getExerciseSpecificService( exercise.getClass() ).getSimpleName() ) ).setOuputByExerciseID( outputRequestString );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "problem processing output request with String: " + outputRequestString );
		}
	}

	@Override
	public List< ExerciseDataImpl > getOutput()
	{
		// return exerciseDataDao.findByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
		return exerciseDataService.findByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public List< ExerciseDataImpl > getOutputByExerciseID( String exerciseID )
	{
		return exerciseDataService.findByExerciseID( exerciseID );
	}

	@Override
	public void startUser()
	{
		Participant participant = participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() );
		participant.getTimer().setStatus( WorkflowElementStatusImpl.RUNNING );
		participantDao.persist( participant );
	}

	@Override
	public void stopUser()
	{
		Participant participant = participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() );
		participant.getTimer().setStatus( WorkflowElementStatusImpl.TERMINATED );
		participantDao.persist( participant );
	}

	@Override
	public void resetUser()
	{
		Participant participant = participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() );
		participant.getTimer().setStatus( WorkflowElementStatusImpl.NEW );
		participant.getTimer().setTimeUnit( null );
		participant.getTimer().setValue( 0 );
		participantDao.persist( participant );
	}

	@Override
	public void suspendUser( TimerRequest request )
	{
		Participant participant = participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() );
		participant.getTimer().setStatus( WorkflowElementStatusImpl.SUSPENDED );
		participant.getTimer().setTimeUnit( request.getTimeUnit() );
		participant.getTimer().setValue( request.getValue() );
		participantDao.persist( participant );
	}

	@Override
	public TimerRequest resumeUser()
	{
		Participant participant = participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() );
		participant.getTimer().setStatus( WorkflowElementStatusImpl.RUNNING );
		participantDao.persist( participant );

		return new TimerRequest( participant.getTimer().getTimeUnit(), participant.getTimer().getValue() );
	}

	@Override
	public void cancelUser()
	{
		// same operation as stopUser();
		stopUser();
	}

	public ExerciseDataDao getExerciseDataDao()
	{
		return exerciseDataDao;
	}

	@Override
	public Participant findUserParticipant()
	{
		return (Participant)simplifyOwnerInObjectGraph( participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() ) );
	}

}
