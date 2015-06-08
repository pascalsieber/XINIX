package ch.zhaw.sml.iwi.cis.pews.test.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.GlobalService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ClientServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDataServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.GlobalServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.SessionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopServiceProxy;
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
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationResultDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PosterDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.SimplePrototypingDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixImageMatrix;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.You2MeDefinition;
import ch.zhaw.iwi.cis.pinkelefant.workshop.definition.PinkElefantDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadTest
{

	private static UserImpl user = new UserImpl();
	private static ObjectMapper mapper = new ObjectMapper();

	private static GlobalService globalService = ServiceProxyManager.createServiceProxy( GlobalServiceProxy.class );
	private static SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
	private static RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
	private static UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
	private static WorkshopDefinitionService workshopDefinitionService = ServiceProxyManager.createServiceProxy( WorkshopDefinitionServiceProxy.class );
	private static WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
	private static ExerciseDefinitionService exerciseDefinitionService = ServiceProxyManager.createServiceProxy( ExerciseDefinitionServiceProxy.class );
	private static ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
	private static ExerciseDataService exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
	private static ClientService clientService = ServiceProxyManager.createServiceProxy( ClientServiceProxy.class );

	@BeforeClass
	public static void generateLoad()
	{
		loadGenerator( 1, 1, 20 );
	}

	private static void loadGenerator( int workshops, int users, int artifacts )
	{
		Random rn = new Random();

		System.out.println( "generating load for testing...." );

		user.setID( userService.findByLoginName( ZhawEngine.ROOT_USER_LOGIN_NAME ).getID() );

		String sampleXinixImageID = exerciseDataService.persist( new XinixImage( user, null, "http://skylla.zhaw.ch/xinix_images/xinix_img_13.jpg" ) );

		for ( int j = 0; j < workshops; j++ )
		{
			// Workshop definition and instance
			WorkshopDefinitionImpl wsDef = workshopDefinitionService.findByID( workshopDefinitionService
				.persist( new PinkElefantDefinition( user, "ws_def_name_", "ws_def_descr_", "ws_def_problem_" ) ) );
			String wsID = workshopService.persist( new WorkshopImpl( j + "_ws_name_", j + "_ws_descr_", wsDef ) );

			// exercise definitions and instance
			WorkflowElementDefinitionImpl startDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new PosterDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_start_def_name_", j + "_start_def_descr_" ) ) );
			exerciseService.persist( new ExerciseImpl( j + "_start_ex_name_", j + "_start_ex_descr_", startDef, (WorkshopImpl)workshopService.findByID( wsID ) ) );

			WorkflowElementDefinitionImpl plabsDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new PinkLabsDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_plabs_question_" ) ) );
			ExerciseImpl plabs = exerciseService.findByID( exerciseService.persist( new ExerciseImpl( j + "_plabs_ex_name_", j + "_plabs_ex_descr_", plabsDef, (WorkshopImpl)workshopService
				.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl p1Def = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new P2POneDefinition(
				user,
				TimeUnit.SECONDS,
				10,
				wsDef,
				"http://oncampusadvertising.com/blog/wp-content/uploads/2014/11/college-students-using-smartphones-and-tablets.jpg",
				j + "_p2p1_question_" ) ) );
			ExerciseImpl p1 = exerciseService.findByID( exerciseService.persist( new ExerciseImpl( j + "_p1_ex_name_", j + "_p1_ex_descr_", p1Def, (WorkshopImpl)workshopService.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl p2Def = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new P2PTwoDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_p2_def_question_" ) ) );
			ExerciseImpl p2 = exerciseService.findByID( exerciseService.persist( new ExerciseImpl( j + "_p2_ex_name_", j + "_p2_ex_descr_", p2Def, (WorkshopImpl)workshopService.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl xinixDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new XinixDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_xinix_def_question_", (XinixImageMatrix)workshopDefinitionService.findByID( ZhawEngine.XINIX_IMAGE_MATRIX_ID ) ) ) );
			ExerciseImpl xinix = exerciseService.findByID( exerciseService.persist( new ExerciseImpl( j + "_xinix_ex_name_", j + "_xinix_ex_descr_", xinixDef, (WorkshopImpl)workshopService
				.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl u2mDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new You2MeDefinition( user, TimeUnit.SECONDS, 10, wsDef, Arrays.asList( j
					+ "_u2me_question1_", j + "_u2me_question2_" ) ) ) );
			ExerciseImpl u2m = exerciseService
				.findByID( exerciseService.persist( new ExerciseImpl( j + "_u2m_ex_name_", j + "_u2m_ex_descr_", u2mDef, (WorkshopImpl)workshopService.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl spDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new SimplePrototypingDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_sproto_question_", "mime" ) ) );
			ExerciseImpl proto = exerciseService
				.findByID( exerciseService.persist( new ExerciseImpl( j + "_sp_ex_name_", j + "_sp_ex_descr_", spDef, (WorkshopImpl)workshopService.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl cDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new CompressionDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_compression_question_", Arrays.asList( j + "_crit1_", j + "_crit2_" ) ) ) );
			ExerciseImpl compression = exerciseService.findByID( exerciseService.persist( new ExerciseImpl(
				j + "_compression_ex_name_",
				j + "_compression_ex_descr_",
				cDef,
				(WorkshopImpl)workshopService.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl evalDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new EvaluationDefinition( user, TimeUnit.SECONDS, 10, wsDef, j
					+ "_eval_question_", 10 ) ) );
			ExerciseImpl eval = exerciseService.findByID( exerciseService.persist( new ExerciseImpl( j + "_eval_ex_name_", j + "_eval_ex_descr_", evalDef, (WorkshopImpl)workshopService
				.findByID( wsID ) ) ) );

			WorkflowElementDefinitionImpl resDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new EvaluationResultDefinition( user, TimeUnit.SECONDS, 10, wsDef ) ) );
			exerciseService.persist( new ExerciseImpl( j + "_res_ex_name_", j + "_res_ex_descr_", resDef, (WorkshopImpl)workshopService.findByID( wsID ) ) );

			WorkflowElementDefinitionImpl endDef = exerciseDefinitionService.findByID( exerciseDefinitionService.persist( new PosterDefinition(
				user,
				TimeUnit.SECONDS,
				10,
				wsDef,
				j + "_end_def_name_",
				j + "_end_def_descr_" ) ) );
			exerciseService.persist( new ExerciseImpl( j + "_end_ex_name_", j + "_end_ex_descr_", endDef, (WorkshopImpl)workshopService.findByID( wsID ) ) );

			// session
			SessionImpl session = sessionService.findByID( sessionService.persist( new SessionImpl( j + "_session_", j + "_session_", null, (WorkshopImpl)workshopService.findByID( wsID ) ) ) );

			// executer
			UserImpl executer = userService.findByID( userService.persist( new UserImpl(
				new PasswordCredentialImpl( "abc123" ),
				(RoleImpl)roleService.findByID( ZhawEngine.EXECUTER_ROLE_ID ),
				null,
				"executer",
				"executer",
				"/" + j + "/executer" ) ) );

			sessionService.join( new Invitation( null, executer, session ) );

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
					exerciseDataService.persist( new XinixData( u, xinix, new HashSet< String >( Arrays.asList( "random xinix answer" ) ), (XinixImage)exerciseDataService
						.findByID( sampleXinixImageID ) ) );
					exerciseDataService.persist( new SimplePrototypingData( u, proto, DatatypeConverter.parseBase64Binary( "cmFuZG9t" ) ) );
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
	public void exerciseDataLoad()
	{

	}

	@Test
	public void exerciseDefinitionLoad()
	{

	}

	@Test
	public void exerciseLoad()
	{

	}

	@Test
	public void invitationLoad()
	{

	}

	@Test
	public void roleLoad()
	{

	}

	@Test
	public void sessionLoad()
	{

	}

	@Test
	public void userLoad()
	{

	}

	@Test
	public void workshopDefinitionLoad()
	{

	}

	@Test
	public void workshopLoad()
	{

	}

}
