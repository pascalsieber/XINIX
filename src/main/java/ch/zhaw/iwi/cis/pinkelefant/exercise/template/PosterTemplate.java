package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PosterTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	@Column( length = 2000 )
	private String title;
	@Column( length = 20000 )
	private String description;

	@ManyToMany
	private List< MediaObject > posterImages;

	@ManyToMany
	private List< MediaObject > posterVideos;

	public PosterTemplate()
	{
		super();
		posterImages = new ArrayList< MediaObject >();
		posterVideos = new ArrayList< MediaObject >();
	}

	public PosterTemplate(
			PrincipalImpl owner,
			boolean timed,
			TimeUnit timeUnit,
			int duration,
			boolean sharing,
			boolean skippable,
			boolean countable,
			int cardinality,
			WorkshopTemplate workshopTemplate,
			String questionTemplate,
			String defaultName,
			String defaultDescription,
			String title,
			String description,
			List< MediaObject > posterImages,
			List< MediaObject > posterVideos )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate, defaultName, defaultDescription );
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

	public List< MediaObject > getPosterImages()
	{
		return posterImages;
	}

	public void setPosterImages( List< MediaObject > posterImages )
	{
		this.posterImages = posterImages;
	}

	public List< MediaObject > getPosterVideos()
	{
		return posterVideos;
	}

	public void setPosterVideos( List< MediaObject > posterVideos )
	{
		this.posterVideos = posterVideos;
	}
}
