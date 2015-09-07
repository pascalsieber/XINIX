package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class PosterInput extends Input
{
	private String title;
	private String description;

	public PosterInput()
	{
		super();
	}

	public PosterInput( ExerciseImpl exercise, String title, String description )
	{
		super( exercise );
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
