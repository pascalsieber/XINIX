package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.CompressableExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.CompressableExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.service.CompressableExerciseDataService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressableExerciseDataServiceImpl extends WorkshopObjectServiceImpl implements CompressableExerciseDataService
{
	private CompressableExerciseDataDao compressableExerciseDataDao;
	
	public CompressableExerciseDataServiceImpl()
	{
		compressableExerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressableExerciseDataDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return compressableExerciseDataDao;
	}

}
