package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;

public class XinixOutput extends Output
{
	private List< String > answers;
	private XinixImage chosenImage;

	public XinixOutput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public XinixOutput( List< String > answers, XinixImage chosenImage )
	{
		super();
		this.answers = answers;
		this.chosenImage = chosenImage;
	}

	public List< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( List< String > answers )
	{
		this.answers = answers;
	}

	public XinixImage getChosenImage()
	{
		return chosenImage;
	}

	public void setChosenImage( XinixImage chosenImage )
	{
		this.chosenImage = chosenImage;
	}

}
