package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
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
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixImageMatrix;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.You2MeDefinition;
import ch.zhaw.iwi.cis.pinkelefant.workshop.definition.PinkElefantDefinition;

public class RestServiceTest
{
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

	// using stub objects (only ID) to avoid json mapping problems and to
	// mimic the way API will be used
	private static Client defaultClientStub = new Client();
	private static RoleImpl defaultRoleStub = new RoleImpl();
	private static UserImpl defaultUserStub = new UserImpl();
	private static PinkElefantDefinition defaultWorkshopDefinitionStub = new PinkElefantDefinition();
	private static WorkshopImpl defaultWorkshopStub = new WorkshopImpl();
	private static SessionImpl defaultSessionStub = new SessionImpl();
	private static ExerciseImpl defaultExerciseStub1 = new ExerciseImpl();
	private static ExerciseImpl defaultExerciseStub2 = new ExerciseImpl();
	private static Invitation defaultInvitationStub = new Invitation();

	private static PinkLabsDefinition pinklabsDefinitionStub = new PinkLabsDefinition();
	private static P2POneDefinition p2poneDefinitionStub = new P2POneDefinition();
	private static P2PTwoDefinition p2ptwoDefinitionStub = new P2PTwoDefinition();
	private static XinixDefinition xinixDefinitionStub = new XinixDefinition();
	private static You2MeDefinition you2meDefinitionStub = new You2MeDefinition();

	private static XinixImage xinixImageStub = new XinixImage();
	private static XinixImageMatrix xinixImageMatrixStub = new XinixImageMatrix();
	private static PinkLabsExerciseData pinklabsDataStub = new PinkLabsExerciseData();
	private static P2POneKeyword p2poneKeywordStub1 = new P2POneKeyword();
	private static P2POneKeyword p2poneKeywordStub2 = new P2POneKeyword();
	private static P2POneData p2poneDataStub = new P2POneData();
	private static P2PTwoData p2ptwoDataStub = new P2PTwoData();
	private static XinixData xinixDataStub = new XinixData();
	private static You2MeExerciseData you2meDataStub = new You2MeExerciseData();

