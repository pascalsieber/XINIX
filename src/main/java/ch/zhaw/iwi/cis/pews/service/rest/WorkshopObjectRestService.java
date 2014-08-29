package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;

public abstract class WorkshopObjectRestService extends RestService
{
	public static final String PERSIST = "/persist";
	public static final String REMOVE = "/remove";
	public static final String FIND_BY_ID = "/findByID";
	public static final String FIND_ALL = "/findAll";

	private UserService userService;

	public WorkshopObjectRestService()
	{
		super();
		userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
	}

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

	public < T extends WorkshopObject > List< T > findAll()
	{
		return getWorkshopObjectService().findAll();
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService( UserService userService )
	{
		this.userService = userService;
	}

	protected abstract WorkshopObjectService getWorkshopObjectService();
}
