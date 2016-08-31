package ch.zhaw.sml.iwi.cis.pews.test.service.client;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ClientServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ClientRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class)
public class ClientRestServiceTest
{

	private static ClientService clientService;

	private static String NAME = "name";

	private Client client = new Client();

	@BeforeClass public static void setup()
	{
		clientService = ServiceProxyManager.createServiceProxy( ClientServiceProxy.class );
	}

	@TestOrder( order = 1)
	@Test public void testPersist()
	{
		client.setID( clientService.persist( new Client( NAME ) ) );
		assertTrue( client.getID() != null );
		assertTrue( !client.getID().equals( "" ) );
	}

	@TestOrder( order = 2)
	@Test public void testFind()
	{
		Client found = clientService.findByID( client.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( client.getID() ) );
		assertTrue( found.getName().equals( NAME ) );
	}

	@TestOrder( order = 3)
	@Test public void testFindAll()
	{
		Client findable = clientService.findByID( client.getID() );
		assertTrue( clientService.findAll().contains( findable ) );
	}

	@TestOrder( order = 4)
	@Test public void testRemove()
	{
		Client removable = clientService.findByID( client.getID() );
		assertTrue( clientService.findAll().contains( removable ) );

		clientService.remove( client );
		assertTrue( clientService.findByID( client.getID() ) == null );
		assertTrue( !clientService.findAll().contains( removable ) );
	}
}
