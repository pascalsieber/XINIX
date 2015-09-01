package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixImageMatrixTemplate;


public class XinixInput extends Input
{
	private String question;
	private XinixImageMatrixTemplate xinixImages;

	public XinixInput()
	{
		super();
	}

	public XinixInput( String question, XinixImageMatrixTemplate xinixImages )
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

	public XinixImageMatrixTemplate getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( XinixImageMatrixTemplate xinixImages )
	{
		this.xinixImages = xinixImages;
	}

	

}
