package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDataDaoImpl extends ExerciseDaoImpl implements ExerciseDataDao
{

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return ExerciseDataImpl.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( int exerciseID )
	{
		List< ExerciseDataImpl > data = getEntityManager().createQuery( "from ExerciseDataImpl d where d.workflowElement.id = " + exerciseID ).getResultList();
		getEntityManager().clear();
		
		for ( ExerciseDataImpl element : data )
		{
			element.setWorkflowElement( null );
		}
		
		return data;
	}

}
