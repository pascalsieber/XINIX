package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopServiceImpl extends WorkflowElementServiceImpl implements WorkshopService
{
	private WorkshopDao workshopDao;
	
	public WorkshopServiceImpl()
	{
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
	}
	
	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return workshopDao;
	}

	@Override
	public List< WorkshopImpl > findAllWorkshopsSimple()
	{
		return workshopDao.findByAllSimple( UserContext.getCurrentUser().getClient().getID() );
	}

}
