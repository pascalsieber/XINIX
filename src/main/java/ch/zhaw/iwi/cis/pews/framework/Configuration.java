package ch.zhaw.iwi.cis.pews.framework;

import java.io.InputStream;
import java.util.Properties;

import ch.zhaw.sml.iwi.cis.exwrapper.java.util.PropertiesWrapper;

public class Configuration
{
	private static final String APPLICATION_PROPERTIES = "application.properties";
	private static Properties properties;
	
	public static String getProperty( String key )
	{
		return getProperties().getProperty( key );
	}
	
	private static Properties getProperties()
	{
		if ( properties == null )
		{
			properties = new Properties();
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( APPLICATION_PROPERTIES );
			PropertiesWrapper.load( properties, is );
		}
		
		return properties;
	}
}
