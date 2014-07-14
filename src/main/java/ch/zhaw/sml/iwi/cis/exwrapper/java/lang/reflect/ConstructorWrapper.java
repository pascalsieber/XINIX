package ch.zhaw.sml.iwi.cis.exwrapper.java.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.IllegalAccessExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.IllegalArgumentExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.InstantiationExceptionWrapper;

public class ConstructorWrapper
{
	@SuppressWarnings( "unchecked" )
	public static < T > T newInstance( Constructor< ? > constructor, Object... initargs )
	{
		try
		{
			return (T)constructor.newInstance( initargs );
		}
		catch ( InstantiationException e )
		{
			throw new InstantiationExceptionWrapper( e );
		}
		catch ( IllegalAccessException e )
		{
			throw new IllegalAccessExceptionWrapper( e );
		}
		catch ( IllegalArgumentException e )
		{
			throw new IllegalArgumentExceptionWrapper( e );
		}
		catch ( InvocationTargetException e )
		{
			throw new InvocationTargetExceptionWrapper( e );
		}
	}
}
