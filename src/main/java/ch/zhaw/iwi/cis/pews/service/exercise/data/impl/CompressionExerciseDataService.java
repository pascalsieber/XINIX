package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.CompressionExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao specificDataDao;

	public CompressionExerciseDataService()
	{
		specificDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDaoImpl.class.getSimpleName() );
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return super.persist( obj );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return specificDataDao.findByExerciseID( exerciseID );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		return specificDataDao.findByExerciseIDs( exerciseIDs );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return specificDataDao.findDataByID( id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< CompressionExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		List< CompressionExerciseDataViewObject > export = new ArrayList< CompressionExerciseDataViewObject >();

		List< ExerciseDataImpl > data = this.findByExerciseID( exercise.getID() );
		for ( ExerciseDataImpl d : data )
		{
			CompressionExerciseData obj = (CompressionExerciseData)d;
			for ( CompressionExerciseDataElement entry : obj.getSolutions() )
			{
				export.add( new CompressionExerciseDataViewObject( obj.getID(), exercise.getWorkshop().getID(), exercise.getWorkshop().getName(), exercise.getID(), exercise.getName(), exercise
					.getQuestion(), obj.getOwner().getID(), ( (UserImpl)obj.getOwner() ).getLoginName(), entry.getSolution(), entry.getDescription() ) );
			}
		}

		return export;
	}

}
