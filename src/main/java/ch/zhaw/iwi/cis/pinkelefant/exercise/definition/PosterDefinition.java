package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PosterDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	@Column( length = 2000 )
	private String title;
	@Column( length = 20000 )
	private String description;

	public PosterDefinition()
	{
		super();
	}

	public PosterDefinition(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopDefinitionImpl workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			String title,
			String description )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
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
