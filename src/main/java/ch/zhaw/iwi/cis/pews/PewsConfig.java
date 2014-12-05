package ch.zhaw.iwi.cis.pews;

public class PewsConfig
{
	public static final String PEWS_HOME = PewsConfig.class.getPackage().getName() + ".pewsHome";
	public static final String BIN = "/bin";
	public static final String CONF = "/conf";
	public static final String DB = "/db";
	public static final String LIB = "/lib";
	public static final String LOG = "/log";
	public static final String WEB = "/web";

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
//		String webBase = "/../../../src";
//		return getPewsHome() + webBase + WEB;
		
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

}
