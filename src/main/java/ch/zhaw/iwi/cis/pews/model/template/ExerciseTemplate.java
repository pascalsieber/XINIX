package ch.zhaw.iwi.cis.pews.model.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class ExerciseTemplate extends WorkflowElementTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	private TimeUnit timeUnit;
	private int duration;
	private boolean timed;
	private boolean sharing;
	private boolean skippable;

	@ManyToOne
	private WorkshopTemplate workshopDefinition;

	public ExerciseTemplate()
	{
		super();
	}

	public ExerciseTemplate( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopTemplate workshopDefinition, boolean timed, boolean sharing, boolean skippable )
	{
		super( owner );
		this.timeUnit = timeUnit;
		this.duration = duration;
		this.workshopDefinition = workshopDefinition;
		this.timed = timed;
		this.sharing = sharing;
		this.skippable = skippable;
	}

	public TimeUnit getTimeUnit()
	{
		return timeUnit;
	}

	public void setTimeUnit( TimeUnit timeUnit )
	{
		this.timeUnit = timeUnit;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration( int duration )
	{
		this.duration = duration;
	}

	public WorkshopTemplate getWorkshopDefinition()
	{
		return workshopDefinition;
	}

	public void setWorkshopDefinition( WorkshopTemplate workshopDefinition )
	{
		this.workshopDefinition = workshopDefinition;
	}

	public boolean isTimed()
	{
		return timed;
	}

	public void setTimed( boolean timed )
	{
		this.timed = timed;
	}

	public boolean isSharing()
	{
		return sharing;
	}

	public void setSharing( boolean sharing )
	{
		this.sharing = sharing;
	}

	public boolean isSkippable()
	{
		return skippable;
	}

	public void setSkippable( boolean skippable )
	{
		this.skippable = skippable;
	}

}
