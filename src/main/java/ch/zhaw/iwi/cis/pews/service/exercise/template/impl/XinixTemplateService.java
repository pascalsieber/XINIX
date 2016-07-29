package ch.zhaw.iwi.cis.pews.service.exercise.template.impl;

import ch.zhaw.iwi.cis.pews.dao.ExerciseTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.template.impl.XinixTemplateDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseTemplateServiceImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixTemplateService extends ExerciseTemplateServiceImpl
{
	private ExerciseTemplateDao specificExerciseTemplateDao;

	public XinixTemplateService()
	{
		specificExerciseTemplateDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixTemplateDao.class.getSimpleName() );
	}

	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		return specificExerciseTemplateDao.findExerciseTemplateByID( id );
	}

}
