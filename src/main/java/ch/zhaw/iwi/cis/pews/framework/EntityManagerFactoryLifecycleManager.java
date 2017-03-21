package ch.zhaw.iwi.cis.pews.framework;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class EntityManagerFactoryLifecycleManager implements LifecycleManager< EntityManagerFactory >
{
	private String persistenceUnit;
	private Map<String, String> properties;

	public EntityManagerFactoryLifecycleManager( String persistenceUnit )
	{
	    this( persistenceUnit, null );
	}

	public EntityManagerFactoryLifecycleManager( String persistenceUnit , Map<String, String> properties)
	{
		super();
		this.persistenceUnit = persistenceUnit;
		this.properties = properties;
	}

	@Override
	public EntityManagerFactory create()
	{
	    if ( properties == null ) {
			return Persistence.createEntityManagerFactory( persistenceUnit );
		} else {
			return Persistence.createEntityManagerFactory( persistenceUnit, properties );
		}
	}

	@Override
	public void destroy( EntityManagerFactory object )
	{
		object.close();
	}
}
