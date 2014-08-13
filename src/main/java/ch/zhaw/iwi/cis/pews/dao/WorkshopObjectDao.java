package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

public interface WorkshopObjectDao
{
	public < T extends WorkshopObject > String persist( T object );

	public < T extends WorkshopObject > void remove( T object );

	public < T extends WorkshopObject > T merge( T object );

	public < T extends WorkshopObject > T findById( String id );

	public < T extends WorkshopObject > List< T > findByAll( String clientID );
}
