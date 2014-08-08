package ch.zhaw.iwi.cis.pews.dao.impl;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDefinitionDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDefinitionDaoImpl extends WorkshopObjectDaoImpl implements ExerciseDefinitionDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return ExerciseDefinitionImpl.class;
	}


}
