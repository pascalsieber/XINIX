package ch.zhaw.iwi.cis.pews.model.input;

public class P2POneInput extends Input
{
	private String picture;
	private String question;

	public P2POneInput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public P2POneInput( String picture, String question )
	{
		super();
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
