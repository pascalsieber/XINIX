package ch.zhaw.iwi.cis.pews.framework;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

public class CustomObjectIDResolver implements ObjectIdResolver
{
	private Map< IdKey, Object > _items = new HashMap< ObjectIdGenerator.IdKey, Object >();

	@Override
	public void bindItem( IdKey id, Object pojo )
	{
		_items.put( id, pojo );
	}

	@Override
	public Object resolveId( IdKey id )
	{
		return _items.get( id );
	}

	@Override
	public ObjectIdResolver newForDeserialization( Object context )
	{
		return this;
	}

	@Override
	public boolean canUseFor( ObjectIdResolver resolverType )
	{
		return resolverType.getClass() == getClass();
	}

}
