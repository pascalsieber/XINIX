package ch.zhaw.iwi.cis.pews.framework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ManagedObjectsMap extends HashMap< String, Set< Object > >
{
	private static final long serialVersionUID = 1L;

	public void add( String name, Object object )
	{
		Set< Object > objects = get( name );

		if ( objects == null )
		{
			objects = new HashSet< Object >();
			put( name, objects );
		}

		objects.add( object );
	}
}
