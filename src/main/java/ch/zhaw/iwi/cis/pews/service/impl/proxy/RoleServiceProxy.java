package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.rest.RoleRestService;

public class RoleServiceProxy extends WorkshopObjectServiceProxy implements RoleService
{

	protected RoleServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, RoleRestService.BASE );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< RoleImpl > findAllRoles()
	{
		return getServiceTarget().path( RoleRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

}
