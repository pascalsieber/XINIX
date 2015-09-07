package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class P2POneInput extends Input
{
	private String picture;
	private String question;

	public P2POneInput()
	{
		super();
	}

	public P2POneInput( ExerciseImpl exercise, String picture, String question )
	{
		super( exercise );
		this.picture = picture;
		this.question = question;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture( String picture )
	{
		this.picture = picture;
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
