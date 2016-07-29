package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class You2MeExerciseDataDao extends ExerciseDataDaoImpl
{
	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseDataImpl findDataByID( String id )
	{
		List< You2MeExerciseData > results = getEntityManager()
			.createQuery( "select distinct d from You2MeExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.dialog where d.id = :_id" )
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
		List< You2MeExerciseData > data = getEntityManager().createQuery(
			"select distinct d from You2MeExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.dialog where d.workflowElement.id = '" + exerciseID + "' ORDER BY d.timestamp ASC" ).getResultList();

		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		List< You2MeExerciseData > data = getEntityManager()
			.createQuery( "select distinct d from You2MeExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.dialog where d.workflowElement.id in (:ids) ORDER BY d.timestamp ASC" )
			.setParameter( "ids", exerciseIDs )
			.getResultList();

		return (List< ExerciseDataImpl >)cloneResult( data );
	}
}
