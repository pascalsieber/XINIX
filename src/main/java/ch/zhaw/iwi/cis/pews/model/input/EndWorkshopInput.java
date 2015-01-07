package ch.zhaw.iwi.cis.pews.model.input;

public class EndWorkshopInput extends Input
{
	private String title;
	private String description;

	public EndWorkshopInput()
	{
		super();
	}

	public EndWorkshopInput( String title, String description )
	{
		super();
		this.title = title;
		this.description = description;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

}
