package ch.zhaw.sml.iwi.cis.exwrapper.org.eclipse.jetty.security;

import org.eclipse.jetty.security.HashLoginService;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ExceptionWrapper;

public class HashLoginServiceWrapper
{
	public static void start( HashLoginService service )
	{
		try
		{
			service.start();
		}
		catch ( Exception e )
		{
			throw new ExceptionWrapper( e );
		}
	}
}
