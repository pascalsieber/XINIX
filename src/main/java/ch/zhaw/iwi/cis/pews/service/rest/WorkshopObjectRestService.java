package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;

public abstract class WorkshopObjectRestService extends RestService
{
	public static final String PERSIST = "/persist";
	public static final String REMOVE = "/remove";
	public static final String FIND_BY_ID = "/findByID";
	public static final String FIND_ALL = "/findAll";
	
	public < T extends WorkshopObject > String persist( T persistentObject )
	{
		return getWorkshopObjectService().persist( persistentObject );
	}

	public < T extends WorkshopObject > void remove( T persistentObject )
	{
		getWorkshopObjectService().remove( persistentObject );
	}

	public < T extends WorkshopObject > T findByID( String id )
	{
		return getWorkshopObjectService().findByID( id );
	}

	public < T extends WorkshopObject > List< T > findAll( String clientID )
	{
		return getWorkshopObjectService().findAll( clientID );
	}

	protected abstract WorkshopObjectService getWorkshopObjectService();
}
