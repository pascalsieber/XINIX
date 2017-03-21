package ch.zhaw.iwi.cis.pews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PewsConfig
{
	private final static Logger LOG = LoggerFactory.getLogger(PewsConfig.class);

	public static Properties properties;

	static {
		LOG.info( "loading properties" );
		properties = new Properties();
		try {
			properties.load( PewsConfig.class.getClassLoader().getResourceAsStream( "defaultConfig/pews.properties" ) );
		} catch (IOException e) {
		    LOG.error("Could not load properties (" + e.getMessage() + ")");
		}

		// Pretty-Print properties
		final int longestKey = properties.keySet().stream()
                .map(Object::toString) .map(String::length)
				.max(Integer::compareTo) .get();

		properties.entrySet().stream().forEach(entry -> {
		    LOG.info("{} | {}",
					String.format("%1$-" + longestKey + "s", entry.getKey()),
					entry.getValue()
			);

		});
	}

	public static String getWebDir()
	{
		return properties.getProperty( "WEB_DIR" );
	}

	public static int getApplicationPort()
	{
		return Integer.parseInt( properties.getProperty( "APPLICATION_PORT" ) );
	}

	public static String getImageDir()
	{
		return PewsConfig.class.getClassLoader().getResource("web/images").getPath();
	}

	public static String getServiceUrl()
	{
		return properties.getProperty( "EXPOSED_SERVICE_URL" );
	}

	public static String getWebClientUrl()
	{
		return properties.getProperty( "WEB_CLIENT_URL" );
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
