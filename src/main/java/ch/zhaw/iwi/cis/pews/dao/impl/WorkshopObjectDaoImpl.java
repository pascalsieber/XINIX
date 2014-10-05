package ch.zhaw.iwi.cis.pews.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.persistence.EntityManager;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.framework.LazyLoadingHandlingOutputStream;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
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
			//TODO double check with John!
//			throw new UnsupportedOperationException( "reference to client is missing!" );
			object.setClient( UserContext.getCurrentUser().getClient() );
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
		return (List< T >)cloneResult( getEntityManager().createQuery( "from " + getWorkshopObjectClass().getSimpleName() + " where client.id = '" + clientID + "'" ).getResultList() );
	}

	protected abstract Class< ? extends WorkshopObject > getWorkshopObjectClass();
	
	@SuppressWarnings( "resource" )
	public Object cloneResult (Object object)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream ooStream = new LazyLoadingHandlingOutputStream( baos );
			ooStream.writeObject( object );

			byte[] bytes = baos.toByteArray();

			ByteArrayInputStream bais = new ByteArrayInputStream( bytes );
			ObjectInputStream oiStream = new ObjectInputStream( bais );
			return oiStream.readObject();
		}
		catch ( IOException | ClassNotFoundException e )
		{
			throw new RuntimeException( e );
		}

	}
}
