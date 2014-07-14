package ch.zhaw.iwi.cis.pews.framework;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerLifecycleManager implements LifecycleManager< EntityManager >
{
	private EntityManagerFactory factory;

	public EntityManagerLifecycleManager( EntityManagerFactory factory )
	{
		super();
		this.factory = factory;
	}

	@Override
	public EntityManager create()
	{
		return factory.createEntityManager();
	}

	@Override
	public void destroy( EntityManager object )
	{
		object.close();
	}
}
