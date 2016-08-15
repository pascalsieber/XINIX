package ch.zhaw.sml.iwi.cis.pews.test.service.user;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ClientServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.UserRestService}
 * - PERSIST
 * - PERSIST_FOR_CLIENT
 * - FIND_BY_ID
 * - FIND_BY_LOGIN_NAME
 * - SEND_PROFILE
 * - FIND_ALL
 * - FIND_ALL_BY_CLIENT_ID
 * - REMOVE
 */
public class UserRestServiceTest
{
	private UserService userService;

	private UserImpl user       = new UserImpl();
	private UserImpl clientUser = new UserImpl();

	private Client   client = new Client();
	private RoleImpl role   = new RoleImpl();

	private static String PASSWORD   = "password";
	private static String FIRST_NAME = "firstname";
	private static String LAST_NAME  = "lastname";
	private static String LOGIN      = "login";

	@BeforeClass public void setup()
	{
		// services
		userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		ClientService clientService = ServiceProxyManager.createServiceProxy( ClientServiceProxy.class );
		RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );

		// client
		client.setID( clientService.persist( new Client( "clientname" ) ) );

		// role
		role.setID( roleService.persist( new RoleImpl( "", "" ) ) );
	}

	@Test public void testPersist()
	{
		userService.persist( new UserImpl( new PasswordCredentialImpl( PASSWORD ),
				role,
				null,
				FIRST_NAME,
				LAST_NAME,
				LOGIN ) );
		assertTrue( user.getID() != null );
		assertTrue( !user.getID().equals( "" ) );
	}

	@Test public void testPersistForClient()
	{
		clientUser = new UserImpl( new PasswordCredentialImpl( "" ), role, null, "", "", "" );
		clientUser.setClient( client );
		clientUser.setID( userService.persistForClient( clientUser ) );

		assertTrue( clientUser.getID() != null );
		assertTrue( !clientUser.getID().equals( "" ) );

		UserImpl check = (UserImpl)userService.findUserByID( clientUser.getID() );
		assertTrue( check.getClient().getID() != null );
		assertTrue( !check.getClient().getID().equals( "" ) );
		assertTrue( check.getClient().getID().equals( client.getID() ) );
	}

	@Test public void testFind()
	{
		UserImpl found = (UserImpl)userService.findUserByID( user.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( user.getID() ) );
		assertTrue( found.getParticipation() == null );
		assertTrue( found.getFirstName().equals( FIRST_NAME ) );
		assertTrue( found.getLastName().equals( LAST_NAME ) );
		assertTrue( found.getLoginName().equals( LOGIN ) );
		assertTrue( found.getCredential().getPassword().equals( PASSWORD ) );
		assertTrue( found.getRole().getID().equals( role.getID() ) );
		assertTrue( found.getSession() == null );
	}

	@Test public void testFindByLogin()
	{
		assertTrue( userService.findByLoginName( user.getLoginName() ).getID().equals( user.getID() ) );
	}

	@Test public void testSendProfile()
	{
		// not checking email, just if API call runs through
		userService.sendProfile( user.getID() );
	}

	@Test public void testFindAll()
	{
		UserImpl findable = (UserImpl)userService.findUserByID( user.getID() );
		assertTrue( userService.findAll().contains( findable ) );
	}

	@Test public void testFindAllByClientID()
	{
		UserImpl findable = (UserImpl)userService.findUserByID( clientUser.getID() );
		assertTrue( findable != null );
		assertTrue( userService.findAllByClientID( client.getID() ).contains( findable ) );
	}

	@Test public void testRemove()
	{
		UserImpl removable = (UserImpl)userService.findUserByID( user.getID() );
		assertTrue( userService.findAll().contains( removable ) );

		userService.remove( removable );
		assertTrue( userService.findUserByID( user.getID() ) == null );
		assertTrue( !userService.findAll().contains( removable ) );
	}
}
