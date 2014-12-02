package ch.zhaw.iwi.cis.pews.model.wrappers;

public class OffsetRequest
{
	private long offsetInMilliSeconds;
	private String workflowElementID;

	public OffsetRequest()
	{
		super();
	}

	public OffsetRequest( long offsetInMilliSeconds, String workflowElementID )
	{
		super();
		this.offsetInMilliSeconds = offsetInMilliSeconds;
		this.workflowElementID = workflowElementID;
	}

	public long getOffsetInMilliSeconds()
	{
		return offsetInMilliSeconds;
	}

	public void setOffsetInMilliSeconds( long offsetInMilliSeconds )
	{
		this.offsetInMilliSeconds = offsetInMilliSeconds;
	}

	public String getWorkflowElementID()
	{
		return workflowElementID;
	}

	public void setWorkflowElementID( String workflowElementID )
	{
		this.workflowElementID = workflowElementID;
	}
}
