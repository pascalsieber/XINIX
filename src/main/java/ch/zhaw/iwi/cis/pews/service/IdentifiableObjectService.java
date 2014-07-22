package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

public interface IdentifiableObjectService extends Service
{
	public < T extends IdentifiableObject > int persist( T object );
	public < T extends IdentifiableObject > void remove( T object );
	public < T extends IdentifiableObject > T findByID( int id );
	public < T extends IdentifiableObject> List< T > findAll( Class< ? > clazz );
}
