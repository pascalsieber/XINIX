package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.PinkLabsExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class PinkLabsExerciseDataService extends ExerciseDataServiceImpl
{
	public PinkLabsExerciseDataService()
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
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return super.genericFindDataByID( id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< PinkLabsExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		List< PinkLabsExerciseDataViewObject > export = new ArrayList< PinkLabsExerciseDataViewObject >();

		List< ExerciseDataImpl > data = this.findByExerciseID( exercise.getID() );
		for ( ExerciseDataImpl d : data )
		{
			PinkLabsExerciseData obj = (PinkLabsExerciseData)d;
			for ( String entry : obj.getAnswers() )
			{
				export.add( new PinkLabsExerciseDataViewObject( obj.getID(), exercise.getWorkshop().getID(), exercise.getWorkshop().getName(), exercise.getID(), exercise.getName(), exercise
					.getQuestion(), obj.getOwner().getID(), ( (UserImpl)obj.getOwner() ).getLoginName(), entry ) );
			}
		}

		return export;
	}
}
