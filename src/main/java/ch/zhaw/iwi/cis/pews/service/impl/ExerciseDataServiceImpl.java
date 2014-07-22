package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDataServiceImpl extends ExerciseServiceImpl implements ExerciseDataService
{
	private ExerciseDataDao exerciseDataDao;

	public ExerciseDataServiceImpl()
	{
		exerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataDaoImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return exerciseDataDao;
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( int exerciseID )
	{
		return exerciseDataDao.findByExerciseID(exerciseID);
	}

}
