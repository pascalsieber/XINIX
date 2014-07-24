package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDataServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.InvitationServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.SessionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.workshop.definition.PinkElefantDefinition;

public class RestServiceTest
{
	private static SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
	private static RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
	private static UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
	private static WorkshopDefinitionService workshopDefinitionService = ServiceProxyManager.createServiceProxy( WorkshopDefinitionServiceProxy.class );
	private static WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
	private static ExerciseDefinitionService exerciseDefinitionService = ServiceProxyManager.createServiceProxy( ExerciseDefinitionServiceProxy.class );
	private static ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
	private static ExerciseDataService exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
	private static InvitationService invitationService = ServiceProxyManager.createServiceProxy( InvitationServiceProxy.class );

	private static int defaultWorkshopDefinitionID;
	private static int defaultWorkshopID;
	private static int defaultSessionID;
	private static int defaultExerciseDefinitionID;
	private static int defaultExerciseID;
	private static int defaultExerciseID2;
	private static int defaultRoleID;
	private static int defaultUserID;
	private static int defaultExerciseDataID;
	private static int defaultInvitationID;

	@BeforeClass
	public static void setupTest()
	{
		// new role
		defaultRoleID = roleService.persist( new RoleImpl( "user", "user role" ) );

		// new User
		defaultUserID = userService.persist( new UserImpl( new PasswordCredentialImpl( "password" ), (RoleImpl)roleService.findByID( defaultRoleID ), null, "John", "Smith", "john.smith@mail.com" ) );

		// new workshop definition
		defaultWorkshopDefinitionID = workshopDefinitionService.persist( new PinkElefantDefinition(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			"workshop definition",
			"workshop definition test entry",
			"problem description" ) );

		// new workshop instance
		defaultWorkshopID = workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", (WorkflowElementDefinitionImpl)workshopDefinitionService
			.findByID( defaultWorkshopDefinitionID ) ) );

		// new session
		defaultSessionID = sessionService.persist( new SessionImpl( "session", "test session", null, (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		defaultInvitationID = invitationService.persist( new Invitation(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			(PrincipalImpl)userService.findByID( defaultUserID ),
			(SessionImpl)sessionService.findByID( defaultSessionID ) ) );

		// new exercise definition
		defaultExerciseDefinitionID = exerciseDefinitionService.persist( new ExerciseDefinitionImpl(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			TimeUnit.SECONDS,
			120,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( defaultWorkshopDefinitionID ) ) );

		// new exercise
		defaultExerciseID = exerciseService.persist( new ExerciseImpl(
			"exercise",
			"exercise test instance",
			(ExerciseDefinitionImpl)exerciseDefinitionService.findByID( defaultExerciseDefinitionID ),
			(WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		defaultExerciseID2 = exerciseService.persist( new ExerciseImpl( "exercise2", "exercise test instance2", (ExerciseDefinitionImpl)exerciseDefinitionService
			.findByID( defaultExerciseDefinitionID ), (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		// new exercise data
		defaultExerciseDataID = exerciseDataService.persist( new PinkLabsExerciseData( (PrincipalImpl)userService.findByID( defaultUserID ), (WorkflowElementImpl)exerciseService
			.findByID( defaultExerciseID ), "sample answer" ) );
	}

	@Test
	public void crudOperationsUserService()
	{
		// create role
		int roleID = roleService.persist( new RoleImpl( "new role", "new role description" ) );
		assertTrue( roleID > 0 );

		// read role
		RoleImpl role = roleService.findByID( roleID );
		assertTrue( role.getID() == roleID );
		assertTrue( role.getName().equalsIgnoreCase( "new role" ) );
		assertTrue( role.getDescription().equalsIgnoreCase( "new role description" ) );

		// update role
		role.setName( "updated role" );
		roleService.persist( role );
		RoleImpl updatedRole = roleService.findByID( roleID );
		assertTrue( updatedRole.getName().equalsIgnoreCase( "updated role" ) );

		// delete role
		roleService.remove( roleService.findByID( roleID ) );

		// find all checks delete
		List< RoleImpl > roles = roleService.findAll( RoleImpl.class );
		assertTrue( roles.size() > 0 );
		assertTrue( checkDelete( roles, roleID ) );

		// create user
		int userID = userService.persist( new UserImpl( new PasswordCredentialImpl( "my password" ), (RoleImpl)userService.findByID( defaultRoleID ), (SessionImpl)userService
			.findByID( defaultSessionID ), "firstname", "lastname", "login@name" ) );

		// read user
		UserImpl user = userService.findByID( userID );
		assertTrue( user.getID() == userID );
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
		userService.remove( userService.findByID( userID ) );

		// find all checks delete
		List< UserImpl > users = userService.findAll( UserImpl.class );
		assertTrue( users.size() > 0 );
		assertTrue( checkDelete( users, userID ) );

	}

	@Test
	public void crudOperationsWorkshopService()
	{
		// create workshop definition
		int workshopDefinitionID = workshopDefinitionService.persist( new WorkshopDefinitionImpl(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			"workshop definition",
			"workshop definition description" ) );

		// read workshop definition
		WorkshopDefinitionImpl workshopDefinition = workshopDefinitionService.findByID( workshopDefinitionID );
		assertTrue( workshopDefinition.getID() == workshopDefinitionID );
		assertTrue( workshopDefinition.getName().equalsIgnoreCase( "workshop definition" ) );
		assertTrue( workshopDefinition.getDescription().equalsIgnoreCase( "workshop definition description" ) );
		assertTrue( workshopDefinition.getOwner().getID() == defaultUserID );

		// update workshop definition
		workshopDefinition.setName( "updated workshop definition" );
		workshopDefinitionService.persist( workshopDefinition );
		WorkshopDefinitionImpl updatedWorkshopDefinition = workshopDefinitionService.findByID( workshopDefinitionID );
		assertTrue( updatedWorkshopDefinition.getName().equalsIgnoreCase( "updated workshop definition" ) );

		// delete workshop definition
		workshopDefinitionService.remove( workshopDefinitionService.findByID( workshopDefinitionID ) );

		// find all checks delete
		List< WorkshopDefinitionImpl > wsDefs = workshopDefinitionService.findAll( WorkshopDefinitionImpl.class );
		assertTrue( wsDefs.size() > 0 );
		assertTrue( checkDelete( wsDefs, workshopDefinitionID ) );

		// create workshop instance
		int wsID = workshopService.persist( new WorkshopImpl( "workshop instance", "workshop instance description", (WorkflowElementDefinitionImpl)workshopDefinitionService
			.findByID( defaultWorkshopDefinitionID ) ) );

		// read workshop instance
		WorkshopImpl ws = workshopService.findByID( wsID );
		assertTrue( ws.getID() == wsID );
		assertTrue( ws.getName().equalsIgnoreCase( "workshop instance" ) );
		assertTrue( ws.getDescription().equalsIgnoreCase( "workshop instance description" ) );
		assertTrue( ws.getCurrentState().equalsIgnoreCase( "new" ) );
		assertTrue( ws.getDefinition().getID() == defaultWorkshopDefinitionID );

		// update workshop instance
		ws.setName( "updated workshop instance" );
		workshopService.persist( ws );
		WorkshopImpl updatedWs = workshopService.findByID( wsID );
		assertTrue( updatedWs.getName().equalsIgnoreCase( "updated workshop instance" ) );

		// delete workshop instance
		workshopService.remove( updatedWs );

		// find all checks delete
		List< WorkshopImpl > workshops = workshopService.findAll( WorkshopImpl.class );
		assertTrue( workshops.size() > 0 );
		assertTrue( checkDelete( workshops, wsID ) );

		// create session
		int sessionID = sessionService.persist( new SessionImpl( "session instance", "session description", null, (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		// read session
		SessionImpl session = sessionService.findByID( sessionID );
		assertTrue( session.getID() == sessionID );
		assertTrue( session.getName().equalsIgnoreCase( "session instance" ) );
		assertTrue( session.getDescription().equalsIgnoreCase( "session description" ) );
		assertTrue( session.getDefinition() == null );
		assertTrue( session.getWorkshop().getID() == defaultWorkshopID );

		// update session
		session.setName( "updated session" );
		sessionService.persist( session );
		SessionImpl updatedSession = sessionService.findByID( sessionID );
		assertTrue( updatedSession.getName().equalsIgnoreCase( "updated session" ) );

		// delete session
		sessionService.remove( updatedSession );

		// find all checks delete
		List< SessionImpl > sessions = sessionService.findAll( SessionImpl.class );
		assertTrue( sessions.size() > 0 );
		assertTrue( checkDelete( sessions, sessionID ) );
	}

	@Test
	public void crudOperationsExerciseService()
	{
		// create exercise definition
		int exDefID = exerciseDefinitionService.persist( new PinkLabsDefinition(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			TimeUnit.MINUTES,
			2,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( defaultWorkshopDefinitionID ),
			"question" ) );

		// read exercise definition
		ExerciseDefinitionImpl exDef = exerciseDefinitionService.findByID( exDefID );
		assertTrue( exDef.getID() == exDefID );
		assertTrue( exDef.getDuration() == 2 );
		assertTrue( exDef.getOwner().getID() == defaultUserID );
		assertTrue( exDef.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( exDef.getWorkshopDefinition().getID() == defaultWorkshopDefinitionID );
		assertTrue( ( (PinkLabsDefinition)exDef ).getQuestion().equalsIgnoreCase( "question" ) );

		// update exercise definition
		exDef.setDuration( 12 );
		exerciseDefinitionService.persist( exDef );
		ExerciseDefinitionImpl updatedExDef = exerciseDefinitionService.findByID( exDefID );
		assertTrue( updatedExDef.getDuration() == 12 );

		// delete exercise definition
		exerciseDefinitionService.remove( updatedExDef );

		// find all checks delete
		List< ExerciseDefinitionImpl > exDefs = exerciseDefinitionService.findAll( ExerciseDefinitionImpl.class );
		assertTrue( exDefs.size() > 0 );
		assertTrue( checkDelete( exDefs, exDefID ) );

		// create exercise instance
		int exID = exerciseService.persist( new ExerciseImpl(
			"exercise",
			"exercise description",
			(ExerciseDefinitionImpl)exerciseDefinitionService.findByID( defaultExerciseDefinitionID ),
			(WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		// read exercise instance
		ExerciseImpl ex = exerciseService.findByID( exID );
		assertTrue( ex.getID() == exID );
		assertTrue( ex.getCurrentState().equalsIgnoreCase( "new" ) );
		assertTrue( ex.getDefinition().getID() == defaultExerciseDefinitionID );
		assertTrue( ex.getName().equalsIgnoreCase( "exercise" ) );
		assertTrue( ex.getDescription().equalsIgnoreCase( "exercise description" ) );
		assertTrue( ex.getWorkshop().getID() == defaultWorkshopID );

		// update exercise instance
		ex.setName( "updated exercise" );
		exerciseService.persist( ex );
		ExerciseImpl updatedEx = exerciseService.findByID( exID );
		assertTrue( updatedEx.getName().equalsIgnoreCase( "updated exercise" ) );

		// delete exercise instance
		exerciseService.remove( updatedEx );

		// find all checks delete
		List< ExerciseImpl > exs = exerciseService.findAll( ExerciseImpl.class );
		assertTrue( exs.size() > 0 );
		assertTrue( checkDelete( exs, exID ) );

		// create exercise data
		int dataID = exerciseDataService
			.persist( new PinkLabsExerciseData( (PrincipalImpl)userService.findByID( defaultUserID ), (ExerciseImpl)exerciseService.findByID( defaultExerciseID ), "answer" ) );

		// read exercise data
		ExerciseDataImpl data = exerciseDataService.findByID( dataID );
		assertTrue( data.getID() == dataID );
		assertTrue( ( (PinkLabsExerciseData)data ).getAnswer().equalsIgnoreCase( "answer" ) );
		assertTrue( data.getOwner().getID() == defaultUserID );
		assertTrue( data.getWorkflowElement().getID() == defaultExerciseID );

		// update exercise data
		( (PinkLabsExerciseData)data ).setAnswer( "updated answer" );
		exerciseDataService.persist( data );
		PinkLabsExerciseData updatedData = exerciseDataService.findByID( dataID );
		assertTrue( updatedData.getAnswer().equalsIgnoreCase( "updated answer" ) );

		// delete exercise data
		exerciseDataService.remove( updatedData );

		// find all checks delete
		List< ExerciseDataImpl > datas = exerciseDataService.findAll( ExerciseDataImpl.class );
		assertTrue( datas.size() > 0 );
		assertTrue( checkDelete( datas, dataID ) );
	}

	@Test
	public void crudOperationsInvitationService()
	{

		// create invitation
		int invitationID = invitationService.persist( new Invitation(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			(PrincipalImpl)userService.findByID( defaultUserID ),
			(SessionImpl)sessionService.findByID( defaultSessionID ) ) );

		// read invitation
		Invitation invitation = invitationService.findByID( invitationID );
		assertTrue( invitation.getID() == invitationID );
		assertTrue( invitation.getDate() != null );
		assertTrue( invitation.getInvitee().getID() == defaultUserID );
		assertTrue( invitation.getInviter().getID() == defaultUserID );
		assertTrue( invitation.getSession().getID() == defaultSessionID );

		// update invitation
		Date date = new Date();
		invitation.setDate( date );
		invitationService.persist( invitation );
		Invitation updatedInvitation = invitationService.findByID( invitationID );
		assertTrue( updatedInvitation.getDate().getTime() == date.getTime() );

		// delete invitation
		invitationService.remove( invitation );

		// find all check delete
		List< Invitation > invitations = invitationService.findAll( Invitation.class );
		assertTrue( invitations.size() > 0 );
		assertTrue( checkDelete( invitations, invitationID ) );
	}

	@Test
	public void functionalUserService()
	{
		// find by login name
		PrincipalImpl usr = userService.findByID( defaultUserID );
		PrincipalImpl searched = userService.findByLoginName( ( (UserImpl)usr ).getLoginName() );
		assertTrue( searched.getID() == usr.getID() );

		// request new password
		String password = userService.requestNewPassword( defaultUserID );
		assertTrue( ( (PrincipalImpl)userService.findByID( defaultUserID ) ).getCredential().getPassword().equals( password ) );
	}

	@Test
	public void functionalWorkshopService()
	{
		// start workshop
		workshopService.start( defaultWorkshopID );
		assertTrue( ( (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ).getCurrentState().equalsIgnoreCase( "running" ) );

		// stop workshop
		workshopService.stop( defaultWorkshopID );
		assertTrue( ( (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ).getCurrentState().equalsIgnoreCase( "terminated" ) );

		// start session
		sessionService.start( defaultSessionID );
		assertTrue( ( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getCurrentState().equalsIgnoreCase( "running" ) );

		// stop session
		sessionService.stop( defaultSessionID );
		assertTrue( ( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getCurrentState().equalsIgnoreCase( "terminated" ) );

		// getCurrentExercise
		assertTrue( sessionService.getCurrentExercise( defaultSessionID ).getID() == defaultExerciseID );

		// getNextExercise
		assertTrue( sessionService.getNextExercise( defaultSessionID ).getID() == defaultExerciseID2 );

		// setNextExercise
		sessionService.setNextExercise( defaultSessionID );
		assertTrue( sessionService.getCurrentExercise( defaultSessionID ).getID() == defaultExerciseID2 );

		// getPreviousExercise
		assertTrue( sessionService.getPreviousExercise( defaultSessionID ).getID() == defaultExerciseID );

		// join Session
		sessionService.join( defaultSessionID, defaultUserID );
		assertTrue( ( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getParticipants().contains( userService.findByID( defaultUserID ) ) );

		// leave Session
		sessionService.leave( defaultSessionID, defaultUserID );
		assertTrue( !( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getParticipants().contains( userService.findByID( defaultUserID ) ) );

		// accept invitation
		invitationService.accept( defaultInvitationID );
		assertTrue( checkSetOperation( ( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getInvitations(), defaultInvitationID, true ) );

		// add executer
		sessionService.addExecuter( new Invitation( null, (PrincipalImpl)userService.findByID( defaultUserID ), (SessionImpl)sessionService.findByID( defaultSessionID ) ) );
		assertTrue( checkSetOperation( ( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getExecuters(), defaultUserID, false ) );

		// remove executer
		sessionService.removeExecuter( new Invitation( null, (PrincipalImpl)userService.findByID( defaultUserID ), (SessionImpl)sessionService.findByID( defaultSessionID ) ) );
		assertTrue( checkSetOperation( ( (SessionImpl)sessionService.findByID( defaultSessionID ) ).getExecuters(), defaultUserID, true ) );
	}

	@Test
	public void functionalExerciseService()
	{
		// start exercise
		exerciseService.start( defaultExerciseID );
		assertTrue( ( (ExerciseImpl)exerciseService.findByID( defaultExerciseID ) ).getCurrentState().equalsIgnoreCase( "running" ) );

		// stop exercise
		exerciseService.stop( defaultExerciseID );
		assertTrue( ( (ExerciseImpl)exerciseService.findByID( defaultExerciseID ) ).getCurrentState().equalsIgnoreCase( "terminated" ) );

		// find data by exercise ID
		assertTrue( exerciseDataService.findByExerciseID( defaultExerciseID ).size() > 0 );
	}

	private < T extends IdentifiableObject > boolean checkSetOperation( Set< T > objects, int checkID, boolean initial )
	{
		for ( IdentifiableObject obj : objects )
		{
			if ( obj.getID() == checkID )
			{
				return !initial;
			}
		}

		return initial;
	}

	private < T extends IdentifiableObject > boolean checkDelete( List< T > objects, int deletedID )
	{
		boolean result = false;
		ArrayList< Integer > ids = new ArrayList<>();

		for ( IdentifiableObject object : objects )
		{
			ids.add( object.getID() );
		}

		if ( !ids.contains( deletedID ) )
		{
			result = true;
		}

		return result;
	}
}
