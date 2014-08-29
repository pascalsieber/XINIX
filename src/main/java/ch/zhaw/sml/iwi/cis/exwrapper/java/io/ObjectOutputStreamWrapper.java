package ch.zhaw.sml.iwi.cis.exwrapper.java.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamWrapper
{
	public static ObjectOutputStream __new( OutputStream out )
	{
		try
		{
			return new ObjectOutputStream( out );
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
	
	public static void writeObject( ObjectOutputStream out, Object obj )
	{
		try
		{
			out.writeObject( obj );
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
}
