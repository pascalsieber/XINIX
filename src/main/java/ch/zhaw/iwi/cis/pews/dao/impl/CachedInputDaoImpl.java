package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.CachedInputDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.input.CachedInput;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CachedInputDaoImpl extends WorkshopObjectDaoImpl implements CachedInputDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return CachedInput.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public CachedInput findBySessionID( String sessionID )
	{
		List< CachedInput > results = getEntityManager().createQuery( "from CachedInput c where c.sessionID = :_session_id" ).setParameter( "_session_id", sessionID ).getResultList();
		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			return null;
		}
	}
}
