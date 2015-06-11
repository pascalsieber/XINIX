package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentIdentifierBag;
import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.internal.PersistentSortedMap;
import org.hibernate.collection.internal.PersistentSortedSet;
import org.hibernate.collection.spi.PersistentCollection;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.CloseableWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.IOExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.ObjectInputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.ObjectOutputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.OutputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.reflect.MethodWrapper;

public abstract class WorkshopObjectServiceImpl extends ServiceImpl implements WorkshopObjectService
{
	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		Object newObject = setClientInObjectGraph( object );
		return getWorkshopObjectDao().persist( (T)newObject );
	}

	@Override
	public < T extends WorkshopObject > void remove( T object )
	{
		getWorkshopObjectDao().remove( object );
	}

	@Override
	public < T extends WorkshopObject > T findByID( String id )
	{
		return getWorkshopObjectDao().findById( id );
	}

	@Override
	public < T extends WorkshopObject > List< T > findAll()
	{
		return getWorkshopObjectDao().findByAll( UserContext.getCurrentUser().getClient().getID() );
	}

	@Override
	public Object simplifyOwnerInObjectGraph( Object object )
	{
		byte[] byteArray = serialize( object );
		Object newObject = deserializeWithSimplifiedOwner( byteArray );

		return newObject;
	}

	protected abstract WorkshopObjectDao getWorkshopObjectDao();

	private Object setClientInObjectGraph( Object object )
	{
		byte[] byteArray = serialize( object );
		Object newObject = deserialize( byteArray );

		return newObject;
	}

	private byte[] serialize( Object object )
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = ObjectOutputStreamWrapper.__new( baos );
		ObjectOutputStreamWrapper.writeObject( oos, object );
		OutputStreamWrapper.flush( oos );
		OutputStreamWrapper.flush( baos );
		byte[] byteArray = baos.toByteArray();
		CloseableWrapper.close( oos );
		CloseableWrapper.close( baos );

		return byteArray;
	}

	private Object deserialize( byte[] byteArray )
	{
		ByteArrayInputStream bais = new ByteArrayInputStream( byteArray );
		ObjectInputStream ois = ClientReplacementInputStream.create( bais );
		Object object = ObjectInputStreamWrapper.readObject( ois );
		CloseableWrapper.close( ois );
		CloseableWrapper.close( bais );

		return object;
	}

	private Object deserializeWithSimplifiedOwner( byte[] byteArray )
	{
		ByteArrayInputStream bais = new ByteArrayInputStream( byteArray );
		ObjectInputStream ois = OwnerSimplificationInputStream.create( bais );
		Object object = ObjectInputStreamWrapper.readObject( ois );
		CloseableWrapper.close( ois );
		CloseableWrapper.close( bais );

		return object;
	}

	private static class ClientReplacementInputStream extends ObjectInputStream
	{
		public static ClientReplacementInputStream create( InputStream in )
		{
			try
			{
				return new ClientReplacementInputStream( in );
			}
			catch ( IOException e )
			{
				throw new IOExceptionWrapper( e );
			}
		}

		public ClientReplacementInputStream( InputStream in ) throws IOException
		{
			super( in );
			enableResolveObject( true );
		}

		@Override
		protected Object resolveObject( Object obj ) throws IOException
		{
			if ( obj instanceof WorkshopObject )
			{
				Method method = ClassWrapper.getMethod( WorkshopObject.class, "setClient", Client.class );
				MethodWrapper.invoke( method, obj, UserContext.getCurrentUser().getClient() );
			}

			return obj;
		}
	}

	private static class OwnerSimplificationInputStream extends ObjectInputStream
	{
		private PersistenceUnitUtil puu;
		private static final Map< Class< ? >, Class< ? > > PERSISTENT_TO_TRANSIENT_COLLECTION_MAP = new HashMap< Class< ? >, Class< ? > >();

		static
		{
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentList.class, ArrayList.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentSet.class, HashSet.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentSortedSet.class, TreeSet.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentMap.class, HashMap.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentSortedMap.class, TreeMap.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentBag.class, ArrayList.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentIdentifierBag.class, ArrayList.class );
		}

		public static OwnerSimplificationInputStream create( InputStream in )
		{
			try
			{
				return new OwnerSimplificationInputStream( in );
			}
			catch ( IOException e )
			{
				throw new IOExceptionWrapper( e );
			}
		}

		public OwnerSimplificationInputStream( InputStream in ) throws IOException
		{
			super( in );
			enableResolveObject( true );
			EntityManagerFactory emf = ZhawEngine.getManagedObjectRegistry().getManagedObject( "pewsFactory" );
			puu = emf.getPersistenceUnitUtil();
		}

		@Override
		protected Object resolveObject( Object obj ) throws IOException
		{
			if ( obj instanceof OwnableObject )
			{
				if ( null != ( (OwnableObject)obj ).getOwner() )
				{
					( (OwnableObject)obj ).getOwner().setCredential( null );
					( (OwnableObject)obj ).getOwner().setParticipation( null );
					( (OwnableObject)obj ).getOwner().setRole( null );
					( (OwnableObject)obj ).getOwner().setSessionAcceptances( null );
					( (OwnableObject)obj ).getOwner().setSessionExecutions( null );
					( (OwnableObject)obj ).getOwner().setSessionInvitations( null );

					if ( ( (OwnableObject)obj ).getOwner() instanceof UserImpl )
					{
						( (UserImpl)( (OwnableObject)obj ).getOwner() ).setGroups( null );
					}
				}
			}

			if ( isTerminalCollection( obj ) )
				return replacePersistentCollection( (PersistentCollection)obj );
			else
				return obj;
		}

		private boolean isTerminalCollection( Object sourceObject )
		{
			boolean isTerminalCollection;

			if ( sourceObject instanceof PersistentCollection && !puu.isLoaded( sourceObject ) )
				isTerminalCollection = true;
			else
				isTerminalCollection = false;

			return isTerminalCollection;
		}

		private Collection< ? > replacePersistentCollection( PersistentCollection pCollection )
		{
			Collection< ? > collection = null;

			Class< ? > transientCollectionClass = PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.get( pCollection.getClass() );

			if ( transientCollectionClass == null )
				throw new IllegalStateException( "Unexpected type for pCollection: " + pCollection.getClass().getName() );

			collection = (Collection< ? >)ClassWrapper.newInstance( transientCollectionClass );

			return collection;
		}
	}
}
