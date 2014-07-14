package ch.zhaw.iwi.cis.pews.framework;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;

public class SimpleObjectLifecycleManager implements LifecycleManager< Object >
{
	private Class< ? > theClass;

	public SimpleObjectLifecycleManager( Class< ? > theClass )
	{
		super();
		this.theClass = theClass;
	}

	@Override
	public Object create()
	{
		return ClassWrapper.newInstance( theClass );
	}

	@Override
	public void destroy( Object object )
	{}
}
