package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;

import javax.inject.Inject;

public class WorkshopTemplateServiceImpl extends WorkshopObjectServiceImpl implements WorkshopTemplateService
{
	@Inject private WorkshopTemplateDao workshopTemplateDao;
	@Inject private ExerciseTemplateService exerciseTemplateService;

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

	@Override
	public void updateOrderOfExerciseTemplates( WorkshopTemplate wrapper )
	{
		for ( int i = 0; i < wrapper.getExerciseTemplates().size(); i++ )
		{
			ExerciseTemplate exerciseTemplate = exerciseTemplateService.findExerciseTemplateByID( wrapper.getExerciseTemplates().get( i ).getID() );
			exerciseTemplate.setOrderInWorkshopTemplate( i );
			exerciseTemplateService.persist( exerciseTemplate );
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopTemplate > findAllWorkshopTemplates()
	{
		return (List< WorkshopTemplate >)simplifyOwnerInObjectGraph( findAll() );
	}
}
