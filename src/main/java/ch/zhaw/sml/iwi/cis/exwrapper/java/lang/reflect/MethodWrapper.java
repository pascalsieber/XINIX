package ch.zhaw.sml.iwi.cis.exwrapper.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ExceptionInInitializerErrorWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.IllegalAccessExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.IllegalArgumentExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.NullPointerExceptionWrapper;

public class MethodWrapper
{
	public static Object invoke( Method method, Object obj, Object... args )
	{
		try
		{
			return method.invoke( obj, args );
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
		catch ( NullPointerException e )
		{
			throw new NullPointerExceptionWrapper( e );
		}
		catch ( ExceptionInInitializerError e )
		{
			throw new ExceptionInInitializerErrorWrapper( e );
		}
	}
}
