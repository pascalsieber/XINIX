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
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< You2MeExerciseData > data = getEntityManager().createQuery(
			"from You2MeExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.dialog where d.workflowElement.id = '" + exerciseID + "'" ).getResultList();
		
		return (List< ExerciseDataImpl >)cloneResult( data );
	}
}
