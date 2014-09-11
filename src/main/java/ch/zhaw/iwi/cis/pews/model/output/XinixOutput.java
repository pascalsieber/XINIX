package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;
import java.util.Set;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;

public class XinixOutput extends Output
{
	private Set< String > answers;
	private XinixImage chosenImage;

	public XinixOutput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public XinixOutput( Set< String > answers, XinixImage chosenImage )
	{
		super();
		this.answers = answers;
		this.chosenImage = chosenImage;
	}

	public Set< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( Set< String > answers )
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
