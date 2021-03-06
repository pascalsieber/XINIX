package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;

public abstract class IdentifiableObjectRestService extends RestService
{
	public static final String PERSIST = "/persist";
	public static final String REMOVE = "/remove";
	public static final String FIND_BY_ID = "/findByID";
	public static final String FIND_ALL = "/findAll";

	public < T extends IdentifiableObject > String persist( T persistentObject )
	{
		return getPersistentObjectService().persist( persistentObject );
	}

	public < T extends IdentifiableObject > void remove( T persistentObject )
	{
		getPersistentObjectService().remove( persistentObject );
	}

	public < T extends IdentifiableObject > T findByID( String id )
	{
		return getPersistentObjectService().findByID( id );
	}

	public < T extends IdentifiableObject > List< T > findAll()
	{
		return getPersistentObjectService().findAll();
	}

	protected abstract IdentifiableObjectService getPersistentObjectService();
}
