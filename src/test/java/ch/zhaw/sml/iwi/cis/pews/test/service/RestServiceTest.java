package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDataServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.SessionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopDefinitionServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopServiceProxy;
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

	private static int defaultWorkshopDefinitionID;
	private static int defaultWorkshopID;
	private static int defaultSessionID;
	private static int defaultExerciseDefinitionID;
	private static int defaultExerciseID;
	private static int defaultRoleID;
	private static int defaultUserID;

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
		defaultWorkshopID = workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", (WorkflowElementDefinitionImpl)workshopService.findByID( defaultWorkshopDefinitionID ) ) );

		// new session
		defaultSessionID = sessionService.persist( new SessionImpl( "session", "test session", null, (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

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
			(ExerciseDefinitionImpl)exerciseService.findByID( defaultExerciseDefinitionID ),
			(WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );
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
		List< WorkshopDefinitionImpl > wsDefs = roleService.findAll( WorkshopDefinitionImpl.class );
		assertTrue( wsDefs.size() > 0 );
		assertTrue(checkDelete( wsDefs, workshopDefinitionID ));

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
		ws.setName( "update workshop instance" );
		workshopService.persist( ws );
		WorkshopImpl updatedWs = workshopService.findByID( wsID );
		assertTrue( updatedWs.getName().equalsIgnoreCase( "updated workshop instance" ) );

		// delete workshop instance
		workshopService.remove( updatedWs );

		// find all checks delete
		List< WorkshopImpl > workshops = roleService.findAll( WorkshopImpl.class );
		assertTrue( workshops.size() > 0 );
		assertTrue(checkDelete( workshops, wsID ));

		// create session
		int sessionID = sessionService.persist( new SessionImpl( "session instance", "session description", null, (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		// read session
		SessionImpl session = sessionService.findByID( sessionID );
		assertTrue( session.getID() == sessionID );
		assertTrue( session.getName().equalsIgnoreCase( "session instance" ) );
		assertTrue( session.getName().equalsIgnoreCase( "session description" ) );
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
		List< SessionImpl > sessions = roleService.findAll( SessionImpl.class );
		assertTrue( sessions.size() > 0 );
		assertTrue(checkDelete( sessions, sessionID ));
	}

	@Test
	public void crudOperationsExerciseService()
	{
		// create exercise definition

		// read exercise definition

		// update exercise definition

		// delete exercise definition

		// find all checks delete

		// create exercise instance

		// read exercise instance

		// update exercise instance

		// delete exercise instance

		// find all checks delete

		// create exercise instance

		// read exercise instance

		// update exercise instance

		// delete exercise instance

		// find all checks delete

		// create exercise data

		// read exercise data

		// update exercise data

		// delete exercise data

		// find all checks delete
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
