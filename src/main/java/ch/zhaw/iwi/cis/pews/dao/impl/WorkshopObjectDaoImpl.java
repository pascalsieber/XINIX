package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

public abstract class WorkshopObjectDaoImpl implements WorkshopObjectDao
{
	protected EntityManager getEntityManager()
	{
		return ZhawEngine.getManagedObjectRegistry().getManagedObject( "pews" );
	}

	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		if ( object.getClient() == null )
		{
			throw new UnsupportedOperationException( "reference to client is missing!" );
		}
		
		WorkshopObject objectMerged = merge( object );
		getEntityManager().persist( objectMerged );
		
		return objectMerged.getID();
	}

	@Override
	public < T extends WorkshopObject > void remove( T object )
	{
		WorkshopObject objectMerged = getEntityManager().merge( object );
		getEntityManager().remove( objectMerged );
	}

	@Override
	public < T extends WorkshopObject > T merge( T object )
	{
		return getEntityManager().merge( object );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > T findById( String id )
	{
		return (T)getEntityManager().find( getWorkshopObjectClass(), id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > List< T > findByAll( String clientID )
	{
		return getEntityManager().createQuery( "from " + getWorkshopObjectClass().getSimpleName() + " where client.id = '" + clientID + "'" ).getResultList();
	}

	protected abstract Class< ? extends WorkshopObject > getWorkshopObjectClass();
}
