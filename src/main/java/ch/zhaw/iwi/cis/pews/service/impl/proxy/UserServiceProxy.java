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
	public void acceptInvitation( int userID, int sessionID )
	{
		getServiceTarget().path( UserRestService.USER_ACCEPT_INVITATION ).request(MediaType.APPLICATION_JSON).post( Entity.json( sessionID ) );
		
	}

	@Override
	public PrincipalImpl findByLoginName( String loginName )
	{
		// method exists as helper for joining and leaving sessions. not to be used as proxy method.
		throw new UnsupportedOperationException();
	}

}
