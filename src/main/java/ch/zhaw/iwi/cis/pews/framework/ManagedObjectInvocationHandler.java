package ch.zhaw.iwi.cis.pews.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.spi.PersistenceUnitTransactionType;

public class ManagedObjectInvocationHandler implements InvocationHandler
{
	private Object managedObject;
	private ManagedObject managedObjectAnnotation;

	public ManagedObjectInvocationHandler( Object managedObject, ManagedObject managedObjectAnnotation )
	{
		super();
		this.managedObject = managedObject;
		this.managedObjectAnnotation = managedObjectAnnotation;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
	{
		Object retVal = null;

		EntityTransaction entityTransaction = getEntityManagerTransaction();

		if ( entityTransaction == null )
			retVal = invokeWithoutTransaction( proxy, method, args );
		else if ( entityTransaction.isActive() )
			retVal = invokeWithExistingTransaction( proxy, method, args, entityTransaction );
		else
			retVal = invokeWithNewTransaction( proxy, method, args, entityTransaction );

		return retVal;
	}

	private EntityTransaction getEntityManagerTransaction()
	{
		EntityTransaction entityTransaction = null;

		EntityManager entityManager = getEntityManager();

		if ( entityManager != null )
		{
			if ( getTransactionType( entityManager ).equals( PersistenceUnitTransactionType.RESOURCE_LOCAL ) )
				entityTransaction = entityManager.getTransaction();
		}

		return entityTransaction;
	}

	private EntityManager getEntityManager()
	{
		EntityManager entityManager = null;
		String entityManagerName = managedObjectAnnotation.entityManager();

		if ( entityManagerName != null && !entityManagerName.isEmpty() )
			entityManager = (EntityManager)ZhawEngine.getManagedObjectRegistry().getManagedObject( entityManagerName );

		return entityManager;
	}

	private PersistenceUnitTransactionType getTransactionType( EntityManager entityManager )
	{
		// TOOD find out how to derive this from the entityManager.
		return PersistenceUnitTransactionType.RESOURCE_LOCAL;
	}

	private Object invokeWithoutTransaction( Object proxy, Method method, Object[] args ) throws Throwable
	{
		Object retVal = null;

		try
		{
			retVal = method.invoke( managedObject, args );
		}
		catch ( InvocationTargetException e )
		{
			throw e.getCause();
		}

		return retVal;
	}

	private Object invokeWithExistingTransaction( Object proxy, Method method, Object[] args, EntityTransaction tx ) throws Throwable
	{
		Object retVal = null;

		try
		{
			retVal = invokeWithoutTransaction( proxy, method, args );
		}
		catch ( Throwable t )
		{
			if ( tx.isActive() )
				tx.rollback();

			throw t;
		}

		return retVal;
	}

	private Object invokeWithNewTransaction( Object proxy, Method method, Object[] args, EntityTransaction tx ) throws Throwable
	{
		Object retVal = null;

		tx.begin();
		getEntityManager().clear();

		try
		{
			retVal = invokeWithExistingTransaction( proxy, method, args, tx );
		}
		finally
		{
			if ( tx.isActive() && !tx.getRollbackOnly() )
				tx.commit();
		}

		return retVal;
	}
}
