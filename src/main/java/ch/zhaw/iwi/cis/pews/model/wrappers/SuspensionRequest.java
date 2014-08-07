package ch.zhaw.iwi.cis.pews.model.wrappers;

public class SuspensionRequest
{
	private String id;
	private double elapsedSeconds;

	public SuspensionRequest()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
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
