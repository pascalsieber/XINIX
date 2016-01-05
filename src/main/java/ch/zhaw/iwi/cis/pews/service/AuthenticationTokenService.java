package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.wrappers.TokenAuthenticationResponse;

public interface AuthenticationTokenService extends WorkshopObjectService
{

	public String generateAuthenticationToken( String target );

	public TokenAuthenticationResponse authenticateWithToken( String token );

	public void removeBySessionID( String sessionID );

}
