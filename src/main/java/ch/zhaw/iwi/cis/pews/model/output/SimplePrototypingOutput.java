package ch.zhaw.iwi.cis.pews.model.output;

public class SimplePrototypingOutput extends Output
{
	private byte[] blob;

	public SimplePrototypingOutput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SimplePrototypingOutput( byte[] blob )
	{
		super();
		this.blob = blob;
	}

	public byte[] getBlob()
	{
		return blob;
	}

	public void setBlob( byte[] blob )
	{
		this.blob = blob;
	}

}
