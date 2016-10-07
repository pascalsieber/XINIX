package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutput;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutputElement;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;
import ch.zhaw.iwi.cis.pews.service.xinix.proxy.XinixImageMatrixServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.*;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link CompressionExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class CompressionExerciseTest
{
	private static ExerciseService     exerciseService;
	private static ExerciseDataService exerciseDataService;
	private static SessionService      sessionService;
	private static WorkshopService     workshopService;

	private static ExerciseImpl     exercise         = new CompressionExercise();
	private static ExerciseTemplate exerciseTemplate = new CompressionTemplate();
	private static WorkshopImpl     workshop         = new WorkshopImpl();
	private static SessionImpl      session          = new SessionImpl();
	private static UserImpl         owner            = new UserImpl();

	private static PinkLabsExerciseData  pinkLabsExerciseData  = new PinkLabsExerciseData();
	private static P2POneData            p2pOneData            = new P2POneData();
	private static P2PTwoData            p2pTwoData            = new P2PTwoData();
	private static XinixData             xinixData             = new XinixData();
	private static SimplePrototypingData simplePrototypingData = new SimplePrototypingData();
	private static You2MeExerciseData    you2MeExerciseData    = new You2MeExerciseData();

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

	private static String CRITERION_ONE = "criterionone";
	private static String CRITERION_TWO = "criteriontwo";

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public static void setup()
	{
		// owner
		String password = "password";
		String login = "compressionexercisetestlogin";
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
		MediaService mediaService = ServiceProxyManager.createServiceProxy( MediaServiceProxy.class );

		// workshop
		PinkElefantTemplate workshopTemplate = new PinkElefantTemplate();
		workshopTemplate.setID( workshopTemplateService.persist( new PinkElefantTemplate( owner, "", "", "", "" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// session
		sessionService = ServiceProxyManager.createServiceProxyWithUser( SessionServiceProxy.class, login, password );
		session.setID( sessionService.persistSession( new SessionImpl( "",
				"",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				null,
				null ) ) );

		// owner joing session
		sessionService.join( new Invitation( null, owner, session ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new CompressionTemplate( null,
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
				Arrays.asList( CRITERION_ONE, CRITERION_TWO ) ) ) );

		// pinklabs exercise with data
		PinkLabsTemplate pinkLabsTemplate = (PinkLabsTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
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
						"" ) ) );
		PinkLabsExercise pinkLabsExercise = (PinkLabsExercise)exerciseService.findExerciseByID( exerciseService.persistExercise(
				new PinkLabsExercise( "", "", pinkLabsTemplate, workshop ) ) );

		pinkLabsExerciseData.setID( exerciseDataService.persistExerciseData( new PinkLabsExerciseData( null,
				pinkLabsExercise,
				new ArrayList<String>() ) ) );

		// p2pone exercise with data
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

		p2pOneData.setID( exerciseDataService.persistExerciseData( new P2POneData( null,
				p2POneExercise,
				Collections.singletonList( "" ) ) ) );

		// p2ptwo exercise with data
		P2PTwoTemplate p2PTwoTemplate = (P2PTwoTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new P2PTwoTemplate( null,
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
						"" ) ) );
		P2PTwoExercise p2PTwoExercise = (P2PTwoExercise)exerciseService.findExerciseByID( exerciseService.persistExercise(
				new P2PTwoExercise( "", "", p2PTwoTemplate, workshop ) ) );

		P2POneKeyword p2pOneKeyword = ( (P2POneData)exerciseDataService.findExerciseDataByID( p2pOneData.getID() ) ).getKeywords()
				.get( 0 );
		p2pTwoData.setID( exerciseDataService.persistExerciseData( new P2PTwoData( null,
				p2PTwoExercise,
				new ArrayList<String>(),
				new HashSet<P2POneKeyword>( Collections.singletonList( p2pOneKeyword ) ) ) ) );

		// xinix exercise with data
		MediaObject xinixImage = mediaService.findByID( mediaService.persist( new MediaObject( "",
				"".getBytes(),
				MediaObjectType.XINIX ) ) );
		XinixImageMatrixService xinixImageMatrixService = ServiceProxyManager.createServiceProxy(
				XinixImageMatrixServiceProxy.class );
		XinixImageMatrix xinixImageMatrix = xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrixService.persistImageMatrix(
				new XinixImageMatrix( Collections.singletonList( xinixImage ) ) ) );

		XinixTemplate xinixTemplate = (XinixTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new XinixTemplate( null,
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
						xinixImageMatrix ) ) );
		XinixExercise xinixExercise = (XinixExercise)exerciseService.findExerciseByID( exerciseService.persistExercise(
				new XinixExercise( "", "", xinixTemplate, workshop ) ) );

		xinixData.setID( exerciseDataService.persistExerciseData( new XinixData( null,
				xinixExercise,
				new HashSet<String>(),
				xinixImage ) ) );

		// simplyprototyping exercise with data
		SimplyPrototypingTemplate simplyPrototypingTemplate = (SimplyPrototypingTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new SimplyPrototypingTemplate( null,
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
		SimplyPrototypingExercise simplyPrototypingExercise = (SimplyPrototypingExercise)exerciseService.findExerciseByID(
				exerciseService.persistExercise( new SimplyPrototypingExercise( "",
						"",
						simplyPrototypingTemplate,
						workshop ) ) );

		MediaObject mediaObject = mediaService.findByID( mediaService.persist( new MediaObject( "",
				"".getBytes(),
				MediaObjectType.SIMPLYPROTOTYPING ) ) );

		simplePrototypingData.setID( exerciseDataService.persistExerciseData( new SimplePrototypingData( null,
				simplyPrototypingExercise,
				mediaObject ) ) );

		// you2me exercise with data
		You2MeTemplate you2MeTemplate = (You2MeTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new You2MeTemplate( null,
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
						new HashSet<String>() ) ) );
		You2MeExercise you2MeExercise = (You2MeExercise)exerciseService.findExerciseByID( exerciseService.persistExercise(
				new You2MeExercise( "", "", you2MeTemplate, workshop ) ) );
		you2MeExerciseData.setID( exerciseDataService.persistExerciseData( new You2MeExerciseData( null,
				you2MeExercise,
				Collections.singletonList( new DialogEntry( DialogRole.RoleA, "" ) ) ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exercise.setID( exerciseService.persistExercise( new CompressionExercise( NAME,
				DESCRIPTION,
				(CompressionTemplate)exerciseTemplate,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		CompressionExercise found = (CompressionExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exercise.getID() ) );

		assertTrue( found.getCurrentState().equals( WorkflowElementStatusImpl.NEW.toString() ) );

		assertTrue( found.getData().isEmpty() );

		assertTrue( found.getWorkshop().getID().equals( workshop.getID() ) );
		assertTrue( found.getOrderInWorkshop() == 6 );
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
		assertTrue( found.getSolutionCriteria().containsAll( Arrays.asList( CRITERION_ONE, CRITERION_TWO ) ) );
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput()
	{
		CompressionExercise base = (CompressionExercise)exerciseService.findExerciseByID( exercise.getID() );
		CompressionInput input = (CompressionInput)exerciseService.getInputByExerciseID( exercise.getID() );

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
		assertTrue( input.getSolutionCriteria().containsAll( Arrays.asList( CRITERION_ONE, CRITERION_TWO ) ) );

		// compressable exercise data
		List<String> compressableIds = new ArrayList<String>();
		for ( CompressableExerciseData compressableData : input.getCompressableExerciseData() )
		{
			compressableIds.add( compressableData.getID() );
		}
		assertTrue( compressableIds.containsAll( Arrays.asList( pinkLabsExerciseData.getID(),
				p2pOneData.getID(),
				p2pTwoData.getID(),
				xinixData.getID(),
				simplePrototypingData.getID(),
				you2MeExerciseData.getID() ) ) );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws JsonProcessingException
	{
		String solutionOne = "solutionone";
		String descriptionOne = "descriptionone";
		String solutionTwo = "solutiontwo";
		String descriptionTwo = "descriptiontwo";

		CompressionOutput output = new CompressionOutput( exercise.getID(),
				Arrays.asList( new CompressionOutputElement( solutionOne, descriptionOne ),
						new CompressionOutputElement( solutionTwo, descriptionTwo ) ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> stored = exerciseDataService.findByExerciseID( exercise.getID() );
		assertTrue( stored.size() == 1 );
		assertTrue( stored.get( 0 ).getOwner().getID().equals( owner.getID() ) );

		assertTrue( ( (CompressionExerciseData)stored.get( 0 ) ).getSolutions()
				.get( 0 )
				.getSolution()
				.equals( solutionOne ) );
		assertTrue( ( (CompressionExerciseData)stored.get( 0 ) ).getSolutions()
				.get( 0 )
				.getDescription()
				.equals( descriptionOne ) );
		assertTrue( ( (CompressionExerciseData)stored.get( 0 ) ).getSolutions()
				.get( 1 )
				.getSolution()
				.equals( solutionTwo ) );
		assertTrue( ( (CompressionExerciseData)stored.get( 0 ) ).getSolutions()
				.get( 1 )
				.getDescription()
				.equals( descriptionTwo ) );
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
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		CompressionExercise removable = (CompressionExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );
		assertTrue( !TestUtil.extractIds( workshopService.findWorkshopByID( workshop.getID() ).getExercises() )
				.contains( removable.getID() ) );
	}
}

