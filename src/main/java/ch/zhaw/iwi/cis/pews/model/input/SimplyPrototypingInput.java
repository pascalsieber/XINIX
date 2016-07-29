package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class SimplyPrototypingInput extends Input
{
	private String question;
	private String mimeType;

	public SimplyPrototypingInput()
	{
		super();
	}

	public SimplyPrototypingInput( ExerciseImpl exercise, String question, String mimeType )
	{
		super( exercise );
		this.question = question;
		this.mimeType = mimeType;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
	}

}
