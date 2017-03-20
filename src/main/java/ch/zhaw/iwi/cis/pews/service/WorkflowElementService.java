package ch.zhaw.iwi.cis.pews.service;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WorkflowElementService extends WorkshopObjectService
{
	public void start( String id );

	public void stop( String id );

	public void renew( String id );
}
