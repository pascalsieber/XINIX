package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixImageDataService extends ExerciseDataServiceImpl
{

	@Inject
	public XinixImageDataService()
	{
		super();
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return super.persist( obj );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return super.genericFindByExerciseID( exerciseID );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		return super.genericFindByExerciseIDs( exerciseIDs );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return super.genericFindDataByID( id );
	}

	@Override
	public List< ExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		// not supported
		return super.getExportableDataByExerciseID( exercise );
	}

}
