package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.WorkflowElementDataImpl;
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
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
	private static ExerciseTemplateService exerciseTemplateService;
	private static ExerciseService         exerciseService;
	private static ExerciseDataService     exerciseDataService;
	private static SessionService          sessionService;
	private static WorkshopService         workshopService;

	private static ExerciseImpl     exercise         = new CompressionExercise();
	private static ExerciseTemplate exerciseTemplate = new CompressionTemplate();
	private static WorkshopImpl     workshop         = new WorkshopImpl();
	private static SessionImpl      session          = new SessionImpl();
	private static UserImpl         owner            = new UserImpl();

	private static PinkLabsExerciseData  pinkLabsExerciseData   = new PinkLabsExerciseData();
	private static P2POneData            p2pOneData             = new P2POneData();
	private static P2PTwoData            p2pTwoData             = new P2PTwoData();
	private static XinixData             xinixData              = new XinixData();
	private static MediaObject           simplePrototypingImage = new MediaObject();
	private static SimplePrototypingData simplePrototypingData  = new SimplePrototypingData();
	private static You2MeExerciseData    you2MeExerciseData     = new You2MeExerciseData();

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

		try
		{
			File temp = new File( "tempsimpleprotoimagetest.jpg" );
			FileUtils.copyURLToFile( new URL( "http://images.freeimages.com/images/previews/1da/lotus-1377828.jpg" ),
					temp );

			simplePrototypingImage.setID( mediaService.persistMediaObjectFormData( temp,
					MediaObjectType.SIMPLYPROTOTYPING,
					ZhawEngine.ROOT_USER_LOGIN_NAME,
					"root" ) );

			temp.delete();
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "error in persisting media object" );
		}

		simplePrototypingData.setID( exerciseDataService.persistExerciseData( new SimplePrototypingData( null,
				simplyPrototypingExercise,
				(MediaObject)mediaService.findByID( simplePrototypingImage.getID() ) ) ) );

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
		CompressionTemplate template = (CompressionTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		exercise.setID( exerciseService.persistExercise( new CompressionExercise( NAME,
				DESCRIPTION,
				template,
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
		// note that solutionCriteria not returned when calling exerciseService.findExerciseByID
		// therefore, not tested here, but as part of getInput (see testGetInput)
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput() throws IOException
	{
		CompressionExercise base = (CompressionExercise)exerciseService.findExerciseByID( exercise.getID() );
		CompressionInput input = TestUtil.objectMapper.readValue( exerciseService.getInputByExerciseIDAsString( exercise
				.getID() ), CompressionInput.class );

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
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws IOException
	{
		String solutionOne = "solutionone";
		String descriptionOne = "descriptionone";
		String solutionTwo = "solutiontwo";
		String descriptionTwo = "descriptiontwo";

		CompressionOutput output = new CompressionOutput( exercise.getID(),
				Arrays.asList( new CompressionOutputElement( solutionOne, descriptionOne ),
						new CompressionOutputElement( solutionTwo, descriptionTwo ) ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> data = exerciseDataService.findByExerciseID( exercise.getID() );
		List<CompressionExerciseData> stored = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString(
				data ),
				TestUtil.makeCollectionType( CompressionExerciseData.class ) );

		assertTrue( stored.size() == 1 );
		for ( CompressionExerciseData d : stored )
		{
			assertTrue( d.getOwner().getID().equals( owner.getID() ) );
			for ( CompressionExerciseDataElement solution : d.getSolutions() )
			{
				assertTrue(
						solution.getSolution().equals( solutionOne ) || solution.getSolution().equals( solutionTwo ) );
				if ( solution.getSolution().equals( solutionOne ) )
				{
					assertTrue( solution.getDescription().equals( descriptionOne ) );
				}
				else
				{
					assertTrue( solution.getDescription().equals( descriptionTwo ) );
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
		List<String> dataSolutionIds = new ArrayList<String>();
		for ( ExerciseDataImpl d : comparableData )
		{
			dataSolutionIds.addAll( TestUtil.extractIds( ( (CompressionExerciseData)d ).getSolutions() ) );
		}

		List<String> outputSolutionIds = new ArrayList<String>();
		for ( ExerciseDataImpl o : comparableOutput )
		{
			outputSolutionIds.addAll( TestUtil.extractIds( ( (CompressionExerciseData)o ).getSolutions() ) );
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
		CompressionExercise removable = (CompressionExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );

		// remove exercise data first
		for ( WorkflowElementDataImpl d : removable.getData() )
		{
			exerciseDataService.removeExerciseDataByID( d.getID() );
		}

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );
		assertTrue( !TestUtil.extractIds( workshopService.findWorkshopByID( workshop.getID() ).getExercises() )
				.contains( removable.getID() ) );
	}
}

