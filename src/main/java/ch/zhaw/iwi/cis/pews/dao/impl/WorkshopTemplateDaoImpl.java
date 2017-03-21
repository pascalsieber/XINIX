package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopTemplateDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopTemplateDaoImpl extends WorkshopObjectDaoImpl implements WorkshopTemplateDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return WorkshopTemplate.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public WorkshopTemplate findByIDWithExerciseTemplates( String id )
	{
		List< WorkshopTemplate > results = em
			.createQuery( "from WorkshopTemplate d LEFT JOIN FETCH d.exerciseTemplates exDefs where d.id = '" + id + "'" )
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
