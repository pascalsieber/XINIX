package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixDataDao extends ExerciseDataDaoImpl
{

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< XinixData > data = getEntityManager()
			.createQuery( "select distinct d from XinixData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.associations where d.workflowElement.id = :_exercise_id" )
			.setParameter( "_exercise_id", exerciseID )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( Class< ? > dataClass )
	{
		List< XinixData > data = new ArrayList< XinixData >();

		for ( ExerciseImpl ex : UserContext.getCurrentUser().getSession().getWorkshop().getExercises() )
		{
			if ( ex.getDefinition().getClass().getSimpleName().equalsIgnoreCase( XinixDefinition.class.getSimpleName() ) )
			{
				data.addAll( getEntityManager()
					.createQuery( "select distinct d from XinixData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.associations where d.workflowElement.id = :_exercise_id" )
					.setParameter( "_exercise_id", ex.getID() )
					.getResultList() );
			}
		}

		return (List< ExerciseDataImpl >)cloneResult( data );
	}

}
