package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class PosterExerciseDataService extends ExerciseDataServiceImpl
{

	public PosterExerciseDataService()
	{
		super();
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return super.genericFindByExerciseID( exerciseID );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return super.genericFindDataByID( id );
	}

	@Override
	public List< ExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		// since Poster does not generate any exercise data, return empty array
		return new ArrayList< ExerciseDataViewObject >();
	}

}
