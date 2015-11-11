package ch.zhaw.iwi.cis.pews.service.exercise.template.impl;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseTemplateServiceImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class EvaluationTemplateService extends ExerciseTemplateServiceImpl
{

	public EvaluationTemplateService()
	{
	}

	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		return super.genericFindExerciseTemplateByID( id );
	}

}
