package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;

@Entity
public class PosterExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	@Column( length = 2000 )
	private String title;
	@Column( length = 20000 )
	private String description;

	@ElementCollection( fetch = FetchType.EAGER )
	private Set< String > posterImages;

	@ElementCollection( fetch = FetchType.EAGER )
	private Set< String > posterVideos;

	public PosterExercise()
	{
		super();
		posterImages = new HashSet< String >();
		posterVideos = new HashSet< String >();
	}

	public PosterExercise( String name, String description, PosterTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.title = derivedFrom.getTitle();
		this.description = derivedFrom.getDescription();
		this.posterImages = derivedFrom.getPosterImages();
		this.posterVideos = derivedFrom.getPosterVideos();
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
