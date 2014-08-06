package ch.zhaw.iwi.cis.pews.service;

public interface WorkflowElementService extends IdentifiableObjectService
{
	public void start( int id );

	public void stop( int id );
}
