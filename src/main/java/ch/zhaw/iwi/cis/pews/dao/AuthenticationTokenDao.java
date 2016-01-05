package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.authentication.AuthenticationToken;

public interface AuthenticationTokenDao extends WorkshopObjectDao
{

	public AuthenticationToken findByToken( String token );

	public List< AuthenticationToken > findAllBySessionID( String sessionID );

	public List< String > getAllTokens();

}
