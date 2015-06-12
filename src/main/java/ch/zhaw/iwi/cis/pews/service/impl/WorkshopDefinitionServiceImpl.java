package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDefinitionDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDefinitionDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopDefinitionServiceImpl extends WorkshopObjectServiceImpl implements WorkshopDefinitionService
{
	private WorkshopDefinitionDao workshopDefinitionDao;

	public WorkshopDefinitionServiceImpl()
	{
		workshopDefinitionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDefinitionDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return workshopDefinitionDao;
	}

	@Override
	public WorkshopDefinitionImpl findWorkshopDefinitionByID( String id )
	{
		return (WorkshopDefinitionImpl)simplifyOwnerInObjectGraph( workshopDefinitionDao.findByIDWithExerciseDefinitions( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopDefinitionImpl > findAllWorkshopDefinitions()
	{
		return (List< WorkshopDefinitionImpl >)simplifyOwnerInObjectGraph( findAll() );
	}
}
