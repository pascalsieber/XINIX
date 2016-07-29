package ch.zhaw.iwi.cis.pews.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.wrappers.TokenAuthenticationResponse;
import ch.zhaw.iwi.cis.pews.service.AuthenticationTokenService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.AuthenticationTokenServiceImpl;

@Path( AuthenticationTokenRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class AuthenticationTokenRestService extends WorkshopObjectRestService
{
	public static final String BASE = "/authenticationService";
	public static final String GENERATE_TOKEN = "/generateToken";
	public static final String AUTHENTICATE_WITH_TOKEN = "authenticateWithToken";

	private AuthenticationTokenService authenticationService;

	public AuthenticationTokenRestService()
	{
		super();
		authenticationService = ZhawEngine.getManagedObjectRegistry().getManagedObject( AuthenticationTokenServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( GENERATE_TOKEN )
	public String generateToken( String target )
	{
		return authenticationService.generateAuthenticationToken( target );
	}

	@POST
	@Path( AUTHENTICATE_WITH_TOKEN )
	public TokenAuthenticationResponse authenticateWithToken( String token )
	{
		return authenticationService.authenticateWithToken( token );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return authenticationService;
	}
}
