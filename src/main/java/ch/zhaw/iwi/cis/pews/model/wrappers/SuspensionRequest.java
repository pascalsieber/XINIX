package ch.zhaw.iwi.cis.pews.model.wrappers;

public class SuspensionRequest
{
	private int id;
	private double	ellapsedSeconds;
	
	public SuspensionRequest()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public double getEllapsedSeconds()
	{
		return ellapsedSeconds;
	}

	public void setEllapsedSeconds( double ellapsedSeconds )
	{
		this.ellapsedSeconds = ellapsedSeconds;
	}
	
}
