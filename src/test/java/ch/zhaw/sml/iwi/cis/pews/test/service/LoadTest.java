package ch.zhaw.sml.iwi.cis.pews.test.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImage;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.GlobalService;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ClientServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDataServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.GlobalServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.InvitationServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.SessionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageService;
import ch.zhaw.iwi.cis.pews.service.xinix.proxy.XinixImageMatrixServiceProxy;
import ch.zhaw.iwi.cis.pews.service.xinix.proxy.XinixImageServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Score;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationResultExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2POneExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PosterExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.SimplyPrototypingExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.You2MeExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Ignore( "enable in order to run load tests" )
public class LoadTest
{

	private static UserImpl user;
	private static ObjectMapper mapper = new ObjectMapper();

	// variables
	private static String exerciseID;
	private static String exerciseDataID;
	private static String sessionID;
	private static String workshopID;
	private static String workshopDefinitionID;
	private static String exerciseDefinitionID;
	private static String invitationID;

	private static GlobalService globalService = ServiceProxyManager.createServiceProxy( GlobalServiceProxy.class );
	private static SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
	private static RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
	private static UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
	private static WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy( WorkshopTemplateServiceProxy.class );
	private static WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
	private static ExerciseTemplateService exTemplateService = ServiceProxyManager.createServiceProxy( ExerciseTemplateServiceProxy.class );
	private static ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
	private static ExerciseDataService exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
	private static ClientService clientService = ServiceProxyManager.createServiceProxy( ClientServiceProxy.class );
	private static InvitationService invitationService = ServiceProxyManager.createServiceProxy( InvitationServiceProxy.class );
	private static XinixImageService xinixImageService = ServiceProxyManager.createServiceProxy( XinixImageServiceProxy.class );
	private static XinixImageMatrixService xinixImageMatrixService = ServiceProxyManager.createServiceProxy( XinixImageMatrixServiceProxy.class );

	@BeforeClass
	public static void generateLoad()
	{
		loadGenerator( 0, 10, 10 );
	}

