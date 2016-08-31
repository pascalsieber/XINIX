package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.SimplyPrototypingInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.output.SimplePrototypingOutput;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.SimplyPrototypingExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link SimplyPrototypingExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class SimplyPrototypingExerciseTest
{
	private static ExerciseService     exerciseService;
	private static ExerciseDataService exerciseDataService;
	private static SessionService      sessionService;
	private static WorkshopService     workshopService;

	private static ExerciseImpl     exercise         = new SimplyPrototypingExercise();
	private static ExerciseTemplate exerciseTemplate = new SimplyPrototypingTemplate();
	private static WorkshopImpl     workshop         = new WorkshopImpl();
	private static SessionImpl      session          = new SessionImpl();
	private static UserImpl         owner            = new UserImpl();

	private static String NAME        = "name";
	private static String DESCRIPTION = "description";

	private static boolean  TIMED             = false;
	private static TimeUnit TIMEUNIT          = TimeUnit.MINUTES;
	private static int      DURATION          = 10;
	private static boolean  SHARING           = false;
	private static boolean  SKIPPABLE         = false;
	private static boolean  COUNTABLE         = false;
	private static int      CARDINALITY       = 10;
	private static String   QUESTION_TEMPLATE = "question";

	private static String MIME = "mime";

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public static void setup()
	{
		// owner
		String password = "password";
		String login = "login";
		UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		owner.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( password ),
				null,
				null,
				"",
				"",
				login ) ) );

		// services
		exerciseService = ServiceProxyManager.createServiceProxyWithUser( ExerciseServiceProxy.class, login, password );
		exerciseDataService = ServiceProxyManager.createServiceProxyWithUser( ExerciseDataServiceProxy.class,
				login,
				password );
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxyWithUser( WorkshopTemplateServiceProxy.class,
				login,
				password );
		workshopService = ServiceProxyManager.createServiceProxyWithUser( WorkshopServiceProxy.class, login, password );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxyWithUser( ExerciseTemplateServiceProxy.class,
				login,
				password );

		// workshop
		WorkshopTemplate workshopTemplate = workshopTemplateService.findWorkshopTemplateByID( workshopTemplateService.persist(
				new WorkshopTemplate( null, "", "" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// session
		sessionService = ServiceProxyManager.createServiceProxyWithUser( SessionServiceProxy.class, login, password );
		session.setID( sessionService.persistSession( new SessionImpl( "",
				"",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				new HashSet<Participant>(),
				new HashSet<PrincipalImpl>(),
				new HashSet<Invitation>(),
				new HashSet<PrincipalImpl>() ) ) );

		// owner joing session
		sessionService.join( new Invitation( null, owner, session ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new SimplyPrototypingTemplate( null,
				TIMED,
				TIMEUNIT,
				DURATION,
				SHARING,
				SKIPPABLE,
				COUNTABLE,
				CARDINALITY,
				workshopTemplate,
				QUESTION_TEMPLATE,
				"",
				"",
				MIME ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exercise.setID( exerciseService.persistExercise( new SimplyPrototypingExercise( NAME,
				DESCRIPTION,
				(SimplyPrototypingTemplate)exerciseTemplate,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		SimplyPrototypingExercise found = (SimplyPrototypingExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exercise.getID() ) );

		assertTrue( found.getCurrentState().equals( WorkflowElementStatusImpl.NEW.toString() ) );

		assertTrue( found.getData().isEmpty() );

		assertTrue( found.getWorkshop().getID().equals( workshop.getID() ) );
		assertTrue( found.getOrderInWorkshop() == 0 );
		assertTrue( found.getDerivedFrom().getID().equals( exerciseTemplate.getID() ) );

		assertTrue( found.getName().equals( NAME ) );
		assertTrue( found.getDescription().equals( DESCRIPTION ) );

		assertTrue( found.isTimed() == TIMED );
		assertTrue( found.getTimeUnit().toString().equals( TIMEUNIT.toString() ) );
		assertTrue( found.getDuration() == DURATION );
		assertTrue( found.isSharing() == SHARING );
		assertTrue( found.isSkippable() == SKIPPABLE );
		assertTrue( found.isCountable() == COUNTABLE );
		assertTrue( found.getCardinality() == CARDINALITY );

		assertTrue( found.getQuestion().equals( QUESTION_TEMPLATE ) );
		assertTrue( found.getMimeType().equals( MIME ) );
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput()
	{
		SimplyPrototypingExercise base = (SimplyPrototypingExercise)exerciseService.findExerciseByID( exercise.getID() );
		SimplyPrototypingInput input = (SimplyPrototypingInput)exerciseService.getInputByExerciseID( exercise.getID() );

		assertTrue( input.getExerciseID().equals( base.getID() ) );
		assertTrue( input.getExerciseType().equals( base.getClass().getSimpleName() ) );
		assertTrue( input.getExerciseOrderInWorkshop().equals( base.getOrderInWorkshop() ) );
		assertTrue( input.getExerciseCurrentState().equals( base.getCurrentState() ) );

		assertTrue( input.isTimed() == base.isTimed() );
		assertTrue( input.getTimeUnit().toString().equals( base.getTimeUnit().toString() ) );
		assertTrue( input.getDuration() == base.getDuration() );
		assertTrue( input.isSharing() == base.isSharing() );
		assertTrue( input.isSkippable() == base.isSkippable() );
		assertTrue( input.isCountable() == base.isCountable() );
		assertTrue( input.getCardinality() == base.getCardinality() );
		assertTrue( input.getHelp().equals( base.getDescription() ) );

		assertTrue( input.getQuestion().equals( base.getQuestion() ) );
		assertTrue( input.getMimeType().equals( base.getMimeType() ) );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws JsonProcessingException
	{
		String type = "image/gif";
		String image = "randomimagestring";
		String base64String = "data:" + type + ";base64," + image;

		SimplePrototypingOutput output = new SimplePrototypingOutput( exercise.getID(), base64String );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> stored = exerciseDataService.findByExerciseID( exercise.getID() );
		assertTrue( stored.size() == 1 );
		assertTrue( stored.get( 0 ).getOwner().getID().equals( owner.getID() ) );

		assertTrue( ( (SimplePrototypingData)stored.get( 0 ) ).getMediaObject().getMimeType().equals( type ) );
		assertTrue( Arrays.equals( ( (SimplePrototypingData)stored.get( 0 ) ).getMediaObject().getBlob(),
				Base64.decodeBase64( image ) ) );
		assertTrue( ( (SimplePrototypingData)stored.get( 0 ) ).getMediaObject()
				.getMediaObjectType()
				.equals( MediaObjectType.SIMPLYPROTOTYPING ) );
	}

	@TestOrder( order = 5 ) @Test public void testGetOutput()
	{
		// set current exercise on owner's session, as exercise to get output for
		// is determined based on the current exercise of the session of user
		// making request
		SessionImpl dummy = new SessionImpl();
		dummy.setID( session.getID() );
		dummy.setCurrentExercise( exercise );
		sessionService.setCurrentExercise( dummy );

		// get output and compare to data of exercise (exerciseDataService)
		assertTrue( exerciseService.getOutput().equals( exerciseDataService.findByExerciseID( exercise.getID() ) ) );
	}

	@TestOrder( order = 6 ) @Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( findable ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		ExerciseImpl removable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( removable ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !exerciseService.findAllExercises().contains( removable ) );
		assertTrue( !workshopService.findWorkshopByID( workshop.getID() ).getExercises().contains( removable ) );
	}
}

