package ch.zhaw.iwi.cis.pews.dao.impl;

import ch.zhaw.iwi.cis.pews.dao.ExerciseTemplateDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseTemplateDaoImpl extends WorkshopObjectDaoImpl implements ExerciseTemplateDao
{

	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		// use default implementation. for specific behavior, this method is overwritten by corresponding subclass
		return findById( id );
	}

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return ExerciseTemplate.class;
	}

}
