package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDefinitionDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDefinitionDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopDefinitionServiceImpl extends WorkshopServiceImpl implements WorkshopDefinitionService
{
	private WorkshopDefinitionDao workshopDefinitionDao;

	public WorkshopDefinitionServiceImpl()
	{
		workshopDefinitionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDefinitionDaoImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return workshopDefinitionDao;
	}

}
