package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;

public abstract class WorkshopObjectServiceImpl implements WorkshopObjectService
{

	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		return getWorkshopObjectDao().persist( object );
	}

	@Override
	public < T extends WorkshopObject > void remove( T object )
	{
		getWorkshopObjectDao().remove( object );
	}

	@Override
	public < T extends WorkshopObject > T findByID( String id )
	{
		return getWorkshopObjectDao().findById( id );
	}

	@Override
	public < T extends WorkshopObject > List< T > findAll( String clientID )
	{
		return getWorkshopObjectDao().findByAll( clientID );
	}

	protected abstract WorkshopObjectDao getWorkshopObjectDao();
}
