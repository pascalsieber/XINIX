package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
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
import ch.zhaw.iwi.cis.pews.model.input.EvaluationInput;
import ch.zhaw.iwi.cis.pews.model.input.P2POneInput;
import ch.zhaw.iwi.cis.pews.model.input.P2PTwoInput;
import ch.zhaw.iwi.cis.pews.model.input.PinkLabsInput;
import ch.zhaw.iwi.cis.pews.model.input.SimplePrototypingInput;
import ch.zhaw.iwi.cis.pews.model.input.XinixInput;
import ch.zhaw.iwi.cis.pews.model.input.You2MeInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutput;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
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
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
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
	private static ExerciseDataService exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
	private static InvitationService invitationService = ServiceProxyManager.createServiceProxy( InvitationServiceProxy.class );

	// using stub objects to mimic the way API will be used
	private static Client defaultClientStub = new Client();
	private static RoleImpl defaultRoleStub = new RoleImpl();
	private static UserImpl defaultUserStub = new UserImpl();
	private static PinkElefantDefinition defaultWorkshopDefinitionStub = new PinkElefantDefinition();
	private static WorkshopImpl defaultWorkshopStub = new WorkshopImpl();
	private static SessionImpl defaultSessionStub = new SessionImpl();
	private static Invitation defaultInvitationStub = new Invitation();

	private static PinkLabsDefinition pinklabsDefinitionStub = new PinkLabsDefinition();
	private static P2POneDefinition p2poneDefinitionStub = new P2POneDefinition();
	private static P2PTwoDefinition p2ptwoDefinitionStub = new P2PTwoDefinition();
	private static XinixImageMatrix xinixImageMatrixStub = new XinixImageMatrix();
	private static XinixDefinition xinixDefinitionStub = new XinixDefinition();
	private static You2MeDefinition you2meDefinitionStub = new You2MeDefinition();
	private static SimplePrototypingDefinition simpleprototypingDefinitionStub = new SimplePrototypingDefinition();
	private static CompressionDefinition compressionDefinitionStub = new CompressionDefinition();
	private static EvaluationDefinition evaluationDefinitionStub = new EvaluationDefinition();

	private static ExerciseImpl pinklabsExerciseStub = new ExerciseImpl();
	private static ExerciseImpl you2meExerciseStub = new ExerciseImpl();
	private static ExerciseImpl p2pOneExerciseStub = new ExerciseImpl();
	private static ExerciseImpl p2pTwoExerciseStub = new ExerciseImpl();
	private static ExerciseImpl simpleprototypingExerciseStub = new ExerciseImpl();
	private static ExerciseImpl xinixExerciseStub = new ExerciseImpl();
	private static ExerciseImpl compressionExerciseStub = new ExerciseImpl();
	private static ExerciseImpl evaluationExerciseStub = new ExerciseImpl();

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

		// workshop definition (pinkelefantDefinition)
		defaultWorkshopDefinitionStub.setID( workshopDefinitionService.persist( new PinkElefantDefinition(
			defaultUserStub,
			"workshop definition",
			"workshop definition test entry",
			"problem description" ) ) );

		// workshop instance
		defaultWorkshopStub.setID( workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", defaultWorkshopDefinitionStub ) ) );

		// exercise definitions
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

		evaluationDefinitionStub.setID( exerciseDefinitionService.persist( new EvaluationDefinition( defaultUserStub, TimeUnit.MINUTES, 10, defaultWorkshopDefinitionStub, "evaluation question" ) ) );

		xinixImageStub.setID( exerciseDataService.persist( new XinixImage( defaultUserStub, null, "http://www.whatnextpawan.com/wp-content/uploads/2014/03/oh-yes-its-free.png" ) ) );
		Set< XinixImage > images = new HashSet<>();
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
		pinklabsExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "pinklabs", "pinklabs exercise", pinklabsDefinitionStub, defaultWorkshopStub ) ) );
		p2pOneExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "p2pone", "p2pone exercise", p2poneDefinitionStub, defaultWorkshopStub ) ) );
		you2meExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "you2me", "you2me exercise", you2meDefinitionStub, defaultWorkshopStub ) ) );
		p2pTwoExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "p2ptwo", "p2ptwo exercise", p2ptwoDefinitionStub, defaultWorkshopStub ) ) );
		simpleprototypingExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "simple proto", "simple proto exercise", simpleprototypingDefinitionStub, defaultWorkshopStub ) ) );
		xinixExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "xinix", "xinix exercise", xinixDefinitionStub, defaultWorkshopStub ) ) );
		compressionExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "compression", "compression exercise", compressionDefinitionStub, defaultWorkshopStub ) ) );
		evaluationExerciseStub.setID( exerciseService.persist( new ExerciseImpl( "evaluation", "evaluation exercise", evaluationDefinitionStub, defaultWorkshopStub ) ) );

		// session
		defaultSessionStub.setID( sessionService.persist( new SessionImpl( "session", "test session", null, defaultWorkshopStub ) ) );

		// set default user's session to newly configured session for testing
		sessionService.join( new Invitation( null, defaultUserStub, defaultSessionStub ) );

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

		// // create exercise data
		// String dataID = exerciseDataService.persist( new PinkLabsExerciseData( defaultUserStub, defaultExerciseStub1, Arrays.asList( "answer1", "answer2" ) ) );
		//
		// // read exercise data
		// ExerciseDataImpl data = exerciseDataService.findByID( dataID );
		// assertTrue( data.getClient().getID().equals( defaultClientStub.getID() ) );
		// assertTrue( data.getID().equals( dataID ) );
		// assertTrue( ( (PinkLabsExerciseData)data ).getAnswers().contains( "answer1" ) );
		// assertTrue( ( (PinkLabsExerciseData)data ).getAnswers().contains( "answer2" ) );
		// assertTrue( data.getOwner().getID().equals( defaultUserStub.getID() ) );
		// assertTrue( data.getWorkflowElement().getID().equals( defaultExerciseStub1.getID() ) );
		//
		// // update exercise data
		// ( (PinkLabsExerciseData)data ).setAnswers( Arrays.asList( "updatedanswer1", "answer2" ) );
		// exerciseDataService.persist( data );
		// PinkLabsExerciseData updatedData = exerciseDataService.findByID( dataID );
		// assertTrue( updatedData.getAnswers().contains( "updatedanswer1" ) );
		//
		// // delete exercise data
		// int dataBefore = exerciseDataService.findAll().size();
		// exerciseDataService.remove( updatedData );
		//
		// // find all checks delete
		// List< ExerciseDataImpl > datas = exerciseDataService.findAll();
		// assertTrue( datas.size() > 0 );
		// assertTrue( dataBefore - datas.size() == 1 );
	}

	@Test
	public void crudOperationsExerciseData()
	{

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

	@Test
	public void getInputSetOutput() throws IOException
	{
		Output output = null;
		List< String > p2pOneKeywordIDs = new ArrayList<>();
		boolean success = false;

		// pinklabs
		setExerciseOnDefaultSession( pinklabsExerciseStub );
		PinkLabsInput pinklabsInput = mapper.readValue( exerciseService.getInputAsString(), PinkLabsInput.class );
		assertTrue( pinklabsInput.getQuestion().equalsIgnoreCase( ( (PinkLabsDefinition)exerciseDefinitionService.findByID( pinklabsDefinitionStub.getID() ) ).getQuestion() ) );

		output = new PinkLabsOutput( Arrays.asList( "answer1", "answer2", "answer3" ) );
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
				p2pOneKeywordIDs.add( keyword.getID() );

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

		// p2ptwo
		setExerciseOnDefaultSession( p2pTwoExerciseStub );
		String p2ptwoInputString = exerciseService.getInputAsString();
		P2PTwoInput p2ptwoInput = mapper.readValue( p2ptwoInputString, P2PTwoInput.class );

		assertTrue( p2ptwoInput.getQuestion().equalsIgnoreCase( ( (P2PTwoDefinition)exerciseDefinitionService.findByID( p2ptwoDefinitionStub.getID() ) ).getQuestion() ) );

		List< ExerciseDataImpl > p2pOneDataForP2PTwoTest = exerciseDataService.findByExerciseID( p2pOneExerciseStub.getID() );
		List< P2POneData > p2pOneDataForP2PTwoTestPrepped = mapper.readValue( mapper.writeValueAsString( p2pOneDataForP2PTwoTest ), makeCollectionType( P2POneData.class ) );

		for ( P2POneData d : p2pOneDataForP2PTwoTestPrepped )
		{
			for ( P2POneKeyword keyword : d.getKeywords() )
			{
				assertTrue( p2ptwoInput.getCascade1Keywords().contains( keyword.getKeyword() ) );
			}
		}

		Set< String > p2ptwoAnswers = new HashSet<>();
		p2ptwoAnswers.addAll( Arrays.asList( "p21", "p22", "p23" ) );

		Set< String > keywordIDs = new HashSet<>();
		keywordIDs.add( p2pOneKeywordIDs.get( 0 ) );
		keywordIDs.add( p2pOneKeywordIDs.get( 1 ) );

		output = new P2PTwoOutput( keywordIDs, p2ptwoAnswers );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > p2ptwoData = exerciseDataService.findByExerciseID( p2pTwoExerciseStub.getID() );
		List< P2PTwoData > p2ptwoDataPrepped = mapper.readValue( mapper.writeValueAsString( p2ptwoData ), makeCollectionType( P2PTwoData.class ) );

		for ( P2PTwoData d : p2ptwoDataPrepped )
		{
			if ( d.getAnswers().containsAll( p2ptwoAnswers ) )
			{
				for ( P2POneKeyword keyword : d.getSelectedKeywords() )
				{
					assertTrue( keywordIDs.contains( keyword.getID() ) );
				}

				success = true;
			}
		}

		assertTrue( success );

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

		// compression
		setExerciseOnDefaultSession( compressionExerciseStub );
		CompressionInput compressionInput = mapper.readValue( exerciseService.getInputAsString(), CompressionInput.class );
		assertTrue( compressionInput.getQuestion().equalsIgnoreCase( ( (CompressionDefinition)exerciseDefinitionService.findByID( compressionDefinitionStub.getID() ) ).getQuestion() ) );
		assertTrue( compressionInput.getCompressableExerciseData().size() > 0 );

		for ( String criterion : compressionInput.getSolutionCriteria() )
		{
			( (CompressionDefinition)exerciseDefinitionService.findByID( compressionDefinitionStub.getID() ) ).getSolutionCriteria().contains( criterion );
		}

		output = new CompressionOutput( Arrays.asList( "c1", "c2", "c3" ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > compressionData = exerciseDataService.findByExerciseID( compressionExerciseStub.getID() );
		List< CompressionExerciseData > compressionDataPrepped = mapper.readValue( mapper.writeValueAsString( compressionData ), makeCollectionType( CompressionExerciseData.class ) );

		for ( CompressionExerciseData d : compressionDataPrepped )
		{
			int count = 0;

			for ( String string : d.getSolutions() )
			{
				if ( string.equalsIgnoreCase( "c1" ) || string.equalsIgnoreCase( "c2" ) || string.equalsIgnoreCase( "c3" ) )
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

		// evaluation
		setExerciseOnDefaultSession( evaluationExerciseStub );
		EvaluationInput evaluationInput = mapper.readValue( exerciseService.getInputAsString(), EvaluationInput.class );
		assertTrue( evaluationInput.getQuestion().equalsIgnoreCase( ( (EvaluationDefinition)exerciseDefinitionService.findByID( evaluationDefinitionStub.getID() ) ).getQuestion() ) );

		List< CompressionExerciseData > dataFromCompression = mapper.readValue(
			mapper.writeValueAsString( exerciseDataService.findByExerciseID( compressionExerciseStub.getID() ) ),
			makeCollectionType( CompressionExerciseData.class ) );

		for ( CompressionExerciseData d : dataFromCompression )
		{
			for ( String solution : d.getSolutions() )
			{
				assertTrue( evaluationInput.getSolutions().contains( solution ) );
			}
		}

		output = new EvaluationOutput( Arrays.asList( new Evaluation( defaultUserStub, "solution1", 5 ), new Evaluation( defaultUserStub, "solution2", 2 ) ) );
		exerciseService.setOutput( mapper.writeValueAsString( output ) );

		success = false;

		List< ExerciseDataImpl > evaluationData = exerciseDataService.findByExerciseID( evaluationExerciseStub.getID() );
		List< EvaluationExerciseData > evaluationDataPrepped = mapper.readValue( mapper.writeValueAsString( evaluationData ), makeCollectionType( EvaluationExerciseData.class ) );

		for ( EvaluationExerciseData d : evaluationDataPrepped )
		{
			int count = 0;

			for ( Evaluation evaluation : d.getEvaluations() )
			{
				if ( evaluation.getSolution().equalsIgnoreCase( "solution1" ) && evaluation.getScore() == 5 )
				{
					count += 1;
				}

				if ( evaluation.getSolution().equalsIgnoreCase( "solution2" ) && evaluation.getScore() == 2 )
				{
					count += 1;
				}
			}

			if ( count == 2 )
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
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( pinklabsExerciseStub.getID() ) );

		// getNextExercise
		assertTrue( sessionService.getNextExercise( defaultSessionStub.getID() ).getID().equals( p2pOneExerciseStub.getID() ) );

		// setNextExercise
		sessionService.setNextExercise( defaultSessionStub.getID() );
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( p2pOneExerciseStub.getID() ) );

		// getPreviousExercise
		assertTrue( sessionService.getPreviousExercise( defaultSessionStub.getID() ).getID().equals( pinklabsExerciseStub.getID() ) );

		// TODO finish this!
		// setCurrentExericse

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
		assertTrue( exerciseDataService.findByExerciseID( pinklabsExerciseStub.getID() ).size() > 0 );

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
