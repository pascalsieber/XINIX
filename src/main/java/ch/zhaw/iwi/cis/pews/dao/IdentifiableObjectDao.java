package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

public interface IdentifiableObjectDao
{
	public < T extends IdentifiableObject > int persist( T object );
	public < T extends IdentifiableObject > void remove( T object );
	public < T extends IdentifiableObject > T merge( T object );
	public < T extends IdentifiableObject > T findById( Integer id );
	public < T extends IdentifiableObject > List< T > findByAll(Class< ? > clazz);
}
