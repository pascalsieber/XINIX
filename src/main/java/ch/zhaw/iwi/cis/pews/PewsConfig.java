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
	public static final String WEB = "/web";
	public static Properties properties;

	public static void loadProperties()
	{
		System.out.println( "loading properties" );
		properties = new Properties();

		try
		{
			properties.load( PewsConfig.class.getClassLoader().getResourceAsStream( "defaultConfig/pews.properties" ) );
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

	public static int getApplicationPort()
	{
		return Integer.parseInt( properties.getProperty( "APPLICATION_PORT" ) );
	}

	public static String getWebDirURL()
	{
		return "http://" + properties.getProperty( "APPLICATION_HOST" ) + ":" + properties.getProperty( "APPLICATION_PORT" );
	}

	public static String getImageDir()
	{
		return properties.getProperty( "IMAGE_DIR" );
	}

	public static String getExportDir()
	{
		return properties.getProperty( "EXPORT_DIR" );
	}

	public static String getServiceUrl()
	{
		return properties.getProperty( "EXPOSED_SERVICE_URL" );
	}

	public static String getWebClientAuthenticationUrl()
	{
		return properties.getProperty( "WEB_CLIENT_URL" ) + properties.getProperty( "WEB_CLIENT_AUTH" );
	}

	public static String getWebClientAuthenticationUserParam()
	{
		return properties.getProperty( "WEB_CLIENT_AUTH_USER" );
	}

	public static String getWebClientAuthenticationPasswordParam()
	{
		return properties.getProperty( "WEB_CLIENT_AUTH_PW" );
	}

	public static String getWebClientAuthenticationProfileTarget()
	{
		return properties.getProperty( "WEB_CLIENT_AUTH_TARGET_PROFILE" );
	}

	public static String getWebClientAuthenticationInvitationTarget()
	{
		return properties.getProperty( "WEB_CLIENT_AUTH_TARGET_INIVITATION" );
	}

	public static String getWebClientAuthenticationInvitationParam()
	{
		return properties.getProperty( "WEB_CLIENT_AUTH_SESSIONID" );
	}

	public static String getMailHost()
	{
		return properties.getProperty( "MAIL_SMTP_HOST" );
	}

	public static String getMailSocketFactoryPort()
	{
		return properties.getProperty( "MAIL_SMTP_SOCKETFACTORY_PORT" );
	}

	public static String getMailSocketFactoryClass()
	{
		return properties.getProperty( "MAIL_SMTP_SOCKETFACTORY_CLASS" );
	}

	public static String getMailPort()
	{
		return properties.getProperty( "MAIL_SMTP_PORT" );
	}

	public static String getMailUserName()
	{
		return properties.getProperty( "MAIL_ACCOUNT_USER" );
	}

	public static String getMailPassword()
	{
		return properties.getProperty( "MAIL_ACCOUNT_PASSWORD" );
	}

	public static String getMailSubjectForInvitation()
	{
		return properties.getProperty( "MAIL_INVITATION_SUBJECT" );
	}

	public static String getMailSubjectForProfile()
	{
		return properties.getProperty( "MAIL_PROFILE_SUBJECT" );
	}

	public static String getMailWebClientInfo()
	{
		return properties.getProperty( "MAIL_WEB_CLIENT_INTRO" );
	}

	public static String getMailTextProfile()
	{
		return properties.getProperty( "MAIL_TEXT_PROFILE" );
	}

	public static String getUrlHelp()
	{
		return properties.getProperty( "URL_HELP_TEXT" );
	}
}
