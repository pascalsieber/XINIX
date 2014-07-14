package ch.zhaw.sml.iwi.cis.exwrapper.java.net;

import java.net.MalformedURLException;
import java.net.URL;

public class URLWrapper
{
	public static URL newURL( String url )
	{
		try
		{
			return new URL( url );
		}
		catch ( MalformedURLException e )
		{
			throw new MalformedURLExceptionWrapper( e );
		}
	}
}
