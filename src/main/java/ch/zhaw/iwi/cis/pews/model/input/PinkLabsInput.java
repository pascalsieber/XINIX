package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class PinkLabsInput extends Input
{
	private String question;

	public PinkLabsInput()
	{
		super();
	}

	public PinkLabsInput( ExerciseImpl exercise, String question )
	{
		super( exercise );
		this.question = question;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

}
