package ch.zhaw.iwi.cis.pews.model.output;

import java.util.Set;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;

public class XinixOutput extends Output
{
	private Set< String > answers;
	private MediaObject chosenImage;

	public XinixOutput()
	{
		super();
	}

	public XinixOutput( String exerciseID, Set<String> answers, MediaObject chosenImage )
	{
		super( exerciseID );
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

	public MediaObject getChosenImage()
	{
		return chosenImage;
	}

	public void setChosenImage( MediaObject chosenImage )
	{
		this.chosenImage = chosenImage;
	}

}
