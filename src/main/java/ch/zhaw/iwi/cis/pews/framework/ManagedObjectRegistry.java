package ch.zhaw.iwi.cis.pews.framework;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;

public interface ManagedObjectRegistry extends LifecycleObject
{
	public < T > T getManagedObject( String name );
	public void registerManagedObjectType( LifecycleManager< ? > lifecycleManager, String name, Scope scope, Transactionality transactionality, int poolSize,
			String entityManager );
	public void registerManagedObjectType( LifecycleManager< ? > lifecycleManager, String name, Scope scope );
}
