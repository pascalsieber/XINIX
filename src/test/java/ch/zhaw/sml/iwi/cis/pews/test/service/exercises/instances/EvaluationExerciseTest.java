package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInputElement;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutputElement;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link EvaluationExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class EvaluationExerciseTest
{
	private static ExerciseTemplateService exerciseTemplateService;
	private static ExerciseService         exerciseService;
	private static ExerciseDataService     exerciseDataService;
	private static SessionService          sessionService;
	private static WorkshopService         workshopService;

	private static ExerciseImpl     exercise                = new EvaluationExercise();
	private static ExerciseTemplate exerciseTemplate        = new EvaluationTemplate();
	private static WorkshopImpl     workshop                = new WorkshopImpl();
	private static SessionImpl      session                 = new SessionImpl();
	private static UserImpl         owner                   = new UserImpl();
	private static ExerciseImpl     compressionExercise     = new CompressionExercise();
	private static ExerciseDataImpl compressionExerciseData = new CompressionExerciseData();

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

	private static int VOTES = 3;

	private static String COMPRESSION_SOLUTION    = "solution";
	private static String COMPRESSION_DESCRIPTION = "description";

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public static void setup()
	{
		// owner
		String password = "password";
		String login = "evaluationexercisetestlogin";

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

		// compression exercise
		CompressionTemplate compressionTemplate = (CompressionTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new CompressionTemplate( null,
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
						new ArrayList<String>() ) ) );
		compressionExercise.setID( exerciseService.persistExercise( new CompressionExercise( "",
				"",
				compressionTemplate,
				workshop ) ) );

		// compression exercise data
		compressionExerciseData.setID( exerciseDataService.persistExerciseData( new CompressionExerciseData( owner,
				compressionExercise,
				Collections.singletonList( new CompressionExerciseDataElement( COMPRESSION_SOLUTION,
						COMPRESSION_DESCRIPTION ) ) ) ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new EvaluationTemplate( null,
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
				VOTES ) ) );

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
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		EvaluationTemplate template = (EvaluationTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		exercise.setID( exerciseService.persistExercise( new EvaluationExercise( NAME,
				DESCRIPTION,
				template,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		EvaluationExercise found = (EvaluationExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exercise.getID() ) );

		assertTrue( found.getCurrentState().equals( WorkflowElementStatusImpl.NEW.toString() ) );

		assertTrue( found.getData().isEmpty() );

		assertTrue( found.getWorkshop().getID().equals( workshop.getID() ) );
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
		assertTrue( found.getNumberOfVotes() == VOTES );
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput() throws IOException
	{
		EvaluationExercise base = (EvaluationExercise)exerciseService.findExerciseByID( exercise.getID() );
		EvaluationInput input = TestUtil.objectMapper.readValue( exerciseService.getInputByExerciseIDAsString( exercise.getID() ),
				EvaluationInput.class );

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
		assertTrue( input.getNumberOfVotes() == base.getNumberOfVotes() );

		assertTrue( input.getSolutions().size() == 1 );
		for ( CompressionInputElement element : input.getSolutions() )
		{
			assertTrue( element.getSolution().equals( COMPRESSION_SOLUTION ) );
			assertTrue( element.getDescription().equals( COMPRESSION_DESCRIPTION ) );
		}
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws IOException
	{
		int score = 5;
		EvaluationOutput output = new EvaluationOutput( exercise.getID(),
				Collections.singletonList( new EvaluationOutputElement( COMPRESSION_SOLUTION,
						COMPRESSION_DESCRIPTION,
						score ) ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> data = exerciseDataService.findByExerciseID( exercise.getID() );
		List<EvaluationExerciseData> stored = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString(
				data ),
				TestUtil.makeCollectionType( EvaluationExerciseData.class ) );

		// check / verify output
		assertTrue( stored.size() == 1 );
		for ( EvaluationExerciseData d : stored )
		{
			assertTrue( d.getOwner().getID().equals( owner.getID() ) );
			assertTrue( d.getEvaluation().getScore().getScore() == score );
			assertTrue( d.getEvaluation().getSolution().getSolution().equals( COMPRESSION_SOLUTION ) );
			assertTrue( d.getEvaluation().getSolution().getDescription().equals( COMPRESSION_DESCRIPTION ) );
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
		List<String> dataSolutionIds = new ArrayList<String>();
		for ( ExerciseDataImpl d : comparableData )
		{
			dataSolutionIds.add( ( (EvaluationExerciseData)d ).getEvaluation().getSolution().getID() );
		}

		List<String> outputSolutionIds = new ArrayList<String>();
		for ( ExerciseDataImpl o : comparableOutput )
		{
			outputSolutionIds.add( ( (EvaluationExerciseData)o ).getEvaluation().getSolution().getID() );
		}

		assertTrue(
				dataSolutionIds.containsAll( outputSolutionIds ) && outputSolutionIds.containsAll( dataSolutionIds ) );
	}

	@TestOrder( order = 6 ) @Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		ExerciseImpl removable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );
		assertTrue( !TestUtil.extractIds( workshopService.findWorkshopByID( workshop.getID() ).getExercises() )
				.contains( removable.getID() ) );
	}
}

