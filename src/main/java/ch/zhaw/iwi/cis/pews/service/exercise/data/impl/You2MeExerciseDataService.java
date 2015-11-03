package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.You2MeExerciseDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.You2MeExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class You2MeExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao specificDataDao;

	public You2MeExerciseDataService()
	{
		specificDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( You2MeExerciseDataDao.class.getSimpleName() );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return specificDataDao.findByExerciseID( exerciseID );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return specificDataDao.findDataByID( id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< You2MeExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		List< You2MeExerciseDataViewObject > export = new ArrayList< You2MeExerciseDataViewObject >();

		List< ExerciseDataImpl > data = this.findByExerciseID( exercise.getID() );
		for ( ExerciseDataImpl d : data )
		{
			You2MeExerciseData obj = (You2MeExerciseData)d;
			for ( DialogEntry entry : obj.getDialog() )
			{
				export.add( new You2MeExerciseDataViewObject( obj.getID(), exercise.getWorkshop().getID(), exercise.getWorkshop().getName(), exercise.getID(), exercise.getName(), exercise
					.getQuestion(), obj.getOwner().getID(), ( (UserImpl)obj.getOwner() ).getLoginName(), entry.getRole().toString(), entry.getText() ) );
			}
		}

		return export;
	}
}
