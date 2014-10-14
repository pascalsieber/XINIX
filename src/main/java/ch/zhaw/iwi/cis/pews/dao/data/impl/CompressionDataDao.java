package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionDataDao extends ExerciseDataDaoImpl
{
	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< CompressionExerciseData > data = getEntityManager().createQuery(
			"select distinct d from CompressionExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.solutions where d.workflowElement.id = '" + exerciseID + "'" ).getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( Class< ? > dataClass )
	{
		List< CompressionExerciseData > data = new ArrayList<>();

		for ( ExerciseImpl ex : UserContext.getCurrentUser().getSession().getWorkshop().getExercises() )
		{
			if ( ex.getDefinition().getClass().getSimpleName().equalsIgnoreCase( CompressionDefinition.class.getSimpleName() ) )
			{
				data.addAll( getEntityManager()
					.createQuery( "select distinct d from CompressionExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.solutions where d.workflowElement.id = '" + ex.getID() + "'" )
					.getResultList() );
			}
		}
		
		return (List< ExerciseDataImpl >)cloneResult( data );
	}
}
