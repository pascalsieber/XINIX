package ch.zhaw.sml.iwi.cis.pews.test.util;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fueg on 06.09.2016.
 */
public class TestUtil
{
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static List<String> extractIds( List<? extends IdentifiableObject> identifiableObjects )
	{
		List<String> ids = new ArrayList<>();
		List<IdentifiableObject> objects = new ArrayList<>();

		try
		{
			objects = objectMapper.readValue(
					objectMapper.writeValueAsString( identifiableObjects ),
					TypeFactory.defaultInstance()
							.constructCollectionType( ArrayList.class, IdentifiableObject.class ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		for ( IdentifiableObject identifiableObject : objects )
		{
			ids.add( identifiableObject.getID() );
		}
		return ids;
	}
}
