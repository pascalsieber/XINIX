package ch.zhaw.sml.iwi.cis.exwrapper.java.io;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWrapper
{
	public static void flush( OutputStream out )
	{
		try
		{
			out.flush();
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
}
