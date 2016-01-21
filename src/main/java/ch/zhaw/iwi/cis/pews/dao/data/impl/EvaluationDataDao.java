package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class EvaluationDataDao extends ExerciseDataDaoImpl
{
	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseDataImpl findDataByID( String id )
	{
		List< EvaluationExerciseData > results = getEntityManager()
			.createQuery( "select distinct d from EvaluationExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.evaluation where d.id = :_id" )
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
		List< EvaluationExerciseData > data = getEntityManager()
			.createQuery(
				"select distinct d from EvaluationExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.evaluation where d.workflowElement.id = '" + exerciseID + "' ORDER BY d.timestamp ASC" )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		List< EvaluationExerciseData > data = getEntityManager()
			.createQuery( "select distinct d from EvaluationExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.evaluation where d.workflowElement.id in (:ids) ORDER BY d.timestamp ASC" )
			.setParameter( "ids", exerciseIDs )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( WorkshopImpl workshop, Class< ? > dataClass )
	{
		List< EvaluationExerciseData > data = new ArrayList<>();

		// check for null pointer on UserContext.getCurrentUser().getSession()
		// this is the case at startup of ZhawEngine -> we simply return an empty list since no evaluationExerciseData present at startup of ZhawEngine
		// TODO: might want to find better way to catch this error

		if ( null == UserContext.getCurrentUser().getSession() )
		{
			return new ArrayList< ExerciseDataImpl >();
		}

		for ( ExerciseImpl ex : workshop.getExercises() )
		{
			if ( ex.getClass().getSimpleName().equalsIgnoreCase( EvaluationExercise.class.getSimpleName() ) )
			{
				data.addAll( getEntityManager()
					.createQuery(
						"select distinct d from EvaluationExerciseData d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.evaluation where d.workflowElement.id = '" + ex.getID()
								+ "' ORDER BY d.timestamp ASC" )
					.getResultList() );
			}
		}

		// TODO: using somewhat of a hack / not so nice solution. using this because query returns all results twice (due to relation product)
		HashSet< ExerciseDataImpl > dataSet = new HashSet< ExerciseDataImpl >( (List< ExerciseDataImpl >)cloneResult( data ) );
		List< ExerciseDataImpl > result = new ArrayList< ExerciseDataImpl >();
		result.addAll( dataSet );
		return result;
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl object )
	{
		// evaluations by a specific user for an existing solution need to be updated (overwritten) rather than added
		List< ExerciseDataImpl > existing = this.findByWorkshopAndExerciseDataClass( UserContext.getCurrentUser().getSession().getWorkshop(), Evaluation.class );

		for ( ExerciseDataImpl data : existing )
		{
			if ( data.getOwner().getID().equals( ( (OwnableObject)object ).getOwner().getID() )
					&& ( (EvaluationExerciseData)data ).getEvaluation().getSolution().getID().equals( ( (EvaluationExerciseData)object ).getEvaluation().getSolution().getID() ) )
			{

				( (EvaluationExerciseData)data ).getEvaluation().getScore().setScore( ( (EvaluationExerciseData)object ).getEvaluation().getScore().getScore() );
				return super.persist( data );
			}
		}

		return super.persist( object );
	}
}
