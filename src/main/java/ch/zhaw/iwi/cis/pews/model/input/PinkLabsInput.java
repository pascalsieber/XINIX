package ch.zhaw.iwi.cis.pews.model.input;

public class PinkLabsInput extends Input
{
	private String question;

	public PinkLabsInput()
	{
		super();
	}

	public PinkLabsInput( String question )
	{
		super();
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
