package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

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
			"workshop definition test entry", "problem description" ) );

		// new workshop instance
		defaultWorkshopID = workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", (WorkflowElementDefinitionImpl)workshopService.findByID( defaultWorkshopDefinitionID ) ) );

		// new session
		defaultSessionID = sessionService.persist( new SessionImpl( "session", "test session", null, (WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );

		// new exercise definition
		defaultExerciseDefinitionID = exerciseDefinitionService.persist( new ExerciseDefinitionImpl( (PrincipalImpl)userService.findByID( defaultUserID ), TimeUnit.SECONDS, 120, (WorkshopDefinitionImpl)workshopDefinitionService.findByID( defaultWorkshopDefinitionID ) ) );

		// new exercise
		defaultExerciseID = exerciseService.persist( new ExerciseImpl(
			"exercise",
			"exercise test instance",
			(ExerciseDefinitionImpl)exerciseService.findByID( defaultExerciseDefinitionID ),
			(WorkshopImpl)workshopService.findByID( defaultWorkshopID ) ) );
	}

	@Test
	public void crudOperationsUser()
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
		roleService.remove( userService.findByID( roleID ) );

		// find all checks delete
		assertTrue( !roleService.findAll( RoleImpl.class.getSimpleName() ).contains( updatedRole ) );

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

		// find all check delete
		assertTrue( !userService.findAll( UserImpl.class.getSimpleName() ).contains( updatedUser ) );

	}

	@Test
	public void crudOperationsWorkshop()
	{

	}

	@Test
	public void crudOperationsExercise()
	{

	}
}
