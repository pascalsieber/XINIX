package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.WorkflowElementDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDataDaoImpl extends WorkshopObjectDaoImpl implements ExerciseDataDao
{
	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return ExerciseDataImpl.class;
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseDataImpl findDataByID( String id )
	{
		List< ExerciseDataImpl > results = getEntityManager().createQuery( "from ExerciseDataImpl d LEFT JOIN FETCH d.owner where d.id = :_id" ).setParameter( "_id", id ).getResultList();

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
		List< ExerciseDataImpl > data = getEntityManager()
			.createQuery( "from ExerciseDataImpl d LEFT JOIN FETCH d.owner where d.workflowElement.id = '" + exerciseID + "' ORDER BY d.timestamp ASC" )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		List< ExerciseDataImpl > data = getEntityManager()
			.createQuery( "from ExerciseDataImpl d LEFT JOIN FETCH d.owner where d.workflowElement.id in (:ids) ORDER BY d.timestamp ASC" )
			.setParameter( "ids", exerciseIDs )
			.getResultList();
		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( WorkshopImpl workshop, Class< ? > dataClass )
	{
		List< ExerciseDataImpl > result = new ArrayList<>();

		for ( ExerciseImpl ex : workshop.getExercises() )
		{
			for ( WorkflowElementDataImpl data : ex.getData() )
			{
				if ( data.getClass().getSimpleName().equalsIgnoreCase( dataClass.getSimpleName() ) )
				{
					result.add( (ExerciseDataImpl)data );
				}
			}
		}

		getEntityManager().clear();

		return (List< ExerciseDataImpl >)cloneResult( result );
	}
}
