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

	// for timing of exercises
	private boolean timed;
	private TimeUnit timeUnit;
	private int duration;

	// whether exercise data is shared among participants
	private boolean sharing;

	// whether exercise is skippable (i.e. before timer runs out)
	private boolean skippable;

	// whether exercise is finished once required number of answers are input by user
	private boolean countable;
	private int cardinality;

	@ManyToOne
	private WorkshopTemplate workshopTemplate;

	// question template (allows generation by wizard)
	private String questionTemplate;

	public ExerciseTemplate()
	{
		super();
	}

	public ExerciseTemplate(
			PrincipalImpl owner,
			boolean timed,
			TimeUnit timeUnit,
			int duration,
			boolean sharing,
			boolean skippable,
			boolean countable,
			int cardinality,
			WorkshopTemplate workshopTemplate,
			String questionTemplate )
	{
		super( owner );
		this.timed = timed;
		this.timeUnit = timeUnit;
		this.duration = duration;
		this.sharing = sharing;
		this.skippable = skippable;
		this.countable = countable;
		this.cardinality = cardinality;
		this.workshopTemplate = workshopTemplate;
		this.questionTemplate = questionTemplate;
	}

	public boolean isTimed()
	{
		return timed;
	}

	public void setTimed( boolean timed )
	{
		this.timed = timed;
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

	public boolean isCountable()
	{
		return countable;
	}

	public void setCountable( boolean countable )
	{
		this.countable = countable;
	}

	public int getCardinality()
	{
		return cardinality;
	}

	public void setCardinality( int cardinality )
	{
		this.cardinality = cardinality;
	}

	public WorkshopTemplate getWorkshopTemplate()
	{
		return workshopTemplate;
	}

	public void setWorkshopTemplate( WorkshopTemplate workshopTemplate )
	{
		this.workshopTemplate = workshopTemplate;
	}

	public String getQuestionTemplate()
	{
		return questionTemplate;
	}

	public void setQuestionTemplate( String questionTemplate )
	{
		this.questionTemplate = questionTemplate;
	}

}
