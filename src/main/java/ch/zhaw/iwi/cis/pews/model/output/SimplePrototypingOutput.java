package ch.zhaw.iwi.cis.pews.model.output;

public class SimplePrototypingOutput extends Output
{
	private String base64String;

	public SimplePrototypingOutput()
	{
		super();
	}

	public SimplePrototypingOutput( String exerciseID, String base64String )
	{
		super( exerciseID );
		this.base64String = base64String;
	}

	public String getBase64String()
	{
		return base64String;
	}

	public void setBase64String( String base64String )
	{
		this.base64String = base64String;
	}

}
