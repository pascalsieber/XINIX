package ch.zhaw.iwi.cis.pews.dao.definition.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDefinitionDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionDefinitionDao extends ExerciseDefinitionDaoImpl
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return CompressionDefinition.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > T findById( String id )
	{
		List< CompressionDefinition > definitions = getEntityManager().createQuery( "from CompressionDefinition d LEFT JOIN FETCH d.solutionCriteria where d.id = '" + id + "'" ).getResultList();
		if ( definitions.size() > 0 )
		{
			return (T)cloneResult( definitions.get( 0 ) );
		}
		else
		{
			return null;
		}
	}

}
