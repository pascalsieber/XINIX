package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;

public abstract class IdentifiableObjectServiceImpl implements IdentifiableObjectService
{
	@Override
	public < T extends IdentifiableObject > int persist( T object )
	{
		return getIdentifiableObjectDao().persist( object );
	}

	@Override
	public < T extends IdentifiableObject > void remove( T object )
	{
		getIdentifiableObjectDao().remove( object );
	}

	@Override
	public < T extends IdentifiableObject > T findByID( int id )
	{
		return getIdentifiableObjectDao().findById( id );
	}

	@Override
	public < T extends IdentifiableObject > List< T > findAll( Class<?> clazz )
	{
		return getIdentifiableObjectDao().findByAll( clazz );
	}

	protected abstract IdentifiableObjectDao getIdentifiableObjectDao();
}
