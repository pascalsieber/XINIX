package ch.zhaw.iwi.cis.pews.service;

public interface WorkflowElementService extends WorkshopObjectService
{
	public void start( String id );

	public void stop( String id );

	public void renew( String id );
}
