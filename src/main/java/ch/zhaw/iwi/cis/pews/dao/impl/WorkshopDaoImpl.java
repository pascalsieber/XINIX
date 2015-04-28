package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopDaoImpl extends WorkshopObjectDaoImpl implements WorkshopDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return WorkshopImpl.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopImpl > findByAllSimple( String clientID )
	{
		List< WorkshopImpl > results = getEntityManager().createQuery(
			"from WorkshopImpl ws LEFT JOIN FETCH ws.sessions sessions LEFT JOIN FETCH ws.exercises ex where ws.client.id = '" + clientID + "'" ).getResultList();
		return new ArrayList< WorkshopImpl >( new HashSet< WorkshopImpl >( (List< WorkshopImpl >)cloneResult( results ) ) );
	}

}
