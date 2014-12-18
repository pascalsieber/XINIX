package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.IOException;
import java.util.HashMap;
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
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.CompressionExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.EvaluationExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.P2POneExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.P2PTwoExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.PinkLabsExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.SimplePrototypingExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.XinixExerciseService;
import ch.zhaw.iwi.cis.pews.service.exercise.impl.You2MeExerciseService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.SimplePrototypingDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.You2MeDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseServiceImpl extends WorkflowElementServiceImpl implements ExerciseService
{
	private ObjectMapper objectMapper;

	private ExerciseDao exerciseDao;
	private ExerciseDataDao exerciseDataDao;
	private ParticipantDao participantDao;
	private WorkshopDao workshopDao;
	private static final Map< String, Class< ? extends ExerciseServiceImpl > > EXERCISESPECIFICSERVICES = new HashMap< String, Class< ? extends ExerciseServiceImpl >>();

	static
	{
		EXERCISESPECIFICSERVICES.put( PinkLabsDefinition.class.getSimpleName(), PinkLabsExerciseService.class );
		EXERCISESPECIFICSERVICES.put( You2MeDefinition.class.getSimpleName(), You2MeExerciseService.class );
		EXERCISESPECIFICSERVICES.put( P2POneDefinition.class.getSimpleName(), P2POneExerciseService.class );
		EXERCISESPECIFICSERVICES.put( P2PTwoDefinition.class.getSimpleName(), P2PTwoExerciseService.class );
		EXERCISESPECIFICSERVICES.put( SimplePrototypingDefinition.class.getSimpleName(), SimplePrototypingExerciseService.class );
		EXERCISESPECIFICSERVICES.put( XinixDefinition.class.getSimpleName(), XinixExerciseService.class );
		EXERCISESPECIFICSERVICES.put( CompressionDefinition.class.getSimpleName(), CompressionExerciseService.class );
		EXERCISESPECIFICSERVICES.put( EvaluationDefinition.class.getSimpleName(), EvaluationExerciseService.class );
	}

	private Class< ? > getExerciseSpecificService( String exerciseDefinitionClassName )
	{
		return EXERCISESPECIFICSERVICES.get( exerciseDefinitionClassName );
	}

	public ExerciseServiceImpl()
	{
		exerciseDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDaoImpl.class.getSimpleName() );
		exerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataDaoImpl.class.getSimpleName() );
		participantDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ParticipantDaoImpl.class.getSimpleName() );
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
		objectMapper = new ObjectMapper();
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
	 * overriding this method to handle orderInWorkshop correctly.
	 */
	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		// assign orderInWorkshop field (back of the row, last exercise)
		// but only on insert (i.e. persist of new exercise)

		// check if object with ID is available (has been persisted)
		if ( null == exerciseDao.findById( object.getID() ) )
		{
			WorkshopImpl workshop = workshopDao.findById( ( (ExerciseImpl)object ).getWorkshop().getID() );
			( (ExerciseImpl)object ).setOrderInWorkshop( workshop.getExercises().size() );
		}
		else
		{
			// if not null, it means that persist operation is for update
			// keep existing orderInWorkshop
			( (ExerciseImpl)object ).setOrderInWorkshop( ( (ExerciseImpl)exerciseDao.findById( object.getID() ) ).getOrderInWorkshop() );
		}

		return super.persist( object );
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
			getExerciseSpecificService( UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition().getClass().getSimpleName() ).getSimpleName() ) ).getInput();
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		return ( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject(
			getExerciseSpecificService( ( (ExerciseImpl)findByID( exerciseID ) ).getDefinition().getClass().getSimpleName() ).getSimpleName() ) ).getInputByExerciseID( exerciseID );
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
			getExerciseSpecificService( UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition().getClass().getSimpleName() ).getSimpleName() ) ).setOutput( output );
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

			( (ExerciseService)ZhawEngine.getManagedObjectRegistry().getManagedObject( getExerciseSpecificService( exercise.getDefinition().getClass().getSimpleName() ).getSimpleName() ) )
				.setOuputByExerciseID( outputRequestString );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "problem processing output request with String: " + outputRequestString );
		}
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
		return participantDao.findByPrincipalIDandSessionID( UserContext.getCurrentUser().getID(), UserContext.getCurrentUser().getSession().getID() );
	}

}
