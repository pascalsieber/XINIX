package ch.zhaw.sml.iwi.cis.pews.test.service.user;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ClientServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.UserServiceProxy;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
@RunWith( OrderedRunner.class ) public class UserRestServiceTest
{
	private static UserService userService;

	private static UserImpl user       = new UserImpl();
	private static UserImpl clientUser = new UserImpl();

	private static Client   client = new Client();
	private static RoleImpl role   = new RoleImpl();

	private static String PASSWORD   = "password";
	private static String FIRST_NAME = "firstname";
	private static String LAST_NAME  = "lastname";
	private static String LOGIN      = "login@login.login";

	private static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeClass public static void setup()
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

	@Test @TestOrder( order = 1 ) public void testPersist()
	{
		user.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( PASSWORD ),
				role,
				null,
				FIRST_NAME,
				LAST_NAME,
				LOGIN ) ) );
		assertTrue( user.getID() != null );
		assertTrue( !user.getID().equals( "" ) );
	}

	@Test @TestOrder( order = 2 ) public void testPersistForClient()
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

	@Test @TestOrder( order = 3 ) public void testFind()
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

	@Test @TestOrder( order = 4 ) public void testFindByLogin()
	{
		PrincipalImpl pr = userService.findByLoginName( LOGIN );
		assertTrue( userService.findByLoginName( LOGIN ).getID().equals( user.getID() ) );
	}

	@Test @TestOrder( order = 5 ) public void testSendProfile()
	{
		// not checking email, just if API call runs through
		userService.sendProfile( user.getID() );
	}

	@Test @TestOrder( order = 6 ) public void testFindAll()
	{
		UserImpl findable = (UserImpl)userService.findUserByID( user.getID() );
		assertTrue( findable != null );
		assertTrue( extractIds( userService.findAll() ).contains( findable.getID() ) );
	}

	@Test @TestOrder( order = 7 ) public void testFindAllByClientID()
	{
		UserImpl findable = (UserImpl)userService.findUserByID( clientUser.getID() );
		assertTrue( findable != null );
		assertTrue( extractIds( userService.findAllByClientID( client.getID() ) ).contains( findable.getID() ) );
	}

	@Test @TestOrder( order = 8 ) public void testRemove()
	{
		UserImpl removable = (UserImpl)userService.findUserByID( user.getID() );
		assertTrue( extractIds( userService.findAll() ).contains( removable.getID() ) );

		userService.remove( removable );
		assertTrue( userService.findUserByID( user.getID() ) == null );
		assertTrue( !extractIds( userService.findAll() ).contains( removable.getID() ) );
	}

	private List<String> extractIds( List<? extends IdentifiableObject> identifiableObjects )
	{
		List<String> ids = new ArrayList<>();
		List<IdentifiableObject> objects = new ArrayList<>();

		try
		{
			objects = objectMapper.readValue( objectMapper.writeValueAsString( identifiableObjects ),
					TypeFactory.defaultInstance()
							.constructCollectionType( ArrayList.class, IdentifiableObject.class ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		for ( IdentifiableObject identifiableObject : objects )
		{
			ids.add( identifiableObject.getID() );
		}
		return ids;
	}
}
