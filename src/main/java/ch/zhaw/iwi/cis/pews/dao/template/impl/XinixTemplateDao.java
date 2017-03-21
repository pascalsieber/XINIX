package ch.zhaw.iwi.cis.pews.dao.template.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixTemplateDao extends ExerciseTemplateDaoImpl
{

	@SuppressWarnings( "unchecked" )
	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		List< XinixTemplate > templates = em
			.createQuery( "from XinixTemplate t LEFT JOIN FETCH t.images i LEFT JOIN FETCH i.xinixImages where t.id = :_id" )
			.setParameter( "_id", id )
			.getResultList();
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
