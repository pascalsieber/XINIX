package ch.zhaw.iwi.cis.pews.model.input;


public class XinixInput extends Input
{
	private String question;
	private XinixImageMatrix xinixImages;

	public XinixInput()
	{
		super();
	}

	public XinixInput( String question, String keyword, XinixImageMatrix xinixImages )
	{
		super();
		this.question = question;
		this.xinixImages = xinixImages;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public XinixImageMatrix getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( XinixImageMatrix xinixImages )
	{
		this.xinixImages = xinixImages;
	}

	

}
