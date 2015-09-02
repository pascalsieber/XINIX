package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pews.model.instance.XinixImageMatrix;


public class XinixInput extends Input
{
	private String question;
	private XinixImageMatrix xinixImages;

	public XinixInput()
	{
		super();
	}

	public XinixInput( String question, XinixImageMatrix xinixImages )
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
