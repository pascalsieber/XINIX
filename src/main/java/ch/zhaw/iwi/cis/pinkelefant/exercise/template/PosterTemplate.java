package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PosterTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	@Column( length = 2000 )
	private String posterTitle;
	@Column( length = 20000 )
	private String posterDescription;

	@ElementCollection( fetch = FetchType.EAGER )
	private Set< String > posterImages;

	@ElementCollection( fetch = FetchType.EAGER )
	private Set< String > posterVideos;

	public PosterTemplate()
	{
		super();
		posterImages = new HashSet< String >();
		posterVideos = new HashSet< String >();
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
			String posterTitle,
			String posterDescription,
			Set< String > posterImages,
			Set< String > posterVideos )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate, defaultName, defaultDescription );
		this.posterTitle = posterTitle;
		this.posterDescription = posterDescription;
		this.posterImages = posterImages;
		this.posterVideos = posterVideos;
	}

	public String getPosterTitle()
	{
		return posterTitle;
	}

	public void setPosterTitle( String posterTitle )
	{
		this.posterTitle = posterTitle;
	}

	public String getPosterDescription()
	{
		return posterDescription;
	}

	public void setPosterDescription( String posterDescription )
	{
		this.posterDescription = posterDescription;
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
