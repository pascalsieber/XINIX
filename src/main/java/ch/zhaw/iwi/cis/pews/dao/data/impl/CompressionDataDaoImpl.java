package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.CompressionDataDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionDataDaoImpl extends ExerciseDataDaoImpl implements CompressionDataDao
{

	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseDataImpl findDataByID( String id )
	{
		List< CompressionExerciseData > results = getEntityManager()
			.createQuery( "select distinct d from CompressionExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.solutions where d.id = :_id" )
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
		List< CompressionExerciseData > data = getEntityManager()
			.createQuery(
				"select distinct d from CompressionExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.solutions where d.workflowElement.id = '" + exerciseID + "' ORDER BY d.timestamp ASC" )
			.getResultList();
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
					.createQuery(
						"select distinct d from CompressionExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.solutions where d.workflowElement.id = '" + ex.getID()
								+ "' ORDER BY d.timestamp ASC" )
					.getResultList() );
			}
		}

		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public CompressionExerciseDataElement findBySolutionAndDescription( String solution, String description )
	{
		List< CompressionExerciseDataElement > results = getEntityManager()
			.createQuery( "from CompressionExerciseDataElement c where LOWER(c.solution) = LOWER(:_solution) AND LOWER(c.description) = LOWER(:_description)" )
			.setParameter( "_solution", solution )
			.setParameter( "_description", description )
			.getResultList();

		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			throw new RuntimeException( "CompressionExerciseDataElement with solution = " + solution + " and description = " + description + " could not be found!" );
		}
	}
}
