package ch.zhaw.sml.iwi.cis.exwrapper.org.codehaus.jackson.map;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import ch.zhaw.sml.iwi.cis.exwrapper.java.io.IOExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.org.codehaus.jackson.JsonGenerationExceptionWrapper;

public class ObjectMapperWrapper
{
	public static String writeValueAsString( ObjectMapper mapper, Object value )
	{
		try
		{
			return mapper.writeValueAsString( value );
		}
		catch ( JsonGenerationException e )
		{
			throw new JsonGenerationExceptionWrapper( e );
		}
		catch ( JsonMappingException e )
		{
			throw new JsonMappingExceptionWrapper( e );
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
}
