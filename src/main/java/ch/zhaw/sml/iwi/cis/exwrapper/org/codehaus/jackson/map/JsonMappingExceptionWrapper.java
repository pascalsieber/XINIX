package ch.zhaw.sml.iwi.cis.exwrapper.org.codehaus.jackson.map;

import org.codehaus.jackson.map.JsonMappingException;

public class JsonMappingExceptionWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public JsonMappingExceptionWrapper( JsonMappingException e )
	{
		super( e );
	}
}
