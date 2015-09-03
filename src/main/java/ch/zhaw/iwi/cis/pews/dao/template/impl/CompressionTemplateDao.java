package ch.zhaw.iwi.cis.pews.dao.template.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionTemplateDao extends ExerciseTemplateDaoImpl
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return CompressionTemplate.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > T findById( String id )
	{
		List< CompressionTemplate > templates = getEntityManager().createQuery( "from CompressionTemplate d LEFT JOIN FETCH d.solutionCriteria where d.id = '" + id + "'" ).getResultList();
		if ( templates.size() > 0 )
		{
			return (T)cloneResult( templates.get( 0 ) );
		}
		else
		{
			return null;
		}
	}

}
