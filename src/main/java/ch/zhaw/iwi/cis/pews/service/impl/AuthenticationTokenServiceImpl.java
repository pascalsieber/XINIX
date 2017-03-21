package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.Random;

import ch.zhaw.iwi.cis.pews.dao.AuthenticationTokenDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.AuthenticationTokenDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.authentication.AuthenticationToken;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.TokenAuthenticationResponse;
import ch.zhaw.iwi.cis.pews.service.AuthenticationTokenService;

import javax.inject.Inject;

public class AuthenticationTokenServiceImpl extends WorkshopObjectServiceImpl implements AuthenticationTokenService
{
	@Inject private AuthenticationTokenDao authenticationTokenDao;
	private final Random generator = new Random();

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return authenticationTokenDao;
	}

	@Override
	public String generateAuthenticationToken( String target )
	{
		String token = getNewToken();
		authenticationTokenDao.persist( new AuthenticationToken( UserContext.getCurrentUser(), token, target, UserContext.getCurrentUser().getParticipation().getSession().getID() ) );
		return token;
	}

	@Override
	public TokenAuthenticationResponse authenticateWithToken( String token )
	{
		AuthenticationToken authenticationToken = authenticationTokenDao.findByToken( token );
		if ( authenticationToken == null )
		{
			throw new RuntimeException( "Token could not be found" );
		}
		else
		{
			TokenAuthenticationResponse response = new TokenAuthenticationResponse( ( (UserImpl)authenticationToken.getOwner() ).getLoginName(), authenticationToken
				.getOwner()
				.getCredential()
				.getPassword(), authenticationToken.getTarget(), authenticationToken.getSessionID() );

			// remove token (one-time token)
			remove( authenticationToken );

			return response;
		}
	}

	@Override
	public void removeBySessionID( String sessionID )
	{
		for ( AuthenticationToken authenticationToken : authenticationTokenDao.findAllBySessionID( sessionID ) )
		{
			authenticationTokenDao.remove( authenticationToken );
		}
	}

	private String getNewToken()
	{
		// random six digit token
		String rand = Integer.toString( generator.nextInt( 899999 ) + 100000 );
		if ( this.verifyToken( rand ) )
		{
			return rand;
		}
		else
		{
			return getNewToken();
		}
	}

	private boolean verifyToken( String token )
	{
		return !authenticationTokenDao.getAllTokens().contains( token );
	}

}
