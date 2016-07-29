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

	@SuppressWarnings( "unchecked" )
	@Override
	public WorkshopImpl findWorkshopByID( String id )
	{
		List< WorkshopImpl > results = getEntityManager()
			.createQuery( "from WorkshopImpl ws LEFT JOIN FETCH ws.sessions sessions LEFT JOIN FETCH ws.exercises ex where ws.id = '" + id + "'" )
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
	public WorkshopImpl findWorkshopByIDForBasicUpdate( String id )
	{
		List< WorkshopImpl > results = getEntityManager()
			.createQuery(
				"from WorkshopImpl ws LEFT JOIN FETCH ws.sessions s LEFT JOIN FETCH s.invitations inv LEFT JOIN FETCH s.participants p LEFT JOIN FETCH p.principal LEFT JOIN FETCH p.session LEFT JOIN FETCH ws.exercises ex where ws.id = '"
						+ id + "'" )
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

}
