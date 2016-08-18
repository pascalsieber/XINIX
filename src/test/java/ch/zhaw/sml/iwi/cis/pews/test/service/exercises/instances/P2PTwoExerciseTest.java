package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.P2PTwoInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.output.P2PTwoOutput;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2POneExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link P2PTwoExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
public class P2PTwoExerciseTest
{
	private ExerciseService     exerciseService;
	private ExerciseDataService exerciseDataService;
	private SessionService      sessionService;
	private WorkshopService     workshopService;

	private ExerciseImpl     exercise         = new P2PTwoExercise();
	private ExerciseTemplate exerciseTemplate = new P2PTwoTemplate();
	private WorkshopImpl     workshop         = new WorkshopImpl();
	private SessionImpl      session          = new SessionImpl();
	private UserImpl         owner            = new UserImpl();
	private P2POneData       p2POneData       = new P2POneData();

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

	private static String KEYWORD_ONE = "keywordone";
	private static String KEYWORD_TWO = "keywordtwo";

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public void setup()
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

		// owner joining session
		sessionService.join( new Invitation( null, owner, session ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new P2PTwoTemplate( null,
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
				"" ) ) );

		// p2p one exercise
		P2POneTemplate p2POneTemplate = (P2POneTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new P2POneTemplate( null,
						false,
						null,
						0,
						false,
						false,
						false,
						0,
						workshopTemplate,
						"",
						"",
						"",
						"" ) ) );
		P2POneExercise p2POneExercise = (P2POneExercise)exerciseService.findExerciseByID( exerciseService.persistExercise(
				new P2POneExercise( "", "", p2POneTemplate, workshop ) ) );

		// p2p one data
		p2POneData.setID( exerciseDataService.persistExerciseData( new P2POneData( null,
				p2POneExercise,
				Arrays.asList( KEYWORD_ONE, KEYWORD_TWO ) ) ) );
	}

	@Test public void testPersist()
	{
		exercise.setID( exerciseService.persistExercise( new P2PTwoExercise( NAME,
				DESCRIPTION,
				(P2PTwoTemplate)exerciseTemplate,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		P2PTwoExercise found = (P2PTwoExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exercise.getID() ) );

		assertTrue( found.getCurrentState().equals( WorkflowElementStatusImpl.NEW.toString() ) );

		assertTrue( found.getData().isEmpty() );

		assertTrue( found.getWorkshop().getID().equals( workshop.getID() ) );
		assertTrue( found.getOrderInWorkshop() == 1 );
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
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@Test public void testGetInput()
	{
		P2PTwoExercise base = (P2PTwoExercise)exerciseService.findExerciseByID( exercise.getID() );
		P2PTwoInput input = (P2PTwoInput)exerciseService.getInputByExerciseID( exercise.getID() );

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

		// keyword check
		List<P2POneKeyword> p2POneKeywords = ( (P2POneData)exerciseDataService.findExerciseDataByID( p2POneData.getID() ) )
				.getKeywords();
		assertTrue( input.getCascade1Keywords().size() == 2 );
		assertTrue( input.getCascade1Keywords().get( 0 ).getId().equals( p2POneKeywords.get( 0 ).getID() ) );
		assertTrue( input.getCascade1Keywords().get( 0 ).getKeyword().equals( KEYWORD_ONE ) );
		assertTrue( input.getCascade1Keywords().get( 1 ).getId().equals( p2POneKeywords.get( 1 ).getID() ) );
		assertTrue( input.getCascade1Keywords().get( 1 ).getKeyword().equals( KEYWORD_TWO ) );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@Test public void testSetOutput() throws JsonProcessingException
	{
		String outputOne = "outputone";
		String outputTwo = "outputtwo";

		P2PTwoOutput output = new P2PTwoOutput( exercise.getID(),
				new HashSet<>( Arrays.asList( KEYWORD_ONE, KEYWORD_TWO ) ),
				Arrays.asList( outputOne, outputTwo ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> stored = exerciseDataService.findByExerciseID( exercise.getID() );
		assertTrue( stored.size() == 1 );
		assertTrue( stored.get( 0 ).getOwner().getID().equals( owner.getID() ) );

		assertTrue( ( (P2PTwoData)stored.get( 0 ) ).getAnswers().containsAll( Arrays.asList( outputOne, outputTwo ) ) );
	}

	@Test public void testGetOutput()
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

	@Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( findable ) );
	}

	@Test public void testRemove()
	{
		ExerciseImpl removable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( removable ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !exerciseService.findAllExercises().contains( removable ) );
		assertTrue( !workshopService.findWorkshopByID( workshop.getID() ).getExercises().contains( removable ) );
	}
}

