package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDefinitionDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDefinitionDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDefinitionServiceImpl extends WorkshopObjectServiceImpl implements ExerciseDefinitionService
{
	private ExerciseDefinitionDao exerciseDefinitionDao;

	public ExerciseDefinitionServiceImpl()
	{
		exerciseDefinitionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDefinitionDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseDefinitionDao;
	}
}
