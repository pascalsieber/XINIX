package ch.zhaw.sml.iwi.cis.exwrapper.java.net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class URIWrapper
{
	public static URL toURL( URI uri )
	{
		try
		{
			return uri.toURL();
		}
		catch ( MalformedURLException e )
		{
			throw new MalformedURLExceptionWrapper( e );
		}
	}
}
