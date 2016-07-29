package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.rest.UserRestService;

public class UserServiceProxy extends WorkshopObjectServiceProxy implements UserService
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
	public PrincipalImpl findByLoginNameForUserContext( String loginName )
	{
		// return same as findByLoginName
		return getServiceTarget().path( UserRestService.FIND_BY_LOGIN_NAME ).request( MediaType.APPLICATION_JSON ).post( Entity.json( loginName ) ).readEntity( PrincipalImpl.class );
	}

	@Override
	public boolean requestNewPassword( String userID )
	{
		return getServiceTarget().path( UserRestService.REQUEST_PASSWORD ).request( MediaType.APPLICATION_JSON ).post( Entity.json( userID ) ).readEntity( boolean.class );
	}

	@Override
	public List< PrincipalImpl > findAllUsersForLoginService()
	{
		// method not to be exposed. used internally to find all users to be able to authenticate
		throw new UnsupportedOperationException();
	}

	@Override
	public PrincipalImpl findUserByID( String id )
	{
		return getServiceTarget().path( UserRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( PrincipalImpl.class );
	}

	@Override
	public void sendProfile( String userID )
	{
		getServiceTarget().path( UserRestService.SEND_PROFILE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( userID ) );
	}

	@Override
	public String persistForClient( PrincipalImpl principal )
	{
		return getServiceTarget().path( UserRestService.PERSIST_FOR_CLIENT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( principal ) ).readEntity( String.class );
	}
}
