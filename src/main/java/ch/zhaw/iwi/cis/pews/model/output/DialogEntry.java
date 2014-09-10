package ch.zhaw.iwi.cis.pews.model.output;

public class DialogEntry
{
	private DialogRole role;
	private String text;

	public DialogEntry()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public DialogEntry( DialogRole role, String text )
	{
		super();
		this.role = role;
		this.text = text;
	}

	public DialogRole getRole()
	{
		return role;
	}

	public void setRole( DialogRole role )
	{
		this.role = role;
	}

	public String getText()
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}

}
