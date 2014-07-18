package ch.zhaw.iwi.cis.pews.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang.ClassUtils;
import org.reflections.Reflections;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;

// -How is an object created?
// -What is it's scope?
// -How is it's lifecycle managed?
// -How are interceptors defined?
public class ManagedObjectRegistryImpl implements ManagedObjectRegistry
{
	private static MetaManagedObjectMap classMap;
	private static ManagedObjectMap singletonMap = new ManagedObjectMap();
	private static ThreadLocal< ManagedObjectMap > threadLocalMap = new ThreadLocal< ManagedObjectMap >();
	private static ManagedObjectsMap allObjects = new ManagedObjectsMap();
	
	@SuppressWarnings( "unchecked" )
	@Override
	public Object getManagedObject( String name )
	{
		Object mangagedObject = null;
		Scope scope = getManagedObjectAnnotation( name ).scope();
		
		if ( scope.equals( Scope.CLASSLOADER ) )
			mangagedObject = getSingletonObject( name );
		else if ( scope.equals( Scope.THREAD ) )
			mangagedObject = getThreadLocalObject( name );
		else if ( scope.equals( Scope.CLIENT ) )
			mangagedObject = getRequestObject( name );
		else if ( scope.equals( Scope.POOLED ) )
			mangagedObject = getPooledObject( name );
		
		return mangagedObject;
	}
	
	@Override
	public void registerManagedObjectType( LifecycleManager< ? > lifecycleManager, final String name, final Scope scope,
			final Transactionality transactionality, final int poolSize, final String entityManager )
	{
		ManagedObject managedObjectAnnotation = new ManagedObject() {
			@Override public Class< ? extends Annotation > annotationType() { return null; }
			@Override public Scope scope() { return scope; }
			@Override public Transactionality transactionality() { return transactionality; }
			@Override public int poolSize() { return poolSize; }
			@Override public String entityManager() { return null; }
		};

		getClassMap().put( name, new MetaManagedObject( lifecycleManager, managedObjectAnnotation ) );
	}
	
	@Override
	public void registerManagedObjectType( LifecycleManager< ? > lifecycleManager, final String name, final Scope scope  )
	{
		registerManagedObjectType( lifecycleManager, name, scope, ManagedObjectDefaults.DEFAULT_TRANSACTIONALITY, ManagedObjectDefaults.DEFAULT_POOL_SIZE, ManagedObjectDefaults.DEFAULT_ENTITY_MANAGER );
	}
	
	private static Object getSingletonObject( String name )
	{
		Object mangagedObject = singletonMap.get( name );
		
		if ( mangagedObject == null )
		{
			mangagedObject = createManagedObject( name );
			singletonMap.put( name, mangagedObject );
		}
		
		return mangagedObject;
	}

	private static Object getThreadLocalObject( String name )
	{
		ManagedObjectMap threadLocalMap = getThreadLocalMap();
		Object mangagedObject = threadLocalMap.get( name );
		
		if ( mangagedObject == null )
		{
			mangagedObject = createManagedObject( name );
			threadLocalMap.put( name, mangagedObject );
		}
		
		return mangagedObject;
	}
	
	private static ManagedObjectMap getThreadLocalMap()
	{
		ManagedObjectMap map = threadLocalMap.get();

		if ( map == null )
		{
			map = new ManagedObjectMap();
			threadLocalMap.set( map );
		}
		
		return map;
	}

	private static Object getRequestObject( String name )
	{
		return createManagedObject( name );
	}

	// TODO implement this
	private static Object getPooledObject( String name )
	{
		throw new UnsupportedOperationException();
	}

	private static ManagedObject getManagedObjectAnnotation( String name )
	{
		return getClassMap().get( name ).getManagedObjectAnnotation();
	}
	
	private static LifecycleManager< ? > getManagedObjectLifecycleManager( String name )
	{
		return getClassMap().get( name ).getLifecycleManager();
	}
	
	private static MetaManagedObjectMap getClassMap()
	{
		if ( classMap == null )
			classMap = createClassMap();
		
		return classMap;
	}

	private static MetaManagedObjectMap createClassMap()
	{
		MetaManagedObjectMap classmap = new MetaManagedObjectMap();
		Reflections reflections = new Reflections( "" );
		Set< Class< ? > > managedObjectTypes = reflections.getTypesAnnotatedWith( ManagedObject.class );
 
		// TODO introduce dual registration of simple and fully qualified class names.
		for ( Class< ? > managedObjectType : managedObjectTypes )
			classmap.put( managedObjectType.getSimpleName(), new MetaManagedObject( new SimpleObjectLifecycleManager( managedObjectType ),
				managedObjectType.getAnnotation( ManagedObject.class ) ) );

		return classmap;
	}
	
	private static Object createManagedObject( String name )
	{
		Object targetObject = getManagedObjectLifecycleManager( name ).create();
		ManagedObject managedObjectAnnotation = getManagedObjectAnnotation( name );
		Object proxyObject = getProxy( targetObject, managedObjectAnnotation );
		
		allObjects.add( name, proxyObject );
		
		return proxyObject;
	}

	@SuppressWarnings( "unchecked" )
	private static Object getProxy( Object targetObject, ManagedObject managedObjectAnnotation )
	{
		ManagedObjectInvocationHandler handler = new ManagedObjectInvocationHandler( targetObject, managedObjectAnnotation );
		List< Class< ? > > interfaces = ClassUtils.getAllInterfaces( targetObject.getClass() );
		Object proxy = Proxy.newProxyInstance( Thread.currentThread().getContextClassLoader(), interfaces.toArray( new Class< ? >[ interfaces.size() ] ), handler );

		return proxy;
	}

	@Override
	public void start() {}

	@Override
	public void stop()
	{
		destroyAll( EntityManager.class );
		destroyAll( EntityManagerFactory.class );
	}
	
	@SuppressWarnings( "unchecked" )
	private void destroyAll( Class< ? > destroyableClass )
	{
		for ( Entry< String, Set< Object > > entries : allObjects.entrySet() )
		{
			LifecycleManager< Object > lifecycleManager = (LifecycleManager< Object >)getManagedObjectLifecycleManager( entries.getKey() );
			
			for ( Object managedObject : entries.getValue() )
				if ( destroyableClass.isAssignableFrom( managedObject.getClass() ) )
					lifecycleManager.destroy( managedObject );
		}
	}
}
