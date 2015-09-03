package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseTemplateServiceImpl extends WorkshopObjectServiceImpl implements ExerciseTemplateService
{
	private ExerciseTemplateDao exerciseTemplateDao;
	private WorkshopTemplateDao workshopTemplateDao;

	public ExerciseTemplateServiceImpl()
	{
		exerciseTemplateDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseTemplateDaoImpl.class.getSimpleName() );
		workshopTemplateDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopTemplateDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseTemplateDao;
	}

	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		return (ExerciseTemplate)simplifyOwnerInObjectGraph( findByID( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseTemplate > findAllExerciseTemplates()
	{
		return (List< ExerciseTemplate >)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override
	public void removeExerciseTemplate( ExerciseTemplate obj )
	{
		ExerciseTemplate exTemplate = exerciseTemplateDao.findById( obj.getID() );
		WorkshopTemplate wsDef = workshopTemplateDao.findById( exTemplate.getWorkshopTemplate().getID() );

		wsDef.getExerciseTemplates().remove( exTemplate );

		workshopTemplateDao.persist( wsDef );
		exerciseTemplateDao.remove( exTemplate );

	}
}
