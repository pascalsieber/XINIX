package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;

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
		// TODO remove ugly null setting once persistent collection fix implemented
		List< ExerciseDataImpl > data = getEntityManager().createQuery( "from ExerciseDataImpl d LEFT JOIN FETCH d.owner where d.workflowElement.id = '" + exerciseID + "'" ).getResultList();
		getEntityManager().clear();

		for ( ExerciseDataImpl element : data )
		{
			element.setWorkflowElement( null );
			element.getOwner().setSession( null );
			element.getOwner().setSessionAcceptances( null );
			element.getOwner().setSessionExecutions( null );
			element.getOwner().setSessionInvitations( null );
			((UserImpl)element.getOwner()).setGroups( null );
			

			// to avoid lazy loading exception, we set owner of xinix image which is part of
			// xinix data object to null
			if ( element instanceof XinixData )
			{
				( (XinixData)element ).getXinixImage().setOwner( null );
			}
		}

		return data;
	}

}
