package ch.zhaw.iwi.cis.pews.service;

public interface WorkflowElementService extends IdentifiableObjectService
{
	public void start( String id );

	public void stop( String id );
}
