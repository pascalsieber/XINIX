package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
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

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class You2MeExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao specificDataDao;

	@Inject
	public You2MeExerciseDataService()
	{
		super();
		specificDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( You2MeExerciseDataDao.class.getSimpleName() );
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return super.persist( obj );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< ExerciseDataImpl > results = specificDataDao.findByExerciseID( exerciseID );

		for ( ExerciseDataImpl result : results )
		{
			Collections.sort( ( (You2MeExerciseData)result ).getDialog(), new Comparator< DialogEntry >() {

				@Override
				public int compare( DialogEntry o1, DialogEntry o2 )
				{
					return compareHandlingNullValues(o1.getOrderInDialog(), o2.getOrderInDialog());
				}

				// compare function which handles null values
				private < T extends Comparable< T >> int compareHandlingNullValues( T a, T b )
				{
					return a == null ? ( b == null ? 0 : Integer.MIN_VALUE ) : ( b == null ? Integer.MAX_VALUE : a.compareTo( b ) );
				}
			} );
		}

		return results;
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		List< ExerciseDataImpl > results = specificDataDao.findByExerciseIDs( exerciseIDs );

		for ( ExerciseDataImpl result : results )
		{
			Collections.sort( ( (You2MeExerciseData)result ).getDialog(), new Comparator< DialogEntry >() {
				
				@Override
				public int compare( DialogEntry o1, DialogEntry o2 )
				{
					return compareHandlingNullValues(o1.getOrderInDialog(), o2.getOrderInDialog());
				}

				// compare function which handles null values
				private < T extends Comparable< T >> int compareHandlingNullValues( T a, T b )
				{
					return a == null ? ( b == null ? 0 : Integer.MIN_VALUE ) : ( b == null ? Integer.MAX_VALUE : a.compareTo( b ) );
				}
			} );
		}

		return results;
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
