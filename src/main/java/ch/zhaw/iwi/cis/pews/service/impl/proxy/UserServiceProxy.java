package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.rest.UserRestService;

public class UserServiceProxy extends IdentifiableObjectServiceProxy implements UserService
{

	protected UserServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, UserRestService.USER_BASE );
	}

	@Override
	public PrincipalImpl findByLoginName( String loginName )
	{
		return getServiceTarget().path( UserRestService.FIND_BY_LOGIN_NAME ).request( MediaType.APPLICATION_JSON ).post( Entity.json( loginName ) ).readEntity( PrincipalImpl.class );
	}

	@Override
	public boolean requestNewPassword( String userID )
	{
		return getServiceTarget().path( UserRestService.REQUEST_PASSWORD ).request( MediaType.APPLICATION_JSON ).post( Entity.json( userID ) ).readEntity( boolean.class );
	}

}
