package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import javax.persistence.EntityManager;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

public abstract class IdentifiableObjectDaoImpl implements IdentifiableObjectDao
{
	protected EntityManager getEntityManager()
	{
		return ZhawEngine.getManagedObjectRegistry().getManagedObject( "pews" );
	}

	@Override
	public < T extends IdentifiableObject > int persist( T object )
	{
		IdentifiableObject objectMerged = merge( object );
		getEntityManager().persist( objectMerged );

		return objectMerged.getID();
	}

	@Override
	public < T extends IdentifiableObject > void remove( T object )
	{
		IdentifiableObject objectMerged = getEntityManager().merge( object );
		getEntityManager().remove( objectMerged );
	}

	public < T extends IdentifiableObject > T merge( T object )
	{
		return getEntityManager().merge( object );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > T findById( Integer id )
	{
		return (T)getEntityManager().find( getPersistentObjectClass(), id );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > List< T > findByAll(String className)
	{
		return getEntityManager().createQuery( "from " + className ).getResultList();
	}

	protected abstract Class< ? extends IdentifiableObject > getPersistentObjectClass();
}
