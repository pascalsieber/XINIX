package ch.zhaw.iwi.cis.pews.model.wrappers;

public class SuspensionRequest
{
	private String suspendedElementid;
	private double elapsedSeconds;

	public SuspensionRequest()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SuspensionRequest( String suspendedElementid, double elapsedSeconds )
	{
		super();
		this.suspendedElementid = suspendedElementid;
		this.elapsedSeconds = elapsedSeconds;
	}

	public String getSuspendedElementid()
	{
		return suspendedElementid;
	}

	public void setSuspendedElementid( String suspendedElementid )
	{
		this.suspendedElementid = suspendedElementid;
	}

	public double getElapsedSeconds()
	{
		return elapsedSeconds;
	}

	public void setElapsedSeconds( double ellapsedSeconds )
	{
		this.elapsedSeconds = ellapsedSeconds;
	}

}
