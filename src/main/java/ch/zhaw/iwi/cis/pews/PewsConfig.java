package ch.zhaw.iwi.cis.pews;

import java.io.IOException;
import java.util.Properties;

public class PewsConfig
{
	public static final String PEWS_HOME = PewsConfig.class.getPackage().getName() + ".pewsHome";
	public static final String BIN = "/bin";
	public static final String CONF = "/conf";
	public static final String DB = "/db";
	public static final String LIB = "/lib";
	public static final String LOG = "/log";
	public static final String WEB = "/web";
	public static Properties properties;

	public static void loadProperties()
	{
		System.out.println( "loading properties" );
		properties = new Properties();

		try
		{
			properties.load( PewsConfig.class.getClassLoader().getResourceAsStream( "META-INF/pews.properties" ) );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( e );
		}

	}

	public static String getPewsHome()
	{
		return System.getProperty( PEWS_HOME );
	}

	public static String getBinDir()
	{
		return getPewsHome() + BIN;
	}

	public static String getConfDir()
	{
		return getPewsHome() + CONF;
	}

	public static String getDbDir()
	{
		return getPewsHome() + DB;
	}

	public static String getWebDir()
	{
		// TODO Make webBase configuration dependent: alternative is ""
		// String webBase = "/../../../src";
		// return getPewsHome() + webBase + WEB;

		return getPewsHome() + WEB;
	}

	public static String getLibDir()
	{
		return getPewsHome() + LIB;
	}

	public static String getLogDir()
	{
		return getPewsHome() + LOG;
	}

	public static int getApplicationPort()
	{
		return Integer.parseInt( properties.getProperty( "APPLICATION_PORT" ) );
	}

	public static int getDerbyPort()
	{
		return Integer.parseInt( properties.getProperty( "DERBY_PORT" ) );
	}

	public static String getWebDirURL()
	{
		return "http://" + properties.getProperty( "APPLICATION_HOST" ) + ":" + properties.getProperty( "APPLICATION_PORT" );
	}

	public static String getMediaDirURL()
	{
		return "http://" + properties.getProperty( "APPLICATION_HOST" ) + ":" + properties.getProperty( "APPLICATION_PORT" ) + "/" + properties.getProperty( "MEDIA_DIR" );
	}

	public static String getImageDir()
	{
		return properties.getProperty( "IMAGE_DIR" );
	}

	public static String getMediaDir()
	{
		return properties.getProperty( "MEDIA_DIR" );
	}

}
