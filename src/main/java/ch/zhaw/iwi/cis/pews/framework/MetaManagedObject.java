package ch.zhaw.iwi.cis.pews.framework;

public class MetaManagedObject
{
	private LifecycleManager< ? > lifecycleManager;
	private ManagedObject managedObjectAnnotation;
	
	public MetaManagedObject( LifecycleManager< ? > lifecycleManager, ManagedObject managedObjectAnnotation )
	{
		super();
		this.lifecycleManager = lifecycleManager;
		this.managedObjectAnnotation = managedObjectAnnotation;
	}
	
	public LifecycleManager< ? > getLifecycleManager()
	{
		return lifecycleManager;
	}
	
	public void setLifecycleManager( LifecycleManager< ? > lifecycleManager )
	{
		this.lifecycleManager = lifecycleManager;
	}
	
	public ManagedObject getManagedObjectAnnotation()
	{
		return managedObjectAnnotation;
	}
	
	public void setManagedObjectAnnotation( ManagedObject managedObjectAnnotation )
	{
		this.managedObjectAnnotation = managedObjectAnnotation;
	}
}
