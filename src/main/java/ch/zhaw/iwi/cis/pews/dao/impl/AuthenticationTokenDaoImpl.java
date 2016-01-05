package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.AuthenticationTokenDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.authentication.AuthenticationToken;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class AuthenticationTokenDaoImpl extends WorkshopObjectDaoImpl implements AuthenticationTokenDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return AuthenticationToken.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public AuthenticationToken findByToken( String token )
	{
		List< AuthenticationToken > results = getEntityManager()
			.createQuery( "from AuthenticationToken a LEFT JOIN FETCH a.owner where a.token = :_token" )
			.setParameter( "_token", token )
			.getResultList();
		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			return null;
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< AuthenticationToken > findAllBySessionID( String sessionID )
	{
		return getEntityManager().createQuery( "from AuthenticationToken a where a.sessionID = :_sessionID" ).setParameter( "_sessionID", sessionID ).getResultList();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< String > getAllTokens()
	{
		return getEntityManager().createQuery( "SELECT token from AuthenticationToken" ).getResultList();
	}

}
