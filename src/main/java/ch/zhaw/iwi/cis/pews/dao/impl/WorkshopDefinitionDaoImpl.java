package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDefinitionDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopDefinitionDaoImpl extends WorkshopObjectDaoImpl implements WorkshopDefinitionDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return WorkshopDefinitionImpl.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public WorkshopDefinitionImpl findByIDWithExerciseDefinitions( String id )
	{
		List< WorkshopDefinitionImpl > results = getEntityManager()
			.createQuery( "from WorkshopDefinitionImpl d LEFT JOIN FETCH d.exerciseDefinitions exDefs where d.id = '" + id + "'" )
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