	private static void loadGenerator( int workshops, int users, int artifacts )
	{
		Random rn = new Random();

		System.out.println( "generating load for testing...." );

		user = (UserImpl)userService.findByLoginName( ZhawEngine.ROOT_USER_LOGIN_NAME );

		String sampleXinixImageID = xinixImageService.persist( new XinixImage( "http://skylla.zhaw.ch/xinix_images/xinix_img_13.jpg" ) );

		for ( int j = 0; j < workshops; j++ )
		{
			// Workshop definition and instance
			WorkshopTemplate wsDef = workshopTemplateService.findByID( workshopTemplateService
				.persist( new PinkElefantTemplate( user, "ws_def_name_", "ws_def_descr_", "ws_def_problem_", "email text" ) ) );
			workshopDefinitionID = wsDef.getID();
			workshopID = workshopService.persist( new WorkshopImpl( j + "_ws_name_", j + "_ws_descr_", wsDef ) );

			// exercise definitions and instance
			PosterTemplate startDef = exTemplateService.findByID( exTemplateService.persist( new PosterTemplate( user, false, null, 0, false, false, false, 0, wsDef, "", j + "_start_title", j
					+ "_start_descr", "", "" ) ) );
			exerciseService.persist( new PosterExercise( j + "_start_ex_name_", j + "_start_ex_descr_", startDef, (WorkshopImpl)workshopService.findByID( workshopID ) ) );

			PinkLabsTemplate plabsDef = exTemplateService.findByID( exTemplateService
				.persist( new PinkLabsTemplate( user, false, null, 0, false, false, false, 0, wsDef, j + "_plabs_question", "", "" ) ) );
			ExerciseImpl plabs = exerciseService.findByID( exerciseService.persist( new PinkLabsExercise( j + "_plabs_ex_name_", j + "_plabs_ex_descr_", plabsDef, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			P2POneTemplate p1Def = exTemplateService.findByID( exTemplateService.persist( new P2POneTemplate( user, false, null, 0, false, false, false, 0, wsDef, j + "_p2p1", "url", "", "" ) ) );
			ExerciseImpl p1 = exerciseService.findByID( exerciseService.persist( new P2POneExercise( j + "_p1_ex_name_", j + "_p1_ex_descr_", p1Def, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			P2PTwoTemplate p2Def = exTemplateService.findByID( exTemplateService.persist( new P2PTwoTemplate( user, false, null, 0, false, false, false, 0, wsDef, j + "_p2_question", "", "" ) ) );
			ExerciseImpl p2 = exerciseService.findByID( exerciseService.persist( new P2PTwoExercise( j + "_p2_ex_name_", j + "_p2_ex_descr_", p2Def, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			XinixTemplate xinixDef = exTemplateService.findByID( exTemplateService.persist( new XinixTemplate(
				user,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				wsDef,
				j + "_xinix_question",
				"",
				"",
				(XinixImageMatrix)xinixImageMatrixService.findXinixImageMatrixByID( ZhawEngine.XINIX_IMAGE_MATRIX_ID ) ) ) );
			ExerciseImpl xinix = exerciseService.findByID( exerciseService.persist( new XinixExercise( j + "_xinix_ex_name_", j + "_xinix_ex_descr_", xinixDef, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			exerciseDefinitionID = xinixDef.getID();

			You2MeTemplate u2mDef = exTemplateService.findByID( exTemplateService.persist( new You2MeTemplate( user, false, null, 0, false, false, false, 0, wsDef, "", "", "", new HashSet< String >(
				Arrays.asList( j + "_u2m1", j + "_u2m2" ) ) ) ) );
			ExerciseImpl u2m = exerciseService.findByID( exerciseService.persist( new You2MeExercise( j + "_u2m_ex_name_", j + "_u2m_ex_descr_", u2mDef, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			SimplyPrototypingTemplate spDef = exTemplateService.findByID( exTemplateService.persist( new SimplyPrototypingTemplate(
				user,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				wsDef,
				j + "sp?",
				"m",
				"",
				"" ) ) );
			ExerciseImpl proto = exerciseService.findByID( exerciseService.persist( new SimplyPrototypingExercise( j + "_sp_ex_name_", j + "_sp_ex_descr_", spDef, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			exerciseID = proto.getID();

			CompressionTemplate cDef = exTemplateService.findByID( exTemplateService.persist( new CompressionTemplate(
				user,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				wsDef,
				j + "_compr?",
				"",
				"",
				Arrays.asList( j + "_crit1_", j + "_crit2_" ) ) ) );
			ExerciseImpl compression = exerciseService.findByID( exerciseService.persist( new CompressionExercise(
				j + "_compression_ex_name_",
				j + "_compression_ex_descr_",
				cDef,
				(WorkshopImpl)workshopService.findByID( workshopID ) ) ) );

			EvaluationTemplate evalDef = exTemplateService
				.findByID( exTemplateService.persist( new EvaluationTemplate( user, false, null, 0, false, false, false, 0, wsDef, j + "_eval?", "", "", 3 ) ) );
			ExerciseImpl eval = exerciseService.findByID( exerciseService.persist( new EvaluationExercise( j + "_eval_ex_name_", j + "_eval_ex_descr_", evalDef, (WorkshopImpl)workshopService
				.findByID( workshopID ) ) ) );

			EvaluationResultTemplate resDef = exTemplateService.findByID( exTemplateService.persist( new EvaluationResultTemplate(
				user,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				wsDef,
				j + "_res?",
				"",
				"" ) ) );
			exerciseService.persist( new EvaluationResultExercise( j + "_res_ex_name_", j + "_res_ex_descr_", resDef, (WorkshopImpl)workshopService.findByID( workshopID ) ) );

			PosterTemplate endDef = exTemplateService.findByID( exTemplateService.persist( new PosterTemplate( user, false, null, 0, false, false, false, 0, wsDef, "", j + "_end_title", j
					+ "_end_descr", "", "" ) ) );
			exerciseService.persist( new PosterExercise( j + "_end_ex_name_", j + "_end_ex_descr_", endDef, (WorkshopImpl)workshopService.findByID( workshopID ) ) );

			// session
			SessionImpl session = sessionService.findByID( sessionService.persist( new SessionImpl(
				j + "_session_",
				j + "_session_",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				(WorkshopImpl)workshopService.findByID( workshopID ),
				eval,
				null,
				null,
				null,
				null ) ) );
			sessionID = session.getID();

			// executer
			UserImpl executer = userService.findByID( userService.persist( new UserImpl(
				new PasswordCredentialImpl( "abc123" ),
				(RoleImpl)roleService.findByID( ZhawEngine.EXECUTER_ROLE_ID ),
				null,
				"executer",
				"executer",
				"/" + j + "/executer" ) ) );

			sessionService.join( new Invitation( null, executer, session ) );

			// invitation
			invitationID = invitationService.persist( new Invitation( user, user, session ) );

			// users
			for ( int k = 0; k < users; k++ )
			{
				UserImpl u = userService.findByID( userService.persist( new UserImpl(
					new PasswordCredentialImpl( "abc123" ),
					(RoleImpl)roleService.findByID( ZhawEngine.PARTICIPANT_ROLE_ID ),
					null,
					"participant",
					"participant",
					"/" + j + "/" + k + "/participant" ) ) );

				sessionService.join( new Invitation( null, u, session ) );

				// exercise data
				for ( int l = 0; l < artifacts; l++ )
				{
					exerciseDataService.persist( new PinkLabsExerciseData( u, plabs, Arrays.asList( "random plabs answer" ) ) );
					P2POneData p1Data = exerciseDataService.findByID( exerciseDataService.persist( new P2POneData( u, p1, Arrays.asList( "random keyword 1", "random keyword 2" ) ) ) );
					exerciseDataService.persist( new P2PTwoData( u, p2, Arrays.asList( "random p2 answer" ), new HashSet< P2POneKeyword >( Arrays.asList( p1Data.getKeywords().get( 0 ), p1Data
						.getKeywords()
						.get( 1 ) ) ) ) );
					exerciseDataService
						.persist( new XinixData( u, xinix, new HashSet< String >( Arrays.asList( "random xinix answer" ) ), (XinixImage)xinixImageService.findByID( sampleXinixImageID ) ) );

					// limiting the simply prototyping exercise data to one per user
					if ( l < 1 )
					{
						exerciseDataID = exerciseDataService.persist( new SimplePrototypingData( u, proto, null ) );
					}

					exerciseDataService.persist( new You2MeExerciseData( u, u2m, Arrays.asList( new DialogEntry( DialogRole.RoleA, "random dialog A" ), new DialogEntry(
						DialogRole.RoleB,
						"random dialog B" ) ) ) );
					CompressionExerciseData compressionData = exerciseDataService.findByID( exerciseDataService.persist( new CompressionExerciseData( u, compression, Arrays
						.asList( new CompressionExerciseDataElement( "random solution", "random description" ) ) ) ) );
					exerciseDataService.persist( new EvaluationExerciseData( u, eval, new Evaluation( u, compressionData.getSolutions().get( 0 ), new Score( u, rn.nextInt( 10 ) + 1 ) ) ) );
				}

			}

		}

	}

	private Object invokeMethod( Object service, Method method, Object[] args ) throws Throwable
	{
		Object retVal = null;
		long execution = 0;
		try
		{
			long start = System.currentTimeMillis();
			retVal = method.invoke( service, args );
			execution = System.currentTimeMillis() - start;
		}
		catch ( InvocationTargetException e )
		{
			throw e.getCause();
		}

		System.out.println( service.getClass().getSimpleName() + "/" + method.getName() + ": " + execution + "ms / " + mapper.writeValueAsString( retVal ).length() + " chars in json." );

		return retVal;
	}

	@Test
	public void clientLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( clientService, clientService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( clientService, clientService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { globalService.getRootClient().getID() } );
	}

	@Test
	public void exerciseDataLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( exerciseDataService, exerciseDataService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( exerciseDataService, exerciseDataService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { exerciseDataID } );

		// findByExerciseID
		invokeMethod( exerciseDataService, exerciseDataService.getClass().getMethod( "findByExerciseID", new Class[] { String.class } ), new Object[] { exerciseID } );
	}

	@Test
	public void exerciseDefinitionLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( exTemplateService, exTemplateService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( exTemplateService, exTemplateService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { exerciseDefinitionID } );
	}

	@Test
	public void exerciseLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( exerciseService, exerciseService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( exerciseService, exerciseService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { exerciseID } );

		// findUserParticipant
		invokeMethod( exerciseService, exerciseService.getClass().getMethod( "findUserParticipant" ), null );

		// getOutput
		invokeMethod( exerciseService, exerciseService.getClass().getMethod( "getOutput" ), null );

		// getOutputByExerciseID
		invokeMethod( exerciseService, exerciseService.getClass().getMethod( "getOutputByExerciseID", new Class[] { String.class } ), new Object[] { exerciseID } );
	}

	@Test
	public void invitationLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( invitationService, invitationService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( invitationService, invitationService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { invitationID } );
	}

	@Test
	public void roleLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( roleService, roleService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( roleService, roleService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { user.getRole().getID() } );
	}

	@Test
	public void sessionLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( sessionService, sessionService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( sessionService, sessionService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { sessionID } );

		// getCurrentExercise
		invokeMethod( sessionService, sessionService.getClass().getMethod( "getCurrentExercise", new Class[] { String.class } ), new Object[] { sessionID } );

		// getPreviousExercise
		invokeMethod( sessionService, sessionService.getClass().getMethod( "getPreviousExercise", new Class[] { String.class } ), new Object[] { sessionID } );

		// getNextExercise
		invokeMethod( sessionService, sessionService.getClass().getMethod( "getNextExercise", new Class[] { String.class } ), new Object[] { sessionID } );

	}

	@Test
	public void userLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( userService, userService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( userService, userService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { user.getID() } );

		// findByLoginName
		invokeMethod( userService, userService.getClass().getMethod( "findByLoginName", new Class[] { String.class } ), new Object[] { user.getLoginName() } );

	}

	@Test
	public void workshopDefinitionLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( workshopTemplateService, workshopTemplateService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( workshopTemplateService, workshopTemplateService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { workshopDefinitionID } );
	}

	@Test
	public void workshopLoad() throws NoSuchMethodException, SecurityException, Throwable
	{
		// findAll
		invokeMethod( workshopService, workshopService.getClass().getMethod( "findAll" ), null );

		// findByID
		invokeMethod( workshopService, workshopService.getClass().getMethod( "findByID", new Class[] { String.class } ), new Object[] { workshopID } );

		// findAllWorkshopsSimple
		invokeMethod( workshopService, workshopService.getClass().getMethod( "findAllWorkshopsSimple" ), null );
	}

}
