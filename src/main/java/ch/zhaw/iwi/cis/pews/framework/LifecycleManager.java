package ch.zhaw.iwi.cis.pews.framework;

public interface LifecycleManager< T >
{
	public T create();

	public void destroy( T object );
}
