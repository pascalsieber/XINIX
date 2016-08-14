package ch.zhaw.sml.iwi.cis.pews.test.service.role;

import static org.junit.Assert.assertTrue;

import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.RoleServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import org.junit.Test;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.RoleRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
public class RoleRestServiceTest
{
	private static RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );

	private static String NAME = "name";
	private static String DESCRIPTION = "description";

	private RoleImpl role;

	@Test public void testPersist()
	{
		role.setID( roleService.persist( new RoleImpl( NAME, DESCRIPTION ) ) );
		assertTrue( role.getID() != null );
		assertTrue( !role.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		RoleImpl found = roleService.findByID( role.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( role.getID() ) );
		assertTrue( found.getName().equals( NAME ) );
		assertTrue( found.getDescription().equals( DESCRIPTION ) );
	}
	@Test public void testFindAll()
	{
		RoleImpl findable = roleService.findByID( role.getID() );
		assertTrue( roleService.findAll().contains( findable ) );
	}

	@Test public void testRemove()
	{
		RoleImpl removable = roleService.findByID( role.getID() );
		assertTrue( roleService.findAll().contains( removable ) );

		roleService.remove( role );
		assertTrue( roleService.findByID( role.getID() ) == null );
		assertTrue( !roleService.findAll().contains( removable ) );
	}
}
