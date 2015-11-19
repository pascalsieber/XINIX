package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
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

	@ManyToMany
	private List< MediaObject > media;

	public PosterExercise()
	{
		super();
		media = new ArrayList< MediaObject >();
	}

	public PosterExercise( String name, String description, PosterTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.title = derivedFrom.getTitle();
		this.description = derivedFrom.getDescription();
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

	public List< MediaObject > getMedia()
	{
		return media;
	}

	public void setMedia( List< MediaObject > media )
	{
		this.media = media;
	}

}
