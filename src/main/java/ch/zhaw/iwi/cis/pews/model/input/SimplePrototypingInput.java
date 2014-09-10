package ch.zhaw.iwi.cis.pews.model.input;

public class SimplePrototypingInput extends Input
{
	private String question;
	private String mimeType;

	public SimplePrototypingInput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SimplePrototypingInput( String question, String mimeType )
	{
		super();
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
