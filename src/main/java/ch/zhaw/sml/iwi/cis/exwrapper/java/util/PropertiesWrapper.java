package ch.zhaw.sml.iwi.cis.exwrapper.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ch.zhaw.sml.iwi.cis.exwrapper.java.io.IOExceptionWrapper;

public class PropertiesWrapper
{
	public static void load( Properties properties, InputStream inputStream ) throws IOExceptionWrapper
	{
		try
		{
			properties.load( inputStream );
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
}
