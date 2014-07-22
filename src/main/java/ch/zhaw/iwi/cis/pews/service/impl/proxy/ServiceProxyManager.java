package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.lang.reflect.Constructor;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.reflect.ConstructorWrapper;

public class ServiceProxyManager
{
	public static final String HOST_NAME = "localhost";
	public static final int PORT = 8080;
	public static final String USER_NAME = "john";
	public static final String PASSWORD = "john";

	public static < T extends ServiceProxy > T createServiceProxy( Class< T > proxyClass )
	{
		Constructor< ? > constructor = ClassWrapper.getDeclaredConstructor( proxyClass, String.class, int.class, String.class, String.class );
		constructor.setAccessible( true );
		T serviceProxy = ConstructorWrapper.newInstance( constructor, HOST_NAME, PORT, USER_NAME, PASSWORD );

		return serviceProxy;
	}
}
