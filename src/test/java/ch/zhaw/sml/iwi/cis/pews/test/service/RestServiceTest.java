package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInput;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInputElement;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationInput;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultInput;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultObject;
import ch.zhaw.iwi.cis.pews.model.input.P2PKeywordInput;
import ch.zhaw.iwi.cis.pews.model.input.P2POneInput;
import ch.zhaw.iwi.cis.pews.model.input.P2PTwoInput;
import ch.zhaw.iwi.cis.pews.model.input.PinkLabsInput;
import ch.zhaw.iwi.cis.pews.model.input.PosterInput;
import ch.zhaw.iwi.cis.pews.model.input.SimplePrototypingInput;
import ch.zhaw.iwi.cis.pews.model.input.XinixInput;
import ch.zhaw.iwi.cis.pews.model.input.You2MeInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutput;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutputElement;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutputElement;
import ch.zhaw.iwi.cis.pews.model.output.Output;
import ch.zhaw.iwi.cis.pews.model.output.P2POneOutput;
import ch.zhaw.iwi.cis.pews.model.output.P2PTwoOutput;
import ch.zhaw.iwi.cis.pews.model.output.PinkLabsOutput;
import ch.zhaw.iwi.cis.pews.model.output.SimplePrototypingOutput;
import ch.zhaw.iwi.cis.pews.model.output.XinixOutput;
import ch.zhaw.iwi.cis.pews.model.output.You2MeOutput;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.GlobalService;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDataServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.GlobalServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.InvitationServiceProxy;
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
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class RestServiceTest
{
	private static ObjectMapper mapper = new ObjectMapper();

	private static GlobalService globalService = ServiceProxyManager.createServiceProxy( GlobalServiceProxy.class );
	private static SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
	private static RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
	private static UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
	private static WorkshopDefinitionService workshopDefinitionService = ServiceProxyManager.createServiceProxy( WorkshopDefinitionServiceProxy.class );
	private static WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
	private static ExerciseDefinitionService exerciseDefinitionService = ServiceProxyManager.createServiceProxy( ExerciseDefinitionServiceProxy.class );
	private static ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
	private static ExerciseService exerciseServiceForSecondUser = ServiceProxyManager.createServiceProxyWithUser( ExerciseServiceProxy.class, "secondUser", "secondUser" );
	private static ExerciseDataService exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
	private static InvitationService invitationService = ServiceProxyManager.createServiceProxy( InvitationServiceProxy.class );

	// using stub objects to mimic the way API will be used
	private static Client defaultClientStub = new Client();
	private static RoleImpl defaultRoleStub = new RoleImpl();
	private static UserImpl defaultUserStub = new UserImpl();
	private static UserImpl secondUserStub = new UserImpl();
	private static PinkElefantDefinition defaultWorkshopDefinitionStub = new PinkElefantDefinition();
	private static WorkshopImpl defaultWorkshopStub = new WorkshopImpl();
	private static SessionImpl defaultSessionStub = new SessionImpl();
	private static Invitation defaultInvitationStub = new Invitation();

	private static PosterDefinition posterDefinitionStub = new PosterDefinition();
	private static PinkLabsDefinition pinklabsDefinitionStub = new PinkLabsDefinition();
	private static P2POneDefinition p2poneDefinitionStub = new P2POneDefinition();
	private static P2PTwoDefinition p2ptwoDefinitionStub = new P2PTwoDefinition();
	private static XinixImageMatrix xinixImageMatrixStub = new XinixImageMatrix();
	private static XinixDefinition xinixDefinitionStub = new XinixDefinition();
	private static You2MeDefinition you2meDefinitionStub = new You2MeDefinition();
	private static SimplePrototypingDefinition simpleprototypingDefinitionStub = new SimplePrototypingDefinition();
	private static CompressionDefinition compressionDefinitionStub = new CompressionDefinition();
	private static EvaluationDefinition evaluationDefinitionStub = new EvaluationDefinition();
	private static EvaluationResultDefinition evaluationResultDefinitionStub = new EvaluationResultDefinition();

	private static ExerciseImpl posterExerciseStub = new ExerciseImpl();
	private static ExerciseImpl pinklabsExerciseStub = new ExerciseImpl();
	private static ExerciseImpl you2meExerciseStub = new ExerciseImpl();
	private static ExerciseImpl p2pOneExerciseStub = new ExerciseImpl();
	private static ExerciseImpl p2pTwoExerciseStub = new ExerciseImpl();
	private static ExerciseImpl simpleprototypingExerciseStub = new ExerciseImpl();
	private static ExerciseImpl xinixExerciseStub = new ExerciseImpl();
	private static ExerciseImpl compressionExerciseStub = new ExerciseImpl();
	private static ExerciseImpl evaluationExerciseStub = new ExerciseImpl();
	private static ExerciseImpl evaluationResultExerciseStub = new ExerciseImpl();

	private static XinixImage xinixImageStub = new XinixImage();
	private static PinkLabsExerciseData pinklabsDataStub = new PinkLabsExerciseData();
	private static P2POneData p2poneDataStub = new P2POneData();
	private static P2PTwoData p2ptwoDataStub = new P2PTwoData();
	private static XinixData xinixDataStub = new XinixData();
	private static You2MeExerciseData you2meDataStub = new You2MeExerciseData();
	private static SimplePrototypingData simpleprototypingDataStub = new SimplePrototypingData();
	private static CompressionExerciseData compressionDataStub = new CompressionExerciseData();
	private static EvaluationExerciseData evaluationDataStub = new EvaluationExerciseData();

	@BeforeClass
	public static void setupTest()
	{
		Client rootClient = globalService.getRootClient();
		defaultClientStub.setID( rootClient.getID() );

		// role
		defaultRoleStub.setID( roleService.persist( new RoleImpl( "user", "user role" ) ) );

		// User
		defaultUserStub.setID( userService.findByLoginName( ZhawEngine.ROOT_USER_LOGIN_NAME ).getID() );
		secondUserStub.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( "secondUser" ), defaultRoleStub, null, "secondUser", "secondUser", "secondUser" ) ) );

		// workshop definition (pinkelefantDefinition)
		defaultWorkshopDefinitionStub.setID( workshopDefinitionService.persist( new PinkElefantDefinition(
			defaultUserStub,
			"workshop definition",
			"workshop definition test entry",
			"problem description" ) ) );

		// workshop instance
		defaultWorkshopStub.setID( workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", defaultWorkshopDefinitionStub ) ) );

		// exercise definitions
		posterDefinitionStub.setID( exerciseDefinitionService.persist( new PosterDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "start", "start description" ) ) );
		pinklabsDefinitionStub.setID( exerciseDefinitionService.persist( new PinkLabsDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "pinklabs?" ) ) );
		p2poneDefinitionStub.setID( exerciseDefinitionService.persist( new P2POneDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "urltopicture", "theme" ) ) );
		p2ptwoDefinitionStub.setID( exerciseDefinitionService.persist( new P2PTwoDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "question?" ) ) );

		you2meDefinitionStub.setID( exerciseDefinitionService.persist( new You2MeDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, Arrays.asList(
			"question?",
			"counter question?" ) ) ) );

		simpleprototypingDefinitionStub.setID( exerciseDefinitionService.persist( new SimplePrototypingDefinition(
			defaultUserStub,
			TimeUnit.MINUTES,
			2,
			defaultWorkshopDefinitionStub,
			"prototyping question",
			"my mimetype" ) ) );

		compressionDefinitionStub.setID( exerciseDefinitionService.persist( new CompressionDefinition(
			defaultUserStub,
			TimeUnit.HOURS,
			1,
			defaultWorkshopDefinitionStub,
			"compression question",
			Arrays.asList( "solution criteria 1", "solution criteria 2" ) ) ) );

		evaluationDefinitionStub
			.setID( exerciseDefinitionService.persist( new EvaluationDefinition( defaultUserStub, TimeUnit.MINUTES, 10, defaultWorkshopDefinitionStub, "evaluation question", 3 ) ) );

		evaluationResultDefinitionStub.setID( exerciseDefinitionService.persist( new EvaluationResultDefinition( defaultUserStub, TimeUnit.MINUTES, 10, defaultWorkshopDefinitionStub ) ) );

		xinixImageStub.setID( exerciseDataService.persist( new XinixImage( defaultUserStub, null, "http://www.whatnextpawan.com/wp-content/uploads/2014/03/oh-yes-its-free.png" ) ) );
		List< XinixImage > images = new ArrayList<>();
		images.add( (XinixImage)exerciseDataService.findByID( xinixImageStub.getID() ) );
		xinixImageMatrixStub.setID( exerciseDefinitionService.persist( new XinixImageMatrix( defaultUserStub, null, 0, defaultWorkshopDefinitionStub, images ) ) );

		xinixDefinitionStub.setID( exerciseDefinitionService.persist( new XinixDefinition(
			defaultUserStub,
			TimeUnit.SECONDS,
			60,
			defaultWorkshopDefinitionStub,
			"xinix question",
			(XinixImageMatrix)exerciseDefinitionService.findByID( xinixImageMatrixStub.getID() ) ) ) );

		// exercises
		posterExerciseStub
			.setID( exerciseService.persist( new ExerciseImpl( "start", "start workshop", posterDefinitionStub, (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ) ) );

		pinklabsExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "pinklabs", "pinklabs exercise", pinklabsDefinitionStub, (WorkshopImpl)workshopService.findByID( defaultWorkshopStub
			.getID() ) ) ) );

		p2pOneExerciseStub
			.setID( exerciseService.persist( new ExerciseImpl( "p2pone", "p2pone exercise", p2poneDefinitionStub, (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ) ) );

		you2meExerciseStub
			.setID( exerciseService.persist( new ExerciseImpl( "you2me", "you2me exercise", you2meDefinitionStub, (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ) ) );

		p2pTwoExerciseStub
			.setID( exerciseService.persist( new ExerciseImpl( "p2ptwo", "p2ptwo exercise", p2ptwoDefinitionStub, (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ) ) );

		simpleprototypingExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "simple proto", "simple proto exercise", simpleprototypingDefinitionStub, (WorkshopImpl)workshopService
			.findByID( defaultWorkshopStub.getID() ) ) ) );

		xinixExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "xinix", "xinix exercise", xinixDefinitionStub, (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ) ) );

		compressionExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "compression", "compression exercise", compressionDefinitionStub, (WorkshopImpl)workshopService
			.findByID( defaultWorkshopStub.getID() ) ) ) );

		evaluationExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "evaluation", "evaluation exercise", evaluationDefinitionStub, (WorkshopImpl)workshopService
			.findByID( defaultWorkshopStub.getID() ) ) ) );

		evaluationResultExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "evaluation result", "evaluation result exercise", evaluationResultDefinitionStub, (WorkshopImpl)workshopService
			.findByID( defaultWorkshopStub.getID() ) ) ) );

		// session
		defaultSessionStub.setID( sessionService.persist( new SessionImpl( "session", "test session", null, defaultWorkshopStub ) ) );

		// set default user's session to newly configured session for testing
		// also have secondDefaultUser join this session
		sessionService.join( new Invitation( null, defaultUserStub, defaultSessionStub ) );
		sessionService.join( new Invitation( null, secondUserStub, defaultSessionStub ) );

		// invitation
		defaultInvitationStub.setID( invitationService.persist( new Invitation( defaultUserStub, defaultUserStub, defaultSessionStub ) ) );
	}

	@Test
	public void crudOperationsUserService()
	{
		// create role
		String roleID = roleService.persist( new RoleImpl( "new role", "new role description" ) );
		assertTrue( !roleID.equalsIgnoreCase( "" ) );

		// read role
		RoleImpl role = roleService.findByID( roleID );
		assertTrue( role.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( role.getID().equals( roleID ) );
		assertTrue( role.getName().equalsIgnoreCase( "new role" ) );
		assertTrue( role.getDescription().equalsIgnoreCase( "new role description" ) );

		// update role
		role.setName( "updated role" );
		roleService.persist( role );
		RoleImpl updatedRole = roleService.findByID( roleID );
		assertTrue( updatedRole.getName().equalsIgnoreCase( "updated role" ) );

		// delete role
		int nbrRolesBefore = roleService.findAll().size();
		roleService.remove( roleService.findByID( roleID ) );

		// find all checks delete
		List< RoleImpl > roles = roleService.findAll();
		assertTrue( roles.size() > 0 );
		assertTrue( nbrRolesBefore - roles.size() == 1 );

		// create user
		String userID = userService.persist( new UserImpl( new PasswordCredentialImpl( "my password" ), defaultRoleStub, null, "firstname", "lastname", "login@name" ) );

		// read user
		UserImpl user = userService.findByID( userID );
		assertTrue( user.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( user.getID().equals( userID ) );
		assertTrue( user.getFirstName().equalsIgnoreCase( "firstname" ) );
		assertTrue( user.getLastName().equalsIgnoreCase( "lastname" ) );
		assertTrue( user.getLoginName().equalsIgnoreCase( "login@name" ) );
		assertTrue( user.getCredential().getPassword().equalsIgnoreCase( "my password" ) );

		// update user
		user.setFirstName( "updated firstname" );
		userService.persist( user );
		UserImpl updatedUser = userService.findByID( userID );
		assertTrue( updatedUser.getFirstName().equalsIgnoreCase( "updated firstname" ) );

		// delete user
		int usersBefore = userService.findAll().size();
		userService.remove( userService.findByID( userID ) );

		// find all checks delete
		List< UserImpl > users = userService.findAll();
		assertTrue( users.size() > 0 );
		assertTrue( usersBefore - users.size() == 1 );

	}

	@Test
	public void crudOperationsWorkshopService()
	{
		// create workshop definition
		String workshopDefinitionID = workshopDefinitionService.persist( new PinkElefantDefinition( defaultUserStub, "workshop definition", "workshop definition description", "problem" ) );

		// read workshop definition
		WorkshopDefinitionImpl workshopDefinition = workshopDefinitionService.findByID( workshopDefinitionID );
		assertTrue( workshopDefinition.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( workshopDefinition.getID().equals( workshopDefinitionID ) );
		assertTrue( workshopDefinition.getName().equalsIgnoreCase( "workshop definition" ) );
		assertTrue( workshopDefinition.getDescription().equalsIgnoreCase( "workshop definition description" ) );
		assertTrue( workshopDefinition.getOwner().getID().equals( defaultUserStub.getID() ) );

		// update workshop definition
		workshopDefinition.setName( "updated workshop definition" );
		workshopDefinitionService.persist( workshopDefinition );
		WorkshopDefinitionImpl updatedWorkshopDefinition = workshopDefinitionService.findByID( workshopDefinitionID );
		assertTrue( updatedWorkshopDefinition.getName().equalsIgnoreCase( "updated workshop definition" ) );

		// delete workshop definition
		int wsDefBefore = workshopDefinitionService.findAll().size();
		workshopDefinitionService.remove( workshopDefinitionService.findByID( workshopDefinitionID ) );

		// find all checks delete
		List< WorkshopDefinitionImpl > wsDefs = workshopDefinitionService.findAll();
		assertTrue( wsDefs.size() > 0 );
		assertTrue( wsDefBefore - wsDefs.size() == 1 );

		// create workshop instance
		String wsID = workshopService.persist( new WorkshopImpl( "workshop instance", "workshop instance description", defaultWorkshopDefinitionStub ) );

		// read workshop instance
		WorkshopImpl ws = workshopService.findByID( wsID );
		assertTrue( ws.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( ws.getID().equals( wsID ) );
		assertTrue( ws.getName().equalsIgnoreCase( "workshop instance" ) );
		assertTrue( ws.getDescription().equalsIgnoreCase( "workshop instance description" ) );
		assertTrue( ws.getCurrentState().equalsIgnoreCase( "new" ) );
		assertTrue( ws.getDefinition().getID().equals( defaultWorkshopDefinitionStub.getID() ) );

		// update workshop instance
		ws.setName( "updated workshop instance" );
		workshopService.persist( ws );
		WorkshopImpl updatedWs = workshopService.findByID( wsID );
		assertTrue( updatedWs.getName().equalsIgnoreCase( "updated workshop instance" ) );

		// delete workshop instance
		int wsBefore = workshopService.findAll().size();
		workshopService.remove( updatedWs );

		// find all checks delete
		List< WorkshopImpl > workshops = workshopService.findAll();
		assertTrue( workshops.size() > 0 );
		assertTrue( wsBefore - workshops.size() == 1 );

		// create session
		String sessionID = sessionService.persist( new SessionImpl( "session instance", "session description", null, defaultWorkshopStub ) );

		// read session
		SessionImpl session = sessionService.findByID( sessionID );
		assertTrue( session.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( session.getID().equals( sessionID ) );
		assertTrue( session.getName().equalsIgnoreCase( "session instance" ) );
		assertTrue( session.getDescription().equalsIgnoreCase( "session description" ) );
		assertTrue( session.getDefinition() == null );
		assertTrue( session.getWorkshop().getID().equals( defaultWorkshopStub.getID() ) );

		// update session
		session.setName( "updated session" );
		sessionService.persist( session );
		SessionImpl updatedSession = sessionService.findByID( sessionID );
		assertTrue( updatedSession.getName().equalsIgnoreCase( "updated session" ) );

		// delete session
		int sessionsBefore = sessionService.findAll().size();
		sessionService.remove( updatedSession );

		// find all checks delete
		List< SessionImpl > sessions = sessionService.findAll();
		assertTrue( sessions.size() > 0 );
		assertTrue( sessionsBefore - sessions.size() == 1 );
	}

	@Test
	public void crudOperationsExerciseService()
	{
		// create exercise definition
		String exDefID = exerciseDefinitionService.persist( new PinkLabsDefinition( defaultUserStub, TimeUnit.MINUTES, 2, defaultWorkshopDefinitionStub, "question" ) );

		// read exercise definition
		ExerciseDefinitionImpl exDef = exerciseDefinitionService.findByID( exDefID );
		assertTrue( exDef.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( exDef.getID().equals( exDefID ) );
		assertTrue( exDef.getDuration() == 2 );
		assertTrue( exDef.getOwner().getID().equals( defaultUserStub.getID() ) );
		assertTrue( exDef.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( exDef.getWorkshopDefinition().getID().equals( defaultWorkshopDefinitionStub.getID() ) );
		assertTrue( ( (PinkLabsDefinition)exDef ).getQuestion().equalsIgnoreCase( "question" ) );

		// update exercise definition
		exDef.setDuration( 12 );
		exerciseDefinitionService.persist( exDef );
		ExerciseDefinitionImpl updatedExDef = exerciseDefinitionService.findByID( exDefID );
		assertTrue( updatedExDef.getDuration() == 12 );

		// delete exercise definition
		int exDefBefore = exerciseDefinitionService.findAll().size();
		exerciseDefinitionService.remove( updatedExDef );

		// find all checks delete
		List< ExerciseDefinitionImpl > exDefs = exerciseDefinitionService.findAll();
		assertTrue( exDefs.size() > 0 );
		assertTrue( exDefBefore - exDefs.size() == 1 );

		// create exercise instance
		String exID = exerciseService.persist( new ExerciseImpl( "exercise", "exercise description", pinklabsDefinitionStub, defaultWorkshopStub ) );

		// read exercise instance
		ExerciseImpl ex = exerciseService.findByID( exID );
		assertTrue( ex.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( ex.getID().equals( exID ) );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "new" ) );
		assertTrue( ex.getDefinition().getID().equals( pinklabsDefinitionStub.getID() ) );
		assertTrue( ex.getName().equalsIgnoreCase( "exercise" ) );
		assertTrue( ex.getDescription().equalsIgnoreCase( "exercise description" ) );
		assertTrue( ex.getWorkshop().getID().equals( defaultWorkshopStub.getID() ) );
		assertTrue( ex.getOrderInWorkshop() == 10 );

		// update exercise instance
		ex.setName( "updated exercise" );
		exerciseService.persist( ex );
		ExerciseImpl updatedEx = exerciseService.findByID( exID );
		assertTrue( updatedEx.getName().equalsIgnoreCase( "updated exercise" ) );

		// delete exercise instance
		int exBefore = exerciseService.findAll().size();
		exerciseService.remove( updatedEx );

		// find all checks delete
		List< ExerciseImpl > exs = exerciseService.findAll();
		assertTrue( exs.size() > 0 );
		assertTrue( exBefore - exs.size() == 1 );
	}

	/**
	 * helper method for validating exercise data objects (ID, Client, Owner, WorkflowElement)
	 */
	private void checkExerciseData( ExerciseImpl exercise, ExerciseDataImpl data, ExerciseDataImpl dataStub )
	{
		assertTrue( data.getID().equalsIgnoreCase( dataStub.getID() ) );
		assertTrue( data.getClient().getID().equalsIgnoreCase( defaultClientStub.getID() ) );
		assertTrue( data.getOwner().getID().equalsIgnoreCase( defaultUserStub.getID() ) );
		assertTrue( data.getWorkflowElement().getID().equalsIgnoreCase( exercise.getID() ) );
	}

	@Test
	public void crudOperationsPinklabsExerciseData()
	{
		// create
		pinklabsDataStub.setID( exerciseDataService.persist( new PinkLabsExerciseData( defaultUserStub, pinklabsExerciseStub, Arrays.asList( "answer1", "answer2", "answer3" ) ) ) );

		// read
		PinkLabsExerciseData data = exerciseDataService.findByID( pinklabsDataStub.getID() );
		checkExerciseData( pinklabsExerciseStub, data, pinklabsDataStub );
		assertTrue( data.getAnswers().containsAll( Arrays.asList( "answer1", "answer2", "answer3" ) ) );

		// update

		// delete

	}

	@Test
	public void crudOperationsP2POneExerciseData()
	{
		// create
		p2poneDataStub.setID( exerciseDataService.persist( new P2POneData( defaultUserStub, p2pOneExerciseStub, Arrays.asList( "keyword1", "keyword2", "keyword3" ) ) ) );

		// read
		P2POneData data = exerciseDataService.findByID( p2poneDataStub.getID() );
		checkExerciseData( p2pOneExerciseStub, data, p2poneDataStub );

		int count = 0;

		for ( P2POneKeyword keyword : data.getKeywords() )
		{
			if ( keyword.getKeyword().equalsIgnoreCase( "keyword1" ) || keyword.getKeyword().equalsIgnoreCase( "keyword2" ) || keyword.getKeyword().equalsIgnoreCase( "keyword3" ) )
			{
				count += 1;
			}
		}

		assertTrue( count == 3 );

		// update

		// delete

	}

	@Test
	public void crudOperationsYou2MeExerciseData()
	{
		// create
		DialogEntry dialogEntry1 = new DialogEntry( DialogRole.RoleA, "what" );
		DialogEntry dialogEntry2 = new DialogEntry( DialogRole.RoleB, "why" );
		you2meDataStub.setID( exerciseDataService.persist( new You2MeExerciseData( defaultUserStub, you2meExerciseStub, Arrays.asList( dialogEntry1, dialogEntry2 ) ) ) );

		// read
		You2MeExerciseData data = exerciseDataService.findByID( you2meDataStub.getID() );
		checkExerciseData( you2meExerciseStub, data, you2meDataStub );

		int count = 0;

		for ( DialogEntry entry : data.getDialog() )
		{
			if ( entry.getRole().toString().equalsIgnoreCase( DialogRole.RoleA.toString() ) && entry.getText().equalsIgnoreCase( "what" ) )
			{
				count += 1;
			}

			if ( entry.getRole().toString().equalsIgnoreCase( DialogRole.RoleB.toString() ) && entry.getText().equalsIgnoreCase( "why" ) )
			{
				count += 1;
			}
		}

		assertTrue( count == 2 );

		// update

		// delete

	}

	@Test
	public void crudOperationsP2PTwoExerciseData()
	{
		// create
		P2POneData p2pOneData = exerciseDataService.findByID( exerciseDataService.persist( new P2POneData( defaultUserStub, p2pOneExerciseStub, Arrays.asList( "key1", "key2" ) ) ) );
		Set< P2POneKeyword > selectedKeywords = new HashSet<>();
		selectedKeywords.add( p2pOneData.getKeywords().get( 0 ) );
		selectedKeywords.add( p2pOneData.getKeywords().get( 1 ) );
		p2ptwoDataStub.setID( exerciseDataService.persist( new P2PTwoData( defaultUserStub, p2pTwoExerciseStub, Arrays.asList( "answer1", "answer2" ), selectedKeywords ) ) );

		// read
		P2PTwoData data = exerciseDataService.findByID( p2ptwoDataStub.getID() );
		checkExerciseData( p2pTwoExerciseStub, data, p2ptwoDataStub );
		assertTrue( data.getAnswers().containsAll( Arrays.asList( "answer1", "answer2" ) ) );

		int count = 0;

		for ( P2POneKeyword keyword : data.getSelectedKeywords() )
		{
			if ( keyword.getKeyword().equalsIgnoreCase( "key1" ) || keyword.getKeyword().equalsIgnoreCase( "key2" ) )
			{
				count += 1;
			}
		}

		assertTrue( count == 2 );

		// update

		// delete

	}

	@Test
	public void crudOperationsXinixExerciseData()
	{
		// create
		Set< String > associations = new HashSet<>();
		associations.addAll( Arrays.asList( "assoc1", "assoc2", "assoc3" ) );
		xinixDataStub.setID( exerciseDataService.persist( new XinixData( defaultUserStub, xinixExerciseStub, associations, xinixImageStub ) ) );

		// read
		XinixData data = exerciseDataService.findByID( xinixDataStub.getID() );
		checkExerciseData( xinixExerciseStub, data, xinixDataStub );
		assertTrue( data.getXinixImage().getID().equalsIgnoreCase( xinixImageStub.getID() ) );
		assertTrue( data.getAssociations().containsAll( associations ) );

		// update

		// delete

	}

	@Test
	public void crudOperationsSimplePrototypeExerciseData()
	{
		// create
		simpleprototypingDataStub.setID( exerciseDataService.persist( new SimplePrototypingData( defaultUserStub, simpleprototypingExerciseStub, "blob".getBytes() ) ) );

		// read
		SimplePrototypingData data = exerciseDataService.findByID( simpleprototypingDataStub.getID() );
		checkExerciseData( simpleprototypingExerciseStub, data, simpleprototypingDataStub );
		assertTrue( Arrays.equals( "blob".getBytes(), data.getBlob() ) );

		// update

		// delete

	}

	@Test
	public void crudOperationsCompressionExerciseData()
	{
		// create
		compressionDataStub.setID( exerciseDataService.persist( new CompressionExerciseData( defaultUserStub, compressionExerciseStub, Arrays.asList( new CompressionExerciseDataElement(
			"sol1",
			"desc1" ), new CompressionExerciseDataElement( "sol2", "desc2" ), new CompressionExerciseDataElement( "sol3", "desc3" ) ) ) ) );

		// read
		CompressionExerciseData data = exerciseDataService.findByID( compressionDataStub.getID() );
		checkExerciseData( compressionExerciseStub, data, compressionDataStub );

		int count = 0;

		for ( CompressionExerciseDataElement sol : data.getSolutions() )
		{
			if ( sol.getSolution().equals( "sol1" ) && sol.getDescription().equals( "desc1" ) || sol.getSolution().equals( "sol2" ) && sol.getDescription().equals( "desc2" )
					|| sol.getSolution().equals( "sol3" ) && sol.getDescription().equals( "desc3" ) )
			{
				count += 1;
			}
		}

		assertTrue( count == 3 );

		// update

		// delete

	}

	@Test
	public void crudOperationsEvaluationExerciseData()
	{
		CompressionExerciseData compressionData = exerciseDataService.findByID( compressionDataStub.getID() );
		CompressionExerciseDataElement compressionDataElement = compressionData.getSolutions().get( 0 );

		// create
		evaluationDataStub.setID( exerciseDataService.persist( new EvaluationExerciseData( defaultUserStub, evaluationExerciseStub, new Evaluation( defaultUserStub, compressionDataElement, new Score(
			defaultUserStub,
			3 ) ) ) ) );

		// read
		EvaluationExerciseData data = exerciseDataService.findByID( evaluationDataStub.getID() );
		checkExerciseData( evaluationExerciseStub, data, evaluationDataStub );

		assertTrue( data.getEvaluation().getScore().getScore() == 3 && data.getEvaluation().getSolution().getID().equals( compressionDataElement.getID() ) );
	}

	@Test
	public void crudOperationsInvitationService()
	{

		// create invitation
		String invitationID = invitationService.persist( new Invitation( defaultUserStub, defaultUserStub, defaultSessionStub ) );

		// read invitation
		Invitation invitation = invitationService.findByID( invitationID );
		assertTrue( invitation.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( invitation.getID().equals( invitationID ) );
		assertTrue( invitation.getDate() != null );
		assertTrue( invitation.getInvitee().getID().equals( defaultUserStub.getID() ) );
		assertTrue( invitation.getInviter().getID().equals( defaultUserStub.getID() ) );
		assertTrue( invitation.getSession().getID().equals( defaultSessionStub.getID() ) );

		// update invitation
		Date date = new Date();
		invitation.setDate( date );
		invitationService.persist( invitation );
		Invitation updatedInvitation = invitationService.findByID( invitationID );
		assertTrue( updatedInvitation.getDate().getTime() == date.getTime() );

		// delete invitation
		int invitBefore = invitationService.findAll().size();
		invitationService.remove( invitation );

		// find all check delete
		List< Invitation > invitations = invitationService.findAll();
		assertTrue( invitations.size() > 0 );
		assertTrue( invitBefore - invitations.size() == 1 );
	}

	@Test
	public void functionalUserService()
	{
		// find by login name
		PrincipalImpl usr = userService.findByID( defaultUserStub.getID() );
		PrincipalImpl searched = userService.findByLoginName( ( (UserImpl)usr ).getLoginName() );
		assertTrue( searched.getID().equals( usr.getID() ) );

		// request new password
		// assertTrue( userService.requestNewPassword( defaultUserStub.getID() ) );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void getInputSetOutputgetOutputgetOutputByExerciseID() throws IOException
	{
		Output output = null;
		List< String > p2pOneKeywordStrings = new ArrayList<>();
		boolean success = false;

		// start workshop
		setExerciseOnDefaultSession( posterExerciseStub );
		PosterInput posterInput = mapper.readValue( exerciseService.getInputAsString(), PosterInput.class );
		assertTrue( posterInput.getTitle().equalsIgnoreCase( ( (PosterDefinition)exerciseDefinitionService.findByID( posterDefinitionStub.getID() ) ).getTitle() ) );
		assertTrue( posterInput.getDescription().equalsIgnoreCase( ( (PosterDefinition)exerciseDefinitionService.findByID( posterDefinitionStub.getID() ) ).getDescription() ) );

		// pinklabs
		setExerciseOnDefaultSession( pinklabsExerciseStub );
		PinkLabsInput pinklabsInput = mapper.readValue( exerciseService.getInputAsString(), PinkLabsInput.class );
		assertTrue( pinklabsInput.getQuestion().equalsIgnoreCase( ( (PinkLabsDefinition)exerciseDefinitionService.findByID( pinklabsDefinitionStub.getID() ) ).getQuestion() ) );

		output = new PinkLabsOutput( "", Arrays.asList( "answer1", "answer2", "answer3" ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > data = exerciseDataService.findByExerciseID( pinklabsExerciseStub.getID() );
		List< PinkLabsExerciseData > prepedData = mapper.readValue( mapper.writeValueAsString( data ), makeCollectionType( PinkLabsExerciseData.class ) );

		for ( PinkLabsExerciseData d : prepedData )
		{
			if ( d.getAnswers().containsAll( Arrays.asList( "answer1", "answer2", "answer3" ) ) )
			{
				success = true;
				break;
			}
		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( data ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( data ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutputByExerciseID( pinklabsExerciseStub.getID() ) ), makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// p2pone
		setExerciseOnDefaultSession( p2pOneExerciseStub );
		P2POneInput p2pOneInput = mapper.readValue( exerciseService.getInputAsString(), P2POneInput.class );
		assertTrue( p2pOneInput.getQuestion().equalsIgnoreCase( ( (P2POneDefinition)exerciseDefinitionService.findByID( p2poneDefinitionStub.getID() ) ).getQuestion() ) );
		assertTrue( p2pOneInput.getPicture().equalsIgnoreCase( ( (P2POneDefinition)exerciseDefinitionService.findByID( p2poneDefinitionStub.getID() ) ).getPicture() ) );

		output = new P2POneOutput( Arrays.asList( "p1", "p2", "p3" ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > p2pOneData = exerciseDataService.findByExerciseID( p2pOneExerciseStub.getID() );
		List< P2POneData > p2pOneDataPrepped = mapper.readValue( mapper.writeValueAsString( p2pOneData ), makeCollectionType( P2POneData.class ) );

		for ( P2POneData d : p2pOneDataPrepped )
		{
			int count = 0;

			for ( P2POneKeyword keyword : ( (P2POneData)d ).getKeywords() )
			{
				p2pOneKeywordStrings.add( keyword.getKeyword() );

				if ( keyword.getKeyword().equalsIgnoreCase( "p1" ) || keyword.getKeyword().equalsIgnoreCase( "p2" ) || keyword.getKeyword().equalsIgnoreCase( "p3" ) )
				{
					count += 1;
				}

				if ( count == 3 )
				{
					success = true;
					break;
				}
			}
		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( p2pOneData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( p2pOneData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutputByExerciseID( p2pOneExerciseStub.getID() ) ), makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// you2me
		setExerciseOnDefaultSession( you2meExerciseStub );
		String you2meInputString = exerciseService.getInputAsString();
		You2MeInput you2meInput = mapper.readValue( you2meInputString, You2MeInput.class );

		for ( String string : ( (You2MeDefinition)exerciseDefinitionService.findByID( you2meDefinitionStub.getID() ) ).getQuestions() )
		{
			assertTrue( you2meInput.getQuestions().contains( string ) );
		}

		output = new You2MeOutput( Arrays.asList( new DialogEntry( DialogRole.RoleA, "roleA" ), new DialogEntry( DialogRole.RoleB, "roleB" ) ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > you2meData = exerciseDataService.findByExerciseID( you2meExerciseStub.getID() );
		List< You2MeExerciseData > you2meDataPrepped = mapper.readValue( mapper.writeValueAsString( you2meData ), makeCollectionType( You2MeExerciseData.class ) );

		for ( You2MeExerciseData d : you2meDataPrepped )
		{
			int count = 0;

			for ( DialogEntry entry : d.getDialog() )
			{
				if ( entry.getRole().toString().equalsIgnoreCase( DialogRole.RoleA.toString() ) && entry.getText().equalsIgnoreCase( "roleA" ) )
				{
					count += 1;
				}

				if ( entry.getRole().toString().equalsIgnoreCase( DialogRole.RoleB.toString() ) && entry.getText().equalsIgnoreCase( "roleB" ) )
				{
					count += 1;
				}

				if ( count == 2 )
				{
					success = true;
					break;
				}
			}

		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( you2meData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( you2meData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutputByExerciseID( you2meExerciseStub.getID() ) ), makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// p2ptwo
		setExerciseOnDefaultSession( p2pTwoExerciseStub );
		String p2ptwoInputString = exerciseService.getInputAsString();
		P2PTwoInput p2ptwoInput = mapper.readValue( p2ptwoInputString, P2PTwoInput.class );

		assertTrue( p2ptwoInput.getQuestion().equalsIgnoreCase( ( (P2PTwoDefinition)exerciseDefinitionService.findByID( p2ptwoDefinitionStub.getID() ) ).getQuestion() ) );

		List< ExerciseDataImpl > p2pOneDataForP2PTwoTest = exerciseDataService.findByExerciseID( p2pOneExerciseStub.getID() );
		List< P2POneData > p2pOneDataForP2PTwoTestPrepped = mapper.readValue( mapper.writeValueAsString( p2pOneDataForP2PTwoTest ), makeCollectionType( P2POneData.class ) );

		List< String > p2p2InputKeywordStrings = new ArrayList< String >();
		List< String > p2p2InputKeywordIDs = new ArrayList< String >();

		for ( P2PKeywordInput keywordInput : p2ptwoInput.getCascade1Keywords() )
		{
			p2p2InputKeywordIDs.add( keywordInput.getId() );
			p2p2InputKeywordStrings.add( keywordInput.getKeyword() );
		}

		for ( P2POneData d : p2pOneDataForP2PTwoTestPrepped )
		{
			for ( P2POneKeyword keyword : d.getKeywords() )
			{
				assertTrue( p2p2InputKeywordIDs.contains( keyword.getID() ) && p2p2InputKeywordStrings.contains( keyword.getKeyword() ) );
			}
		}

		Set< String > cascade1Keywords = new HashSet<>();
		cascade1Keywords.add( p2pOneKeywordStrings.get( 0 ) );
		cascade1Keywords.add( p2pOneKeywordStrings.get( 1 ) );

		output = new P2PTwoOutput( cascade1Keywords, Arrays.asList( "p21", "p22", "p23" ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > p2ptwoData = exerciseDataService.findByExerciseID( p2pTwoExerciseStub.getID() );
		List< P2PTwoData > p2ptwoDataPrepped = mapper.readValue( mapper.writeValueAsString( p2ptwoData ), makeCollectionType( P2PTwoData.class ) );

		for ( P2PTwoData d : p2ptwoDataPrepped )
		{
			if ( d.getAnswers().containsAll( Arrays.asList( "p21", "p22", "p23" ) ) )
			{
				for ( P2POneKeyword keyword : d.getSelectedKeywords() )
				{
					assertTrue( cascade1Keywords.contains( keyword.getKeyword() ) );
				}

				success = true;
			}
		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( p2ptwoData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( p2ptwoData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutputByExerciseID( p2pTwoExerciseStub.getID() ) ), makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// simple prototyping
		setExerciseOnDefaultSession( simpleprototypingExerciseStub );
		SimplePrototypingInput simpleprotoInput = mapper.readValue( exerciseService.getInputAsString(), SimplePrototypingInput.class );
		assertTrue( simpleprotoInput.getQuestion().equalsIgnoreCase( ( (SimplePrototypingDefinition)exerciseDefinitionService.findByID( simpleprototypingDefinitionStub.getID() ) ).getQuestion() ) );
		assertTrue( simpleprotoInput.getMimeType().equalsIgnoreCase( ( (SimplePrototypingDefinition)exerciseDefinitionService.findByID( simpleprototypingDefinitionStub.getID() ) ).getMimeType() ) );

		output = new SimplePrototypingOutput( "simpleprototyping".getBytes() );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > simpleProtoData = exerciseDataService.findByExerciseID( simpleprototypingExerciseStub.getID() );
		List< SimplePrototypingData > simpleProtoDataPrepped = mapper.readValue( mapper.writeValueAsString( simpleProtoData ), makeCollectionType( SimplePrototypingData.class ) );

		for ( SimplePrototypingData d : simpleProtoDataPrepped )
		{
			if ( Arrays.equals( "simpleprototyping".getBytes(), d.getBlob() ) )
			{
				success = true;
				break;
			}
		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( simpleProtoData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( simpleProtoData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue(
				mapper.writeValueAsString( exerciseService.getOutputByExerciseID( simpleprototypingExerciseStub.getID() ) ),
				makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// xinix
		setExerciseOnDefaultSession( xinixExerciseStub );
		XinixInput xinixInput = mapper.readValue( exerciseService.getInputAsString(), XinixInput.class );
		assertTrue( xinixInput.getQuestion().equalsIgnoreCase( ( (XinixDefinition)exerciseDefinitionService.findByID( xinixDefinitionStub.getID() ) ).getQuestion() ) );
		assertTrue( xinixInput.getXinixImages().getID().equalsIgnoreCase( xinixImageMatrixStub.getID() ) );

		List< String > imageIDs = new ArrayList<>();
		for ( XinixImage img : ( xinixInput ).getXinixImages().getXinixImages() )
		{
			imageIDs.add( img.getID() );
		}

		for ( XinixImage img : ( (XinixImageMatrix)exerciseDefinitionService.findByID( xinixImageMatrixStub.getID() ) ).getXinixImages() )
		{
			assertTrue( imageIDs.contains( img.getID() ) );
		}

		output = new XinixOutput( new HashSet<>( Arrays.asList( "x1", "x2", "x3" ) ), xinixImageStub );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > xinixData = exerciseDataService.findByExerciseID( xinixExerciseStub.getID() );
		List< XinixData > xinixDataPrepped = mapper.readValue( mapper.writeValueAsString( xinixData ), makeCollectionType( XinixData.class ) );

		for ( XinixData d : xinixDataPrepped )
		{
			int count = 0;

			if ( d.getXinixImage().getID().equalsIgnoreCase( xinixImageStub.getID() ) )
			{
				for ( String association : d.getAssociations() )
				{
					if ( association.equalsIgnoreCase( "x1" ) || association.equalsIgnoreCase( "x2" ) || association.equalsIgnoreCase( "x3" ) )
					{
						count += 1;
					}

					if ( count == 3 )
					{
						success = true;
						break;
					}
				}
			}
		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( xinixData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( xinixData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutputByExerciseID( xinixExerciseStub.getID() ) ), makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// compression
		setExerciseOnDefaultSession( compressionExerciseStub );
		CompressionInput compressionInput = mapper.readValue( exerciseService.getInputAsString(), CompressionInput.class );
		assertTrue( compressionInput.getQuestion().equalsIgnoreCase( ( (CompressionDefinition)exerciseDefinitionService.findByID( compressionDefinitionStub.getID() ) ).getQuestion() ) );
		assertTrue( compressionInput.getCompressableExerciseData().size() > 0 );

		for ( String criterion : compressionInput.getSolutionCriteria() )
		{
			( (CompressionDefinition)exerciseDefinitionService.findByID( compressionDefinitionStub.getID() ) ).getSolutionCriteria().contains( criterion );
		}

		output = new CompressionOutput( Arrays.asList( new CompressionOutputElement( "s1", "d1" ), new CompressionOutputElement( "s2", "d2" ), new CompressionOutputElement( "s3", "d3" ) ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > compressionData = exerciseDataService.findByExerciseID( compressionExerciseStub.getID() );
		List< CompressionExerciseData > compressionDataPrepped = mapper.readValue( mapper.writeValueAsString( compressionData ), makeCollectionType( CompressionExerciseData.class ) );

		for ( CompressionExerciseData d : compressionDataPrepped )
		{
			int count = 0;

			for ( CompressionExerciseDataElement el : d.getSolutions() )
			{
				if ( el.getSolution().equalsIgnoreCase( "s1" ) && el.getDescription().equalsIgnoreCase( "d1" ) || el.getSolution().equalsIgnoreCase( "s2" )
						&& el.getDescription().equalsIgnoreCase( "d2" ) || el.getSolution().equalsIgnoreCase( "s3" ) && el.getDescription().equalsIgnoreCase( "d3" ) )
				{
					count += 1;
				}

			}

			if ( count == 3 )
			{
				success = true;
			}

		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( compressionData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( compressionData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue(
				mapper.writeValueAsString( exerciseService.getOutputByExerciseID( compressionExerciseStub.getID() ) ),
				makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// evaluation
		setExerciseOnDefaultSession( evaluationExerciseStub );
		EvaluationInput evaluationInput = mapper.readValue( exerciseService.getInputAsString(), EvaluationInput.class );
		assertTrue( evaluationInput.getQuestion().equalsIgnoreCase( ( (EvaluationDefinition)exerciseDefinitionService.findByID( evaluationDefinitionStub.getID() ) ).getQuestion() ) );
		assertTrue( evaluationInput.getNumberOfVotes() == ( (EvaluationDefinition)exerciseDefinitionService.findByID( evaluationDefinitionStub.getID() ) ).getNumberOfVotes() );

		List< CompressionExerciseData > dataFromCompression = mapper.readValue(
			mapper.writeValueAsString( exerciseDataService.findByExerciseID( compressionExerciseStub.getID() ) ),
			makeCollectionType( CompressionExerciseData.class ) );

		int c = 0;

		for ( CompressionExerciseData d : dataFromCompression )
		{
			for ( CompressionExerciseDataElement sol : d.getSolutions() )
			{
				if ( sol.getSolution().equals( "s1" ) && sol.getDescription().equals( "d1" ) || sol.getSolution().equals( "s2" ) && sol.getDescription().equals( "d2" )
						|| sol.getSolution().equals( "s3" ) && sol.getDescription().equals( "d3" ) )
				{
					c += 1;
				}

			}
		}

		assertTrue( c >= 3 );

		List< EvaluationOutputElement > evaluationsForOutput = new ArrayList<>();
		EvaluationOutputElement eval = new EvaluationOutputElement( "s1", "d1", 5 );
		evaluationsForOutput.add( eval );

		output = new EvaluationOutput( evaluationsForOutput );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > evaluationData = exerciseDataService.findByExerciseID( evaluationExerciseStub.getID() );
		List< EvaluationExerciseData > evaluationDataPrepped = mapper.readValue( mapper.writeValueAsString( evaluationData ), makeCollectionType( EvaluationExerciseData.class ) );

		for ( EvaluationExerciseData d : evaluationDataPrepped )
		{
			int count = 0;

			if ( d.getEvaluation().getSolution().getSolution().equals( "s1" ) && d.getEvaluation().getSolution().getDescription().equals( "d1" ) && d.getEvaluation().getScore().getScore() == 5 )
			{
				count += 1;
			}

			if ( count == 1 )
			{
				success = true;
				break;
			}
		}

		assertTrue( success );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( evaluationData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( exerciseService.getOutput() ), makeCollectionType( ExerciseDataImpl.class ) ) ) );
		assertTrue( checkOutput(
			(List< ExerciseDataImpl >)mapper.readValue( mapper.writeValueAsString( evaluationData ), makeCollectionType( ExerciseDataImpl.class ) ),
			(List< ExerciseDataImpl >)mapper.readValue(
				mapper.writeValueAsString( exerciseService.getOutputByExerciseID( evaluationExerciseStub.getID() ) ),
				makeCollectionType( ExerciseDataImpl.class ) ) ) );

		// evaluation result -> only testing getInput, as setOutput and getOutput operations are not supported for this kind of exercise
		// use two newly persisted EvaluationExerciseData objects
		exerciseService.setOutput( mapper.writeValueAsString( new EvaluationOutput( Arrays.asList( new EvaluationOutputElement( "s2", "d2", 6 ) ) ) ) );

		exerciseServiceForSecondUser.setOutput( mapper.writeValueAsString( new EvaluationOutput( Arrays.asList( new EvaluationOutputElement( "s2", "d2", 2 ) ) ) ) );

		setExerciseOnDefaultSession( evaluationResultExerciseStub );
		EvaluationResultInput evaluationResultInput = mapper.readValue( exerciseService.getInputAsString(), EvaluationResultInput.class );

		success = false;

		for ( EvaluationResultObject resultObject : evaluationResultInput.getResults() )
		{
			if ( resultObject.getSolution().getSolution().equalsIgnoreCase( "s2" ) )
			{
				success = true;
				assertTrue( resultObject.getAverageScore() == 4 );
				assertTrue( resultObject.getNumberOfVotes() >= 2 );
				break;
			}
		}

		assertTrue( success );

		// make sure that evaluationResultInput has list of solutions which have not been evaluated
		// in our case we check for solutions s3 which has been evaluated
		boolean cNon = false;
		for ( CompressionInputElement element : evaluationResultInput.getNotEvaluated() )
		{
			if ( element.getSolution().equals( "s3" ) && element.getDescription().equals( "d3" ) )
			{
				cNon = true;
			}
		}

		assertTrue( cNon );
	}

	@Test
	public void getInputSetOutputByExerciseID() throws IOException
	{
		// only testing for one example exercise
		// getInputByExerciseID and setOutputByExerciseID reuse getInput and setOutput methods
		// hence, this test is to ensure that input and output parameters which are used in addition are properly used

		Output output = null;
		boolean success = false;

		// testing for pinklabs, setting currentExercise to different exercise on purpose
		setExerciseOnDefaultSession( compressionExerciseStub );
		PinkLabsInput pinklabsInput = mapper.readValue( exerciseService.getInputByExerciseIDAsString( pinklabsExerciseStub.getID() ), PinkLabsInput.class );
		assertTrue( pinklabsInput.getQuestion().equalsIgnoreCase( ( (PinkLabsDefinition)exerciseDefinitionService.findByID( pinklabsDefinitionStub.getID() ) ).getQuestion() ) );

		output = new PinkLabsOutput( "", Arrays.asList( "answer4", "answer5", "answer6" ) );
		output.setExerciseID( pinklabsExerciseStub.getID() );
		exerciseService.setOuputByExerciseID( mapper.writeValueAsString( output ) );

		List< ExerciseDataImpl > data = exerciseDataService.findByExerciseID( pinklabsExerciseStub.getID() );
		List< PinkLabsExerciseData > prepedData = mapper.readValue( mapper.writeValueAsString( data ), makeCollectionType( PinkLabsExerciseData.class ) );

		for ( PinkLabsExerciseData d : prepedData )
		{
			if ( d.getAnswers().containsAll( Arrays.asList( "answer4", "answer5", "answer6" ) ) )
			{
				success = true;
				break;
			}
		}

		assertTrue( success );
	}

	private CollectionType makeCollectionType( Class< ? > elementClass )
	{
		return TypeFactory.defaultInstance().constructCollectionType( ArrayList.class, elementClass );
	}

	@Test
	public void functionalWorkshopService()
	{
		// start workshop
		workshopService.start( defaultWorkshopStub.getID() );
		assertTrue( ( (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ).getCurrentState().equalsIgnoreCase( "running" ) );

		// stop workshop
		workshopService.stop( defaultWorkshopStub.getID() );
		assertTrue( ( (WorkshopImpl)workshopService.findByID( defaultWorkshopStub.getID() ) ).getCurrentState().equalsIgnoreCase( "terminated" ) );

		// start session
		sessionService.start( defaultSessionStub.getID() );
		assertTrue( ( (SessionImpl)sessionService.findByID( defaultSessionStub.getID() ) ).getCurrentState().equalsIgnoreCase( "running" ) );

		// stop session
		sessionService.stop( defaultSessionStub.getID() );
		assertTrue( ( (SessionImpl)sessionService.findByID( defaultSessionStub.getID() ) ).getCurrentState().equalsIgnoreCase( "terminated" ) );

		// getCurrentExercise
		setExerciseOnDefaultSession( pinklabsExerciseStub );
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( pinklabsExerciseStub.getID() ) );

		// getNextExercise
		@SuppressWarnings( "unused" )
		String nexExID = sessionService.getNextExercise( defaultSessionStub.getID() ).getID();
		assertTrue( sessionService.getNextExercise( defaultSessionStub.getID() ).getID().equals( p2pOneExerciseStub.getID() ) );

		// setNextExercise
		sessionService.setNextExercise( defaultSessionStub.getID() );
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( p2pOneExerciseStub.getID() ) );

		// getPreviousExercise
		assertTrue( sessionService.getPreviousExercise( defaultSessionStub.getID() ).getID().equals( pinklabsExerciseStub.getID() ) );

		// setCurrentExericse
		SessionImpl sessionRequestWrapper = new SessionImpl();
		sessionRequestWrapper.setID( defaultSessionStub.getID() );
		sessionRequestWrapper.setCurrentExercise( compressionExerciseStub );
		sessionService.setCurrentExercise( sessionRequestWrapper );
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( compressionExerciseStub.getID() ) );

		// join Session
		Invitation wrappedJoinRequest = new Invitation();
		wrappedJoinRequest.setInvitee( defaultUserStub );
		wrappedJoinRequest.setSession( defaultSessionStub );

		sessionService.join( wrappedJoinRequest );
		assertTrue( ( (PrincipalImpl)userService.findByID( defaultUserStub.getID() ) ).getSession().getID().equals( defaultSessionStub.getID() ) );

		// leave Session
		sessionService.leave( wrappedJoinRequest );
		assertTrue( ( (PrincipalImpl)userService.findByID( defaultUserStub.getID() ) ).getSession() == null );

		// accept invitation
		invitationService.accept( defaultInvitationStub.getID() );
		assertTrue( checkSetOperation( ( (SessionImpl)sessionService.findByID( defaultSessionStub.getID() ) ).getInvitations(), defaultInvitationStub.getID(), true ) );

		// add executer
		sessionService.addExecuter( wrappedJoinRequest );
		assertTrue( checkSetOperation( ( (SessionImpl)sessionService.findByID( defaultSessionStub.getID() ) ).getExecuters(), defaultUserStub.getID(), false ) );

		// remove executer
		sessionService.removeExecuter( wrappedJoinRequest );
		assertTrue( checkSetOperation( ( (SessionImpl)sessionService.findByID( defaultSessionStub.getID() ) ).getExecuters(), defaultUserStub.getID(), true ) );
	}

	@Test
	public void functionalExerciseService()
	{
		// start exercise
		exerciseService.start( pinklabsExerciseStub.getID() );
		ExerciseImpl ex = exerciseService.findByID( pinklabsExerciseStub.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "running" ) );

		// suspend exercise
		exerciseService.suspend( new SuspensionRequest( pinklabsExerciseStub.getID(), 12.5 ) );
		ex = exerciseService.findByID( pinklabsExerciseStub.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "suspended" ) );
		assertTrue( ex.getElapsedSeconds() == 12.5 );

		// resume exercise
		assertTrue( exerciseService.resume( pinklabsExerciseStub.getID() ) == 12.5 );
		ex = exerciseService.findByID( pinklabsExerciseStub.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "running" ) );
		assertTrue( ex.getElapsedSeconds() == 0.0 );

		// stop exercise
		exerciseService.stop( pinklabsExerciseStub.getID() );
		ex = exerciseService.findByID( pinklabsExerciseStub.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "terminated" ) );

		// find data by exercise ID
		// not testing here as already tested in depth in getInputSetOutput();

		// start exercise for user
		exerciseService.startUser();
		assertTrue( exerciseService.findUserParticipant().getTimer().getStatus().toString().equalsIgnoreCase( "running" ) );

		// stop exercise for user
		exerciseService.stopUser();
		assertTrue( exerciseService.findUserParticipant().getTimer().getStatus().toString().equalsIgnoreCase( "terminated" ) );

		// suspend exercise for user
		exerciseService.suspendUser( new TimerRequest( TimeUnit.SECONDS, 22 ) );
		assertTrue( exerciseService.findUserParticipant().getTimer().getStatus().toString().equalsIgnoreCase( "suspended" ) );
		assertTrue( exerciseService.findUserParticipant().getTimer().getTimeUnit() == TimeUnit.SECONDS );
		assertTrue( exerciseService.findUserParticipant().getTimer().getValue() == 22 );

		// resume exercise for user
		TimerRequest timer = exerciseService.resumeUser();
		assertTrue( exerciseService.findUserParticipant().getTimer().getStatus().toString().equalsIgnoreCase( "running" ) );
		assertTrue( timer.getTimeUnit() == TimeUnit.SECONDS );
		assertTrue( timer.getValue() == 22 );

		// cancel
		exerciseService.cancelUser();
		assertTrue( exerciseService.findUserParticipant().getTimer().getStatus().toString().equalsIgnoreCase( "terminated" ) );

		// reset exercise for user
		exerciseService.resetUser();
		assertTrue( exerciseService.findUserParticipant().getTimer().getStatus().toString().equalsIgnoreCase( "new" ) );
		assertTrue( exerciseService.findUserParticipant().getTimer().getTimeUnit() == null );
		assertTrue( exerciseService.findUserParticipant().getTimer().getValue() == 0 );
	}

	/**
	 * helper method for checking result of getOutput and getOutputByExerciseID
	 * 
	 * @param data
	 * @param output
	 * @return
	 */
	private boolean checkOutput( List< ExerciseDataImpl > data, List< ExerciseDataImpl > output )
	{

		// only checking IDs, since getOutput and getExerciseDataByExerciseID
		// might produce different output at some point, if we ever change the methods

		List< String > outputIDs = new ArrayList< String >();
		List< String > dataIDs = new ArrayList< String >();

		for ( ExerciseDataImpl e : output )
		{
			outputIDs.add( e.getID() );
		}

		for ( ExerciseDataImpl e : data )
		{
			dataIDs.add( e.getID() );
		}

		if ( outputIDs.containsAll( dataIDs ) && dataIDs.containsAll( outputIDs ) )
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	 * helper method, extensively used in getInputSetOutput could use sessionService.setCurrentExercise, but this is faster
	 */
	private void setExerciseOnDefaultSession( ExerciseImpl exercise )
	{
		SessionImpl session = sessionService.findByID( defaultSessionStub.getID() );
		session.setCurrentExercise( exercise );
		sessionService.persist( session );
	}

	private < T extends IdentifiableObject > boolean checkSetOperation( Set< T > objects, String checkID, boolean initial )
	{
		for ( IdentifiableObject obj : objects )
		{
			if ( obj.getID().equals( checkID ) )
			{
				return !initial;
			}
		}

		return initial;
	}

}
