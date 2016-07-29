package ch.zhaw.iwi.cis.pews.dao.exercise.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixExerciseDaoImpl extends ExerciseDaoImpl implements ExerciseDao
{

	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseImpl findExerciseByID( String id )
	{
		List< XinixExercise > results = getEntityManager()
			.createQuery( "from XinixExercise x LEFT JOIN FETCH x.images as m LEFT JOIN FETCH m.xinixImages where x.id = :_id" )
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
}
