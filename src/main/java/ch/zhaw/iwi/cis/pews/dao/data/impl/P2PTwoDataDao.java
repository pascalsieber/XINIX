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
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class P2PTwoDataDao extends ExerciseDataDaoImpl
{
	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseDataImpl findDataByID( String id )
	{
		List< P2PTwoData > results = getEntityManager()
			.createQuery( "select distinct d from P2PTwoData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.selectedKeywords where d.id = :_id" )
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
		List< P2PTwoData > data = getEntityManager()
			.createQuery( "select distinct d from P2PTwoData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.selectedKeywords where d.workflowElement.id = '" + exerciseID + "' ORDER BY d.timestamp ASC" )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		List< P2PTwoData > data = getEntityManager()
			.createQuery( "select distinct d from P2PTwoData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.selectedKeywords where d.workflowElement.id in (:ids) ORDER BY d.timestamp ASC" )
			.setParameter( "ids", exerciseIDs )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( WorkshopImpl workshop, Class< ? > dataClass )
	{
		List< P2PTwoData > data = new ArrayList<>();

		for ( ExerciseImpl ex : workshop.getExercises() )
		{
			if ( ex.getClass().getSimpleName().equalsIgnoreCase( P2PTwoExercise.class.getSimpleName() ) )
			{
				data.addAll( getEntityManager().createQuery(
					"select distinct d from P2PTwoData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.answers LEFT JOIN FETCH d.selectedKeywords where d.workflowElement.id = '" + ex.getID()
							+ "' ORDER BY d.timestamp ASC" ).getResultList() );
			}
		}

		return (List< ExerciseDataImpl >)cloneResult( data );
	}
}
