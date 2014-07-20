package ch.zhaw.sml.iwi.cis.pews.test.service;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
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
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopServiceProxy;

public class RestServiceTest
{
	private UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
	private WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
	private ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );

	private int defaultWorkshopDefinitionID;
	private int defaultWorkshopID;
	private int defaultSessionID;
	private int defaultExerciseDefinitionID;
	private int defaultExerciseID;
	private int defaultRoleID;
	private int defaultUserID;

	@Before
	public void setupTest()
	{
		// new role
		defaultRoleID = userService.persist( new RoleImpl( "user", "user role" ) );

		// new User
		defaultUserID = userService.persist( new UserImpl( new PasswordCredentialImpl( "password" ), (RoleImpl)userService.findByID( defaultRoleID ), null, "John", "Smith", "john.smith@mail.com" ) );

		// new workshop definition
		defaultWorkshopDefinitionID = workshopService.persist( new WorkshopDefinitionImpl(
			(PrincipalImpl)userService.findByID( defaultUserID ),
			"workshop definition",
			"workshop definition test entry" ) );

		// new workshop instance
		defaultWorkshopID = workshopService.persist( new WorkshopImpl( "workshop", "workshop test instance", (WorkflowElementDefinitionImpl)workshopService.findByID( defaultWorkshopDefinitionID ) ) );

		// new session
		defaultSessionID = workshopService.persist( new SessionImpl( "session", "test session", null, (WorkshopImpl)workshopService.findByID( defaultSessionID ) ) );

		// new exercise definition
		defaultExerciseDefinitionID = exerciseService.persist( new ExerciseDefinitionImpl( (PrincipalImpl)userService.findByID( defaultUserID ), TimeUnit.SECONDS, 120 ) );

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
		int roleID = userService.persist( new RoleImpl( "new role", "new role description" ) );
		assertTrue( roleID > 0 );

		// read role
		RoleImpl role = userService.findByID( roleID );
		assertTrue( role.getID() == roleID );
		assertTrue( role.getName().equalsIgnoreCase( "new role" ) );
		assertTrue( role.getDescription().equalsIgnoreCase( "new role description" ) );

		// update role
		role.setName( "updated role" );
		userService.persist( role );
		RoleImpl updatedRole = userService.findByID( roleID );
		assertTrue( updatedRole.getName().equalsIgnoreCase( "updated role" ) );

		// delete role
		userService.remove( userService.findByID( roleID ) );

		// find all checks delete
		assertTrue( !userService.findAll( RoleImpl.class.getSimpleName() ).contains( updatedRole ) );

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
