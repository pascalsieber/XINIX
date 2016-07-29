package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.wrappers.TokenAuthenticationResponse;
import ch.zhaw.iwi.cis.pews.service.AuthenticationTokenService;
import ch.zhaw.iwi.cis.pews.service.rest.AuthenticationTokenRestService;

public class AuthenticationTokenServiceProxy extends WorkshopObjectServiceProxy implements AuthenticationTokenService
{

	protected AuthenticationTokenServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, AuthenticationTokenRestService.BASE );
	}

	@Override
	public String generateAuthenticationToken( String target )
	{
		return getServiceTarget().path( AuthenticationTokenRestService.GENERATE_TOKEN ).request( MediaType.APPLICATION_JSON ).post( Entity.json( target ) ).readEntity( String.class );
	}

	@Override
	public TokenAuthenticationResponse authenticateWithToken( String token )
	{
		return getServiceTarget()
			.path( AuthenticationTokenRestService.AUTHENTICATE_WITH_TOKEN )
			.request( MediaType.APPLICATION_JSON )
			.post( Entity.json( token ) )
			.readEntity( TokenAuthenticationResponse.class );
	}

	@Override
	public void removeBySessionID( String sessionID )
	{
		throw new UnsupportedOperationException( "internal service method, not accessible through REST" );
	}

}
