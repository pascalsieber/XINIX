package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class SessionDaoImpl extends IdentifiableObjectDaoImpl implements SessionDao
{

	@SuppressWarnings( "unchecked" )
	@Override
	public SessionImpl findById( Integer id )
	{
		List< SessionImpl > exercises = getEntityManager().createQuery( "from SessionImpl as s LEFT JOIN FETCH s.workshop as w LEFT JOIN FETCH w.exercises as ex where s.id = " + id ).getResultList();

		if ( exercises.size() > 0 )
		{
			return exercises.get( 0 );
		}

		return null;
	}

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return SessionImpl.class;
	}

}
