package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.CachedInputDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.CachedInputDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.input.CachedInput;
import ch.zhaw.iwi.cis.pews.service.CachedInputService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CachedInputServiceImpl extends WorkshopObjectServiceImpl implements CachedInputService
{
	private CachedInputDao cachedInputDao;

	public CachedInputServiceImpl()
	{
		cachedInputDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CachedInputDaoImpl.class.getSimpleName() );
	}

	@Override
	public CachedInput findBySessionID( String sessionID )
	{
		return cachedInputDao.findBySessionID( sessionID );
	}

	@Override
	public String persistCachedInput( CachedInput cachedInput )
	{
		CachedInput original = this.findBySessionID( cachedInput.getSessionID() );
		if ( original != null )
		{
			original.setInputBlob( cachedInput.getInputBlob() );
			return persist( original );
		}
		else
		{
			return persist( cachedInput );
		}
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return cachedInputDao;
	}
}
