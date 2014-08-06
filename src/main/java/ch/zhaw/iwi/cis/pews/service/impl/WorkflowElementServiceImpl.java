package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.WorkflowElementDao;
import ch.zhaw.iwi.cis.pews.dao.WorkflowElementDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.service.WorkflowElementService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkflowElementServiceImpl extends IdentifiableObjectServiceImpl implements WorkflowElementService
{
	private WorkflowElementDao workflowElementDao;

	public WorkflowElementServiceImpl()
	{
		workflowElementDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkflowElementDaoImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return workflowElementDao;
	}

	@Override
	public void start( int id )
	{
		changeStatus( id, WorkflowElementStatusImpl.RUNNING );
	}

	@Override
	public void stop( int id )
	{
		changeStatus( id, WorkflowElementStatusImpl.TERMINATED );
	}

	private void changeStatus( int id, WorkflowElementStatusImpl newStatus )
	{
		WorkflowElementImpl workflowElement = findByID( id );
		workflowElement.setCurrentState( newStatus );
	}


}
