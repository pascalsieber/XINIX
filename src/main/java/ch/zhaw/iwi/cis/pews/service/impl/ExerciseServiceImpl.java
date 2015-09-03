package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.IOException;
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
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
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
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseServiceImpl extends WorkflowElementServiceImpl implements ExerciseService
{
	private ObjectMapper objectMapper;
	private ExerciseDataService exerciseDataService;

	private ExerciseDao exerciseDao;
	private ExerciseDataDao exerciseDataDao;
	private ParticipantDao participantDao;
	private WorkshopDao workshopDao;
	private static final Map< String, Class< ? extends ExerciseServiceImpl > > EXERCISESPECIFICSERVICES = new HashMap< String, Class< ? extends ExerciseServiceImpl >>();

	// TODO move this from manual entries to an automatic solution, preferably using annotation @ExerciseSpecificService (which is already implemented)
	static
	{
		EXERCISESPECIFICSERVICES.put( PinkLabsTemplate.class.getSimpleName(), PinkLabsExerciseService.class );
		EXERCISESPECIFICSERVICES.put( You2MeTemplate.class.getSimpleName(), You2MeExerciseService.class );
		EXERCISESPECIFICSERVICES.put( P2POneTemplate.class.getSimpleName(), P2POneExerciseService.class );
		EXERCISESPECIFICSERVICES.put( P2PTwoTemplate.class.getSimpleName(), P2PTwoExerciseService.class );
		EXERCISESPECIFICSERVICES.put( SimplyPrototypingTemplate.class.getSimpleName(), SimplePrototypingExerciseService.class );
		EXERCISESPECIFICSERVICES.put( XinixTemplate.class.getSimpleName(), XinixExerciseService.class );
		EXERCISESPECIFICSERVICES.put( CompressionTemplate.class.getSimpleName(), CompressionExerciseService.class );
		EXERCISESPECIFICSERVICES.put( EvaluationTemplate.class.getSimpleName(), EvaluationExerciseService.class );
		EXERCISESPECIFICSERVICES.put( PosterTemplate.class.getSimpleName(), PosterExerciseService.class );
		EXERCISESPECIFICSERVICES.put( EvaluationResultTemplate.class.getSimpleName(), EvaluationResultExerciseService.class );
	}

	private Class< ? > getExerciseSpecificService( String exerciseTemplateClassName )
	{
		return EXERCISESPECIFICSERVICES.get( exerciseTemplateClassName );
	}

	public ExerciseServiceImpl()
	{
		exerciseDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDaoImpl.class.getSimpleName() );
		exerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataDaoImpl.class.getSimpleName() );
		participantDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ParticipantDaoImpl.class.getSimpleName() );
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
		objectMapper = new ObjectMapper();
		exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
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
			getExerciseSpecificService( UserContext.getCurrentUser().getSession().getCurrentExercise().getDerivedFrom().getClass().getSimpleName() ).getSimpleName() ) ).getInput();
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		return ( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject(
			getExerciseSpecificService( ( (ExerciseImpl)findByID( exerciseID ) ).getDerivedFrom().getClass().getSimpleName() ).getSimpleName() ) ).getInputByExerciseID( exerciseID );
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
			getExerciseSpecificService( UserContext.getCurrentUser().getSession().getCurrentExercise().getDerivedFrom().getClass().getSimpleName() ).getSimpleName() ) ).setOutput( output );
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

			( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject( getExerciseSpecificService( exercise.getDerivedFrom().getClass().getSimpleName() ).getSimpleName() ) )
				.setOuputByExerciseID( outputRequestString );
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
