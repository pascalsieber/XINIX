package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixDataDao extends ExerciseDataDaoImpl
{

	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseDataImpl findDataByID( String id )
	{
		List< XinixData > results = getEntityManager()
			.createQuery( "select distinct d from XinixData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.associations where d.id = :_id" )
			.setParameter( "_id", id )
			.getResultList();

		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			return null;
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< XinixData > data = getEntityManager()
			.createQuery( "select distinct d from XinixData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.associations where d.workflowElement.id = :_exercise_id ORDER BY d.timestamp ASC" )
			.setParameter( "_exercise_id", exerciseID )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		List< XinixData > data = getEntityManager()
			.createQuery( "select distinct d from XinixData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.associations where d.workflowElement.id in (:ids) ORDER BY d.timestamp ASC" )
			.setParameter( "ids", exerciseIDs )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( WorkshopImpl workshop, Class< ? > dataClass )
	{
		List< XinixData > data = new ArrayList< XinixData >();

		for ( ExerciseImpl ex : workshop.getExercises() )
		{
			if ( ex.getClass().getSimpleName().equalsIgnoreCase( XinixExercise.class.getSimpleName() ) )
			{
				data.addAll( getEntityManager()
					.createQuery( "select distinct d from XinixData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.associations where d.workflowElement.id = :_exercise_id ORDER BY d.timestamp ASC" )
					.setParameter( "_exercise_id", ex.getID() )
					.getResultList() );
			}
		}

		return (List< ExerciseDataImpl >)cloneResult( data );
	}

}
