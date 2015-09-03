package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class ExerciseImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private Integer orderInWorkshop = null;

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
	private WorkshopImpl workshop;

	@ManyToMany( cascade = CascadeType.ALL )
	private Set< PrincipalImpl > participants;

	// question to be answered / instructions for exercise
	private String question;

	public ExerciseImpl()
	{
		super();
		this.participants = new HashSet< PrincipalImpl >();
	}

	// constructor which takes template as argument and sets attributes accordingly
	public < T extends ExerciseTemplate > ExerciseImpl( String name, String description, T derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom );
		this.timed = derivedFrom.isTimed();
		this.timeUnit = derivedFrom.getTimeUnit();
		this.duration = derivedFrom.getDuration();
		this.sharing = derivedFrom.isSharing();
		this.skippable = derivedFrom.isSkippable();
		this.countable = derivedFrom.isCountable();
		this.cardinality = derivedFrom.getCardinality();
		this.question = derivedFrom.getQuestionTemplate();
		
		this.participants = new HashSet< PrincipalImpl >();
		this.workshop = workshop;
	}

	public Integer getOrderInWorkshop()
	{
		return orderInWorkshop;
	}

	public void setOrderInWorkshop( Integer orderInWorkshop )
	{
		this.orderInWorkshop = orderInWorkshop;
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

	public WorkshopImpl getWorkshop()
	{
		return workshop;
	}

	public void setWorkshop( WorkshopImpl workshop )
	{
		this.workshop = workshop;
	}

	public Set< PrincipalImpl > getParticipants()
	{
		return participants;
	}

	public void setParticipants( Set< PrincipalImpl > participants )
	{
		this.participants = participants;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

}
