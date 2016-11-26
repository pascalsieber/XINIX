package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.P2PKeywordInput;
import ch.zhaw.iwi.cis.pews.model.input.P2PTwoInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.output.P2PTwoOutput;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
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
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
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
@RunWith( OrderedRunner.class ) public class P2PTwoExerciseTest
{
	private static ExerciseTemplateService exerciseTemplateService;
	private static ExerciseService         exerciseService;
	private static ExerciseDataService     exerciseDataService;
	private static SessionService          sessionService;
	private static WorkshopService         workshopService;

	private static ExerciseImpl     exercise         = new P2PTwoExercise();
	private static ExerciseTemplate exerciseTemplate = new P2PTwoTemplate();
	private static WorkshopImpl     workshop         = new WorkshopImpl();
	private static SessionImpl      session          = new SessionImpl();
	private static UserImpl         owner            = new UserImpl();
	private static P2POneData       p2POneData       = new P2POneData();

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

	@BeforeClass public static void setup()
	{
		// owner
		String password = "password";
		String login = "p2ptwoexercisetestlogin";

		RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
		RoleImpl role = roleService.findByID( roleService.persist( new RoleImpl( "role", "role" ) ) );

		UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		owner.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( password ),
				role,
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
		exerciseTemplateService = ServiceProxyManager.createServiceProxyWithUser( ExerciseTemplateServiceProxy.class,
				login,
				password );

		// workshop
		PinkElefantTemplate workshopTemplate = new PinkElefantTemplate();
		workshopTemplate.setID( workshopTemplateService.persist( new PinkElefantTemplate( owner, "", "", "", "" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

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

		// owner joining session
		sessionService.join( new Invitation( null, owner, session ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		P2PTwoTemplate template = (P2PTwoTemplate)exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() );
		exercise.setID( exerciseService.persistExercise( new P2PTwoExercise( NAME,
				DESCRIPTION,
				template,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
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
	@TestOrder( order = 3 ) @Test public void testGetInput() throws IOException
	{
		P2PTwoExercise base = (P2PTwoExercise)exerciseService.findExerciseByID( exercise.getID() );
		P2PTwoInput input = TestUtil.objectMapper.readValue( exerciseService.getInputByExerciseIDAsString( exercise.getID() ),
				P2PTwoInput.class );

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

		List<String> p2POneKeywordIds = new ArrayList<>();
		for ( P2POneKeyword p2POneKeyword : p2POneKeywords )
		{
			p2POneKeywordIds.add( p2POneKeyword.getID() );
		}

		for ( P2PKeywordInput keyword : input.getCascade1Keywords() )
		{
			assertTrue( keyword.getKeyword().equals( KEYWORD_ONE ) || keyword.getKeyword().equals( KEYWORD_TWO ) );
			assertTrue( p2POneKeywordIds.contains( keyword.getId() ) );
		}
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws IOException
	{
		String outputOne = "outputone";
		String outputTwo = "outputtwo";

		P2PTwoOutput output = new P2PTwoOutput( exercise.getID(),
				new HashSet<>( Arrays.asList( KEYWORD_ONE, KEYWORD_TWO ) ),
				Arrays.asList( outputOne, outputTwo ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> data = exerciseDataService.findByExerciseID( exercise.getID() );
		List<P2PTwoData> stored = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString( data ),
				TestUtil.makeCollectionType( P2PTwoData.class ) );

		assertTrue( stored.size() == 1 );
		for ( P2PTwoData d : stored )
		{
			assertTrue( d.getOwner().getID().equals( owner.getID() ) );
			assertTrue( d.getAnswers().containsAll( Arrays.asList( outputOne, outputTwo ) ) );
			for ( P2POneKeyword keyword : d.getSelectedKeywords() )
			{
				assertTrue( keyword.getKeyword().equals( KEYWORD_ONE ) || keyword.getKeyword().equals( KEYWORD_TWO ) );
			}
		}
	}

	// only testing getOutputByExerciseID. this ends up doing the same as getOutput, except that
	// the exerciseID is explicitly provided as argument and not deduced from the user's session
	@TestOrder( order = 5 ) @Test public void testGetOutput() throws IOException
	{
		// get data from exerciseDataService for exercise
		List<ExerciseDataImpl> data = exerciseDataService.findByExerciseID( exercise.getID() );
		List<ExerciseDataImpl> comparableData = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString(
				data ),
				TestUtil.makeCollectionType( ExerciseDataImpl.class ) );

		// get output from exerciseService for exercise
		List<ExerciseDataImpl> output = exerciseService.getOutputByExerciseID( exercise.getID() );
		List<ExerciseDataImpl> comparableOutput = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString(
				output ),
				TestUtil.makeCollectionType( ExerciseDataImpl.class ) );

		// ensure output and data are not empty and compare ids
		assertTrue( comparableData.size() == 1 && comparableOutput.size() == 1 );
		assertTrue( TestUtil.extractIds( comparableData ).containsAll( TestUtil.extractIds( comparableOutput ) ) );
		assertTrue( TestUtil.extractIds( comparableOutput ).containsAll( TestUtil.extractIds( comparableData ) ) );

		// check specifics
		List<String> dataAnswers = new ArrayList<>();
		List<String> dataKeywordIds = new ArrayList<>();
		for ( ExerciseDataImpl d : comparableData )
		{
			dataAnswers.addAll( ( (P2PTwoData)d ).getAnswers() );
			for ( P2POneKeyword keyword : ( (P2PTwoData)d ).getSelectedKeywords() )
			{
				dataKeywordIds.add( keyword.getID() );
			}
		}

		List<String> outputAnswers = new ArrayList<>();
		List<String> outputKeywordIds = new ArrayList<String>();
		for ( ExerciseDataImpl o : comparableOutput )
		{
			outputAnswers.addAll( ( (P2PTwoData)o ).getAnswers() );
			for ( P2POneKeyword keyword : ( (P2PTwoData)o ).getSelectedKeywords() )
			{
				outputKeywordIds.add( keyword.getID() );
			}
		}

		assertTrue( dataAnswers.containsAll( outputAnswers ) && outputAnswers.containsAll( dataAnswers ) );
		assertTrue( dataKeywordIds.containsAll( outputKeywordIds ) && outputKeywordIds.containsAll( dataKeywordIds ) );
	}

	@TestOrder( order = 6 ) @Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		P2PTwoExercise removable = (P2PTwoExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );
		assertTrue( !TestUtil.extractIds( workshopService.findWorkshopByID( workshop.getID() ).getExercises() )
				.contains( removable.getID() ) );
	}
}

