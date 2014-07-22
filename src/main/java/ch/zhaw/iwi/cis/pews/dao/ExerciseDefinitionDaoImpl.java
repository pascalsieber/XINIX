package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDefinitionDaoImpl extends WorkflowElementDaoImpl implements ExerciseDefinitionDao
{

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return ExerciseDefinitionImpl.class;
	}

}
