package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.You2MeExerciseDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class You2MeExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao dataDao;
	
	public You2MeExerciseDataService()
	{
		dataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( You2MeExerciseDataDao.class.getSimpleName() );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return dataDao.findByExerciseID( exerciseID );
	}
}
