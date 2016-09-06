package ch.zhaw.sml.iwi.cis.pews.test.service.role;

import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.RoleRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class RoleRestServiceTest
{
	private static RoleService roleService;

	private static String NAME        = "name";
	private static String DESCRIPTION = "description";

	private static RoleImpl role = new RoleImpl();

	@BeforeClass public static void setup()
	{
		roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		role.setID( roleService.persist( new RoleImpl( NAME, DESCRIPTION ) ) );
		assertTrue( role.getID() != null );
		assertTrue( !role.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		RoleImpl found = roleService.findByID( role.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( role.getID() ) );
		assertTrue( found.getName().equals( NAME ) );
		assertTrue( found.getDescription().equals( DESCRIPTION ) );
	}

	@TestOrder( order = 3 ) @Test public void testFindAll()
	{
		RoleImpl findable = roleService.findByID( role.getID() );
		assertTrue( TestUtil.extractIds( roleService.findAll() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 4 ) @Test public void testRemove()
	{
		RoleImpl removable = roleService.findByID( role.getID() );
		assertTrue( TestUtil.extractIds( roleService.findAll() ).contains( removable ) );

		roleService.remove( role );
		assertTrue( roleService.findByID( role.getID() ) == null );
		assertTrue( !TestUtil.extractIds( roleService.findAll() ).contains( removable.getID() ) );
	}
}
