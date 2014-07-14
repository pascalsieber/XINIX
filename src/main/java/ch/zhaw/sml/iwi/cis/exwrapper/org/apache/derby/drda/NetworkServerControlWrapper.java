package ch.zhaw.sml.iwi.cis.exwrapper.org.apache.derby.drda;

import java.io.PrintWriter;
import java.net.InetAddress;

import org.apache.derby.drda.NetworkServerControl;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ExceptionWrapper;

public class NetworkServerControlWrapper
{
	public static NetworkServerControl __new( InetAddress paramInetAddress, int paramInt )
	{
		try
		{
			return new NetworkServerControl( paramInetAddress, paramInt );
		}
		catch ( Exception e )
		{
			throw new ExceptionWrapper( e );
		}
	}
	
	public static void start( NetworkServerControl nsc, PrintWriter pw )
	{
		try
		{
			nsc.start( pw );
		}
		catch ( Exception e )
		{
			throw new ExceptionWrapper( e );
		}
	}
	
	public static void shutdown( NetworkServerControl nsc )
	{
		try
		{
			nsc.shutdown();
		}
		catch ( Exception e )
		{
			throw new ExceptionWrapper( e );
		}
	}
}
