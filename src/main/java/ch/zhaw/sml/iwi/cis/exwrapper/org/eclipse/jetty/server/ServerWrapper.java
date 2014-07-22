package ch.zhaw.sml.iwi.cis.exwrapper.org.eclipse.jetty.server;

import org.eclipse.jetty.server.Server;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ExceptionWrapper;

public class ServerWrapper
{
	public static void start( Server server )
	{
		try
		{
			server.start();
		}
		catch ( Exception e )
		{
			throw new ExceptionWrapper( e );
		}
	}

	public static void stop( Server server )
	{
		try
		{
			server.stop();
		}
		catch ( Exception e )
		{
			throw new ExceptionWrapper( e );
		}
	}
}
