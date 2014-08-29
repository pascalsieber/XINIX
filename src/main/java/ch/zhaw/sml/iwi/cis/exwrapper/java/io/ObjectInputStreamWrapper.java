package ch.zhaw.sml.iwi.cis.exwrapper.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassNotFoundExceptionWrapper;

public class ObjectInputStreamWrapper
{
	public static ObjectInputStream __new( InputStream in )
	{
		try
		{
			return new ObjectInputStream( in );
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
	
	public static Object readObject( ObjectInputStream ois )
	{
		try
		{
			return ois.readObject();
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
		catch ( ClassNotFoundException e )
		{
			throw new ClassNotFoundExceptionWrapper( e );
		}
	}
}
