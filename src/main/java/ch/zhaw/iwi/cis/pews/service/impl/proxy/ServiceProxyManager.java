package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.lang.reflect.Constructor;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.reflect.ConstructorWrapper;

public class ServiceProxyManager
{
	public static final String HOST_NAME = "localhost";
	public static final String USER_NAME = ZhawEngine.ROOT_USER_LOGIN_NAME;
	public static final String PASSWORD = "root";

	private static void ensureConfigLoaded()
	{
		try
		{
			PewsConfig.getApplicationPort();
		}
		catch ( NullPointerException e )
		{
			PewsConfig.loadProperties();
		}
	}

	public static < T extends ServiceProxy > T createServiceProxy( Class< T > proxyClass )
	{
		ensureConfigLoaded();
		Constructor< ? > constructor = ClassWrapper.getDeclaredConstructor( proxyClass, String.class, int.class, String.class, String.class );
		constructor.setAccessible( true );
		T serviceProxy = ConstructorWrapper.newInstance( constructor, HOST_NAME, PewsConfig.getApplicationPort(), USER_NAME, PASSWORD );

		return serviceProxy;
	}

	public static < T extends ServiceProxy > T createServiceProxyWithUser( Class< T > proxyClass, String userName, String password )
	{
		ensureConfigLoaded();
		Constructor< ? > constructor = ClassWrapper.getDeclaredConstructor( proxyClass, String.class, int.class, String.class, String.class );
		constructor.setAccessible( true );
		T serviceProxy = ConstructorWrapper.newInstance( constructor, HOST_NAME, PewsConfig.getApplicationPort(), userName, password );

		return serviceProxy;
	}
}
