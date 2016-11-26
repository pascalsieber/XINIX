package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.XinixInput;
import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.output.XinixOutput;
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
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link XinixExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class XinixExerciseTest
{
	private static ExerciseTemplateService exerciseTemplateService;
	private static ExerciseService         exerciseService;
	private static ExerciseDataService     exerciseDataService;
	private static SessionService          sessionService;
	private static WorkshopService         workshopService;
	private static MediaService            mediaService;

	private static ExerciseImpl     exercise         = new XinixExercise();
	private static ExerciseTemplate exerciseTemplate = new XinixTemplate();
	private static WorkshopImpl     workshop         = new WorkshopImpl();
	private static SessionImpl      session          = new SessionImpl();
	private static UserImpl         owner            = new UserImpl();
	private static XinixImageMatrix xinixImageMatrix = new XinixImageMatrix();
	private static MediaObject      mediaObject      = new MediaObject();

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

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public static void setup()
	{
		// owner
		String password = "password";
		String login = "xinixexercisetestlogin";

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
		XinixImageMatrixService xinixImageMatrixService = ServiceProxyManager.createServiceProxyWithUser( XinixImageMatrixServiceProxy.class,
				login,
				password );
		mediaService = ServiceProxyManager.createServiceProxyWithUser( MediaServiceProxy.class, login, password );

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

		// xinix image matrix
		try
		{
			File temp = new File( "tempxinixrestservicetest.jpg" );
			FileUtils.copyURLToFile( new URL( "http://images.freeimages.com/images/previews/1da/lotus-1377828.jpg" ),
					temp );

			mediaObject.setID( mediaService.persistMediaObjectFormData( temp,
					MediaObjectType.XINIX,
					ZhawEngine.ROOT_USER_LOGIN_NAME,
					"root" ) );

			temp.delete();
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "error in persisting media object" );
		}
		xinixImageMatrix.setID( xinixImageMatrixService.persistImageMatrix( new XinixImageMatrix( Collections.singletonList(
				(MediaObject)mediaService.findByID( mediaObject.getID() ) ) ) ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new XinixTemplate( null,
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
				xinixImageMatrix ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		XinixTemplate template = (XinixTemplate)exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() );
		exercise.setID( exerciseService.persistExercise( new XinixExercise( NAME, DESCRIPTION, template, workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		XinixExercise found = (XinixExercise)exerciseService.findExerciseByID( exercise.getID() );
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
		assertTrue( found.getImages().getID().equals( xinixImageMatrix.getID() ) );
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput() throws IOException
	{
		XinixExercise base = (XinixExercise)exerciseService.findExerciseByID( exercise.getID() );
		XinixInput input = TestUtil.objectMapper.readValue( exerciseService.getInputByExerciseIDAsString( exercise.getID() ),
				XinixInput.class );

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
		assertTrue( input.getXinixImages().getID().equals( base.getImages().getID() ) );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput() throws IOException
	{
		String outputOne = "outputone";
		String outputTwo = "outputtwo";

		XinixOutput output = new XinixOutput( exercise.getID(),
				new HashSet<>( Arrays.asList( outputOne, outputTwo ) ),
				(MediaObject)mediaService.findByID( mediaObject.getID() ) );
		exerciseService.setOuputByExerciseID( objectMapper.writeValueAsString( output ) );

		List<ExerciseDataImpl> data = exerciseDataService.findByExerciseID( exercise.getID() );
		List<XinixData> stored = TestUtil.objectMapper.readValue( TestUtil.objectMapper.writeValueAsString( data ),
				TestUtil.makeCollectionType( XinixData.class ) );

		assertTrue( stored.size() == 1 );
		for ( XinixData d : stored )
		{
			assertTrue( d.getOwner().getID().equals( owner.getID() ) );
			assertTrue( d.getXinixImage().getID().equals( mediaObject.getID() ) );
			assertTrue( d.getAssociations().size() == 2 );
			assertTrue( d.getAssociations().containsAll( Arrays.asList( outputOne, outputTwo ) ) );
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

		// specifics
		List<String> dataAssociations = new ArrayList<String>();
		for ( ExerciseDataImpl d : comparableData )
		{
			dataAssociations.addAll( ( (XinixData)d ).getAssociations() );
		}

		List<String> outputAssociations = new ArrayList<String>();
		for ( ExerciseDataImpl o : comparableOutput )
		{
			outputAssociations.addAll( ( (XinixData)o ).getAssociations() );
		}

		assertTrue( outputAssociations.containsAll( dataAssociations ) && dataAssociations.containsAll(
				outputAssociations ) );
	}

	@TestOrder( order = 6 ) @Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		XinixExercise removable = (XinixExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseService.findAllExercises() ).contains( removable.getID() ) );
		assertTrue( !TestUtil.extractIds( workshopService.findWorkshopByID( workshop.getID() ).getExercises() )
				.contains( removable.getID() ) );
	}
}

