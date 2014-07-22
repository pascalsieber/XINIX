package ch.zhaw.sml.iwi.cis.exwrapper.org.codehaus.jackson;

import org.codehaus.jackson.JsonGenerationException;

public class JsonGenerationExceptionWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public JsonGenerationExceptionWrapper( JsonGenerationException e )
	{
		super( e );
	}
}
