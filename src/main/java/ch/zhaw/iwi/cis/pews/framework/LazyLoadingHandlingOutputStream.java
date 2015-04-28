package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;

public class LazyLoadingHandlingOutputStream extends ObjectOutputStream
{
	private PersistenceUnitUtil puu;
	
	public LazyLoadingHandlingOutputStream( OutputStream out ) throws IOException
	{
		super( out );
		enableReplaceObject( true );
		EntityManagerFactory emf = ZhawEngine.getManagedObjectRegistry().getManagedObject( "pewsFactory" );
		puu = emf.getPersistenceUnitUtil();
	}

	@Override
	protected Object replaceObject( Object sourceObject ) throws IOException
	{
		if ( isTerminalCollection( sourceObject ) )
			return replacePersistentCollection( (PersistentCollection)sourceObject );
		else
			return sourceObject;
	}
	
	private boolean isTerminalCollection( Object sourceObject )
	{
		boolean isTerminalCollection;
		
		if ( sourceObject instanceof PersistentCollection && !puu.isLoaded( sourceObject ) )
			isTerminalCollection = true;
		else
			isTerminalCollection = false;
		
		return isTerminalCollection;
		
//		boolean isTerminalCollection = false;		
//		
//		if ( sourceObject instanceof PersistentCollection )
//		{
//			Collection< ? > collection = (Collection< ? >)sourceObject;
//			
//			try
//			{
//				collection.size();
//			}
//			catch ( LazyInitializationException e )
//			{
//				isTerminalCollection = true;
//			}
//		}
//		
//		return isTerminalCollection;
	}
	
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
