package ch.zhaw.iwi.cis.pews.framework;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryLifecycleManager implements LifecycleManager< EntityManagerFactory >
{
	private String persistenceUnit;

	public EntityManagerFactoryLifecycleManager( String persistenceUnit )
	{
		super();
		this.persistenceUnit = persistenceUnit;
	}

	@Override
	public EntityManagerFactory create()
	{
		return Persistence.createEntityManagerFactory( persistenceUnit );
	}

	@Override
	public void destroy( EntityManagerFactory object )
	{
		object.close();
	}
}
