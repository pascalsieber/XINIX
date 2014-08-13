package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.SessionDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class SessionDaoImpl extends WorkshopObjectDaoImpl implements SessionDao
{

	@SuppressWarnings( "unchecked" )
	@Override
	public SessionImpl findById( String id )
	{
		List< SessionImpl > exercises = getEntityManager()
			.createQuery(
				"from SessionImpl as s LEFT JOIN FETCH s.workshop as w LEFT JOIN FETCH w.exercises as ex LEFT JOIN FETCH s.acceptees as acceptees LEFT JOIN FETCH s.participants as participants LEFT JOIN FETCH s.executers as executers where s.id = '"
						+ id + "'" )
			.getResultList();

		if ( exercises.size() > 0 )
		{
			return exercises.get( 0 );
		}

		return null;
	}

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return SessionImpl.class;
	}

}
