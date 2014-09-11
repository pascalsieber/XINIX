package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
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

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< ExerciseDataImpl > data = getEntityManager().createQuery( "from ExerciseDataImpl d LEFT JOIN FETCH d.owner where d.workflowElement.id = '" + exerciseID + "'" ).getResultList();
		getEntityManager().clear();

		return (List< ExerciseDataImpl >)cloneResult( data );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl> findByWorkshopAndExerciseDataClass( Class< ? > dataClass )
	{
		List< ExerciseDataImpl > result = new ArrayList<>();
		
		WorkshopImpl workshop = getEntityManager().find( WorkshopImpl.class, UserContext.getCurrentUser().getSession().getWorkshop() );

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
