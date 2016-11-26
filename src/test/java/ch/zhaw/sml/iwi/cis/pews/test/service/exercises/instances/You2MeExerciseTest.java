package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.You2MeInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.output.You2MeOutput;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.user.*;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.You2MeExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;
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
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link You2MeExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class You2MeExerciseTest
{
	private static ExerciseTemplateService exerciseTemplateService;
	private static ExerciseService         exerciseService;
	private static ExerciseDataService     exerciseDataService;
	private static SessionService          sessionService;
	private static WorkshopService         workshopService;

	private static ExerciseImpl     exercise         = new You2MeExercise();
	private static ExerciseTemplate exerciseTemplate = new You2MeTemplate();
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

	private static String QUESTION_ONE = "questionone";
	private static String QUESTION_TWO = "questiontwo";

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public static void setup()
	{
		// owner
		String password = "password";
		String login = "you2meexercisetestlogin";

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
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new You2MeTemplate( null,
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
				new HashSet<String>( Arrays.asList( QUESTION_ONE, QUESTION_TWO ) ) ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		You2MeTemplate template = (You2MeTemplate)exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() );
		exercise.setID( exerciseService.persistExercise( new You2MeExercise( NAME,
				DESCRIPTION,
				template,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		You2MeExercise found = (You2MeExercise)exerciseService.findExerciseByID( exercise.getID() );
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
		assertTrue( found.getQuestions().containsAll( Arrays.asList( QUESTION_ONE, QUESTION_TWO ) ) );
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput() throws IOException
	{
		You2MeExercise base = (You2MeExercise)exerciseService.findExerciseByID( exercise.getID() );
		You2MeInput input = TestUtil.objectMapper.readValue( exerciseService.getInputByExerciseIDAsString( exercise.getID() ),
				You2MeInput.class );

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

		assertTrue( input.getQuestions().containsAll( base.getQuestions() ) );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws IOException
	{
		String outputOne = "outputone";
		String outputTwo = "outputtwo";
		DialogEntry entry0 = new DialogEntry( DialogRole.RoleA, outputOne );
		entry0.setOrderInDialog( 0 );
		DialogEntry entry1 = new DialogEntry( DialogRole.RoleA, outputOne );
		entry1.setOrderInDialog( 1 );

		You2MeOutput output = new You2MeOutput( exercise.getID(), Arrays.asList( entry0, entry1 ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> data = exerciseDataService.findByExerciseID( exercise.getID() );
		List<You2MeExerciseData> stored = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString(
				data ),
				TestUtil.makeCollectionType( You2MeExerciseData.class ) );

		assertTrue( stored.size() == 1 );
		for ( You2MeExerciseData d : stored )
		{
			assertTrue( d.getOwner().getID().equals( owner.getID() ) );
			assertTrue( d.getDialog().size() == 2 );
			for ( DialogEntry dialogEntry : d.getDialog() )
			{
				assertTrue( dialogEntry.getOrderInDialog() == 0 || dialogEntry.getOrderInDialog() == 1 );
				if ( dialogEntry.getOrderInDialog() == 0 )
				{
					dialogEntry.getText().equals( outputOne );
					dialogEntry.getRole().equals( DialogRole.RoleA );
				}
				else
				{
					dialogEntry.getText().equals( outputTwo );
					dialogEntry.getRole().equals( DialogRole.RoleB );
				}
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
		List<String> dataDialogEntryIds = new ArrayList<>();
		for ( ExerciseDataImpl d : comparableData )
		{
			for ( DialogEntry dialogEntry : ( (You2MeExerciseData)d ).getDialog() )
			{
				dataDialogEntryIds.add( dialogEntry.getID() );
			}
		}

		List<String> outputDialogEntryIds = new ArrayList<String>();
		for ( ExerciseDataImpl d : comparableOutput )
		{
			for ( DialogEntry dialogEntry : ( (You2MeExerciseData)d ).getDialog() )
			{
				outputDialogEntryIds.add( dialogEntry.getID() );
			}
		}

		assertTrue( dataDialogEntryIds.containsAll( outputDialogEntryIds ) && outputDialogEntryIds.containsAll(
				dataDialogEntryIds ) );
	}

	@TestOrder( order = 6 ) @Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		You2MeExercise removable = (You2MeExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );
		assertTrue( !TestUtil.extractIds( workshopService.findWorkshopByID( workshop.getID() ).getExercises() )
				.contains( removable.getID() ) );
	}
}

