package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.impl.RoleServiceImpl;

@Path( RoleRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class RoleRestService extends IdentifiableObjectRestService
{

	public static final String BASE = "/userService/role";

	private RoleService roleService;

	public RoleRestService()
	{
		roleService = ZhawEngine.getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public int persist( RoleImpl obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public RoleImpl findById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( RoleImpl obj )
	{
		super.remove( obj );
	}

	@POST
	@Path( FIND_ALL )
	public List< RoleImpl > findAll()
	{
		return super.findAll( RoleImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return roleService;
	}

}
