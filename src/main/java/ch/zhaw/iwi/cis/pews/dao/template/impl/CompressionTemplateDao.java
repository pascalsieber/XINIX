package ch.zhaw.iwi.cis.pews.dao.template.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionTemplateDao extends ExerciseTemplateDaoImpl
{

	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		List< CompressionTemplate > templates = em.createQuery( "from CompressionTemplate t LEFT JOIN FETCH t.solutionCriteria where t.id = '" + id + "'" ).getResultList();
		if ( templates.size() > 0 )
		{
			return (ExerciseTemplate)cloneResult( templates.get( 0 ) );
		}
		else
		{
			return null;
		}
	}

}
