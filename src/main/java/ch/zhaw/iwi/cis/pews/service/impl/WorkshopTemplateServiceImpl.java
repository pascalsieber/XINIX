package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopTemplateServiceImpl extends WorkshopObjectServiceImpl implements WorkshopTemplateService
{
	private WorkshopTemplateDao workshopTemplateDao;

	public WorkshopTemplateServiceImpl()
	{
		workshopTemplateDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopTemplateDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return workshopTemplateDao;
	}

	@Override
	public WorkshopTemplate findWorkshopTemplateByID( String id )
	{
		return (WorkshopTemplate)simplifyOwnerInObjectGraph( workshopTemplateDao.findByIDWithExerciseTemplates( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopTemplate > findAllWorkshopTemplates()
	{
		return (List< WorkshopTemplate >)simplifyOwnerInObjectGraph( findAll() );
	}
}
