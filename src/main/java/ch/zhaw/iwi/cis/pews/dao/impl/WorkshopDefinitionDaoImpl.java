package ch.zhaw.iwi.cis.pews.dao.impl;

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

}
