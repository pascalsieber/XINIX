package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.RoleServiceImpl;

@Path( RoleRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class RoleRestService extends WorkshopObjectRestService
{

	public static final String BASE = "/userService/role";

	private RoleService roleService;

	public RoleRestService()
	{
		super();
		roleService = ZhawEngine.getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( RoleImpl obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public RoleImpl findByID( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( RoleImpl obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< RoleImpl > findAll()
	{
		return roleService.findAllRoles();
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return roleService;
	}

}
