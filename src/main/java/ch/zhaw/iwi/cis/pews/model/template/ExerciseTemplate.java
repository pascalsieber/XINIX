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
	private Integer orderInWorkshopTemplate = null;

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

	// default values for name and description when generating exercise instances
	private String defaultName;
	private String defaultDescription;

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
			String questionTemplate,
			String defaultName,
			String defaultDescription )
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
		this.defaultName = defaultName;
		this.defaultDescription = defaultDescription;

		// insert at end of exercise template queue of workshop template
		this.orderInWorkshopTemplate = workshopTemplate.getExerciseTemplates().size();
	}

	public Integer getOrderInWorkshopTemplate()
	{
		return orderInWorkshopTemplate;
	}

	public void setOrderInWorkshopTemplate( Integer orderInWorkshopTemplate )
	{
		this.orderInWorkshopTemplate = orderInWorkshopTemplate;
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

	public String getDefaultName()
	{
		return defaultName;
	}

	public void setDefaultName( String defaultName )
	{
		this.defaultName = defaultName;
	}

	public String getDefaultDescription()
	{
		return defaultDescription;
	}

	public void setDefaultDescription( String defaultDescription )
	{
		this.defaultDescription = defaultDescription;
	}

}