	@SuppressWarnings( "unchecked" )
	@BeforeClass
	public static void setupTest()
	{
		Client rootClient = globalService.getRootClient();
		defaultClientStub.setID( rootClient.getID() );

		// role
		defaultRoleStub.setID( roleService.persist( new RoleImpl( "user", "user role" ) ) );

		// User
		defaultUserStub.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( "john" ), defaultRoleStub, null, "John", "Smith", rootClient.getName() + "/fueg@zhaw.ch" ) ) );

		// workshop definition (pinkelefantDefinition)
		defaultWorkshopDefinitionStub.setID( workshopDefinitionService.persist( new PinkElefantDefinition(
			defaultUserStub,
			"workshop definition",
			"workshop definition test entry",
			"problem description" ) ) );

		// workshop instance
		defaultWorkshopStub.setID( workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", defaultWorkshopDefinitionStub ) ) );

		// pinklabs definition
		pinklabsDefinitionStub.setID( exerciseDefinitionService.persist( new PinkLabsDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "pinklabs?" ) ) );

		// p2pone definition
		p2poneDefinitionStub.setID( exerciseDefinitionService.persist( new P2POneDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "urltopicture", "theme" ) ) );

		// p2ptwo definition
		p2ptwoDefinitionStub.setID( exerciseDefinitionService.persist( new P2PTwoDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "question?" ) ) );

		// xinix definition
		// TODO fix this!
		// xinixDefinitionStub.setID( exerciseDefinitionService.persist( new XinixDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, "question", images ) ) );

		// you2me definition
		you2meDefinitionStub.setID( exerciseDefinitionService.persist( new You2MeDefinition( defaultUserStub, TimeUnit.SECONDS, 120, defaultWorkshopDefinitionStub, Arrays.asList(
			"question?",
			"counter question?" ) ) ) );

		// exercise one
		defaultExerciseStub1.setID( exerciseService.persist( new ExerciseImpl( "exercise1", "instance of exercise 1", pinklabsDefinitionStub, defaultWorkshopStub ) ) );

		// exercise two
		defaultExerciseStub2.setID( exerciseService.persist( new ExerciseImpl( "exercise2", "instance of exercise 2", xinixDefinitionStub, defaultWorkshopStub ) ) );

		// xinix image
		xinixImageStub.setID( exerciseDataService.persist( new XinixImage( defaultUserStub, null, "http://www.whatnextpawan.com/wp-content/uploads/2014/03/oh-yes-its-free.png" ) ) );

		// xinix image matrix
		// TODO finsh

		// pinklabs data
		pinklabsDataStub.setID( exerciseDataService.persist( new PinkLabsExerciseData( defaultUserStub, defaultExerciseStub1, Arrays.asList( "answer one", "answer two" ) ) ) );

		// p2pone data
		p2poneDataStub.setID( exerciseDataService.persist( new P2POneData( defaultUserStub, defaultExerciseStub1, Arrays.asList( "keyword one", "keyword two" ) ) ) );

		// p2ptwo data
		p2ptwoDataStub.setID( exerciseDataService.persist( new P2PTwoData( defaultUserStub, defaultExerciseStub1, new HashSet<>( Arrays.asList( "answer one", "answer two" ) ), new HashSet<>(
			( (P2POneData)exerciseDataService.findByID( p2poneDataStub.getID() ) ).getKeywords() ) ) ) );

		// xinix data
		// TODO fix this!
		// xinixDataStub.setID( exerciseDataService.persist( new XinixData( defaultUserStub, defaultExerciseStub1, "my association", xinixImageStub ) ) );

		// you2me data
		// TODO fix this!
		// you2meDataStub.setID( exerciseDataService.persist( new You2MeExerciseData( defaultUserStub, defaultExerciseStub1, "question one", "question two", "response one", "response two" ) ) );

		// session
		defaultSessionStub.setID( sessionService.persist( new SessionImpl( "session", "test session", null, defaultWorkshopStub ) ) );

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

		// create exercise data
		String dataID = exerciseDataService.persist( new PinkLabsExerciseData( defaultUserStub, defaultExerciseStub1, Arrays.asList( "answer1", "answer2" ) ) );

		// read exercise data
		ExerciseDataImpl data = exerciseDataService.findByID( dataID );
		assertTrue( data.getClient().getID().equals( defaultClientStub.getID() ) );
		assertTrue( data.getID().equals( dataID ) );
		assertTrue( ( (PinkLabsExerciseData)data ).getAnswers().contains( "answer1" ) );
		assertTrue( ( (PinkLabsExerciseData)data ).getAnswers().contains( "answer2" ) );
		assertTrue( data.getOwner().getID().equals( defaultUserStub.getID() ) );
		assertTrue( data.getWorkflowElement().getID().equals( defaultExerciseStub1.getID() ) );

		// update exercise data
		( (PinkLabsExerciseData)data ).setAnswers( Arrays.asList( "updatedanswer1", "answer2" ) );
		exerciseDataService.persist( data );
		PinkLabsExerciseData updatedData = exerciseDataService.findByID( dataID );
		assertTrue( updatedData.getAnswers().contains( "updatedanswer1" ) );

		// delete exercise data
		int dataBefore = exerciseDataService.findAll().size();
		exerciseDataService.remove( updatedData );

		// find all checks delete
		List< ExerciseDataImpl > datas = exerciseDataService.findAll();
		assertTrue( datas.size() > 0 );
		assertTrue( dataBefore - datas.size() == 1 );
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
		assertTrue( userService.requestNewPassword( defaultUserStub.getID() ) );
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
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( defaultExerciseStub1.getID() ) );

		// getNextExercise
		assertTrue( sessionService.getNextExercise( defaultSessionStub.getID() ).getID().equals( defaultExerciseStub2.getID() ) );

		// setNextExercise
		sessionService.setNextExercise( defaultSessionStub.getID() );
		assertTrue( sessionService.getCurrentExercise( defaultSessionStub.getID() ).getID().equals( defaultExerciseStub2.getID() ) );

		// getPreviousExercise
		assertTrue( sessionService.getPreviousExercise( defaultSessionStub.getID() ).getID().equals( defaultExerciseStub1.getID() ) );

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
		exerciseService.start( defaultExerciseStub1.getID() );
		ExerciseImpl ex = exerciseService.findByID( defaultExerciseStub1.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "running" ) );

		// suspend exercise
		exerciseService.suspend( new SuspensionRequest( defaultExerciseStub1.getID(), 12.5 ) );
		ex = exerciseService.findByID( defaultExerciseStub1.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "suspended" ) );
		assertTrue( ex.getElapsedSeconds() == 12.5 );

		// resume exercise
		assertTrue( exerciseService.resume( defaultExerciseStub1.getID() ) == 12.5 );
		ex = exerciseService.findByID( defaultExerciseStub1.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "running" ) );
		assertTrue( ex.getElapsedSeconds() == 0.0 );

		// stop exercise
		exerciseService.stop( defaultExerciseStub1.getID() );
		ex = exerciseService.findByID( defaultExerciseStub1.getID() );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "terminated" ) );

		// find data by exercise ID
		assertTrue( exerciseDataService.findByExerciseID( defaultExerciseStub1.getID() ).size() > 0 );
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
