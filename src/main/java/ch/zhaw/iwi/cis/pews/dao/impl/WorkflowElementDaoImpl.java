package ch.zhaw.iwi.cis.pews.dao.impl;

import ch.zhaw.iwi.cis.pews.dao.WorkflowElementDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkflowElementDaoImpl extends WorkshopObjectDaoImpl implements WorkflowElementDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return WorkflowElementImpl.class;
	}

}
