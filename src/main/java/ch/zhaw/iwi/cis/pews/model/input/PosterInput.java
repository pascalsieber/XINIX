package ch.zhaw.iwi.cis.pews.model.input;

import java.util.HashSet;
import java.util.Set;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class PosterInput extends Input
{
	private String title;
	private String description;
	private Set< String > posterImages;
	private Set< String > posterVideos;

	public PosterInput()
	{
		super();
		posterImages = new HashSet< String >();
		posterVideos = new HashSet< String >();
	}

	public PosterInput( ExerciseImpl exercise, String title, String description, Set< String > posterImages, Set< String > posterVideos )
	{
		super( exercise );
		this.title = title;
		this.description = description;
		this.posterImages = posterImages;
		this.posterVideos = posterVideos;
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

	public Set< String > getPosterImages()
	{
		return posterImages;
	}

	public void setPosterImages( Set< String > posterImages )
	{
		this.posterImages = posterImages;
	}

	public Set< String > getPosterVideos()
	{
		return posterVideos;
	}

	public void setPosterVideos( Set< String > posterVideos )
	{
		this.posterVideos = posterVideos;
	}

}
