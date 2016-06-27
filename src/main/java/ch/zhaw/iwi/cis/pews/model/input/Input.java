package ch.zhaw.iwi.cis.pews.model.input;

import java.util.concurrent.TimeUnit;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class Input
{
	private String exerciseID;
	private String exerciseType;
	private Integer exerciseOrderInWorkshop;
	private String exerciseCurrentState;

	private boolean timed;
	private TimeUnit timeUnit;
	private Integer duration;
	private boolean sharing;
	private boolean skippable;
	private boolean countable;
	private Integer cardinality;

	private String help;

	public Input()
	{
		super();
	}

	public Input( ExerciseImpl exercise )
	{
		super();
		this.exerciseID = exercise.getID();
		this.exerciseType = exercise.getClass().getSimpleName();
		this.exerciseOrderInWorkshop = exercise.getOrderInWorkshop();
		this.exerciseCurrentState = exercise.getCurrentState();
		this.timed = exercise.isTimed();
		this.timeUnit = exercise.getTimeUnit();
		this.duration = exercise.getDuration();
		this.sharing = exercise.isSharing();
		this.skippable = exercise.isSkippable();
		this.countable = exercise.isCountable();
		this.cardinality = exercise.getCardinality();
		this.help = exercise.getDescription();
	}

	public String getExerciseID()
	{
		return exerciseID;
	}

	public void setExerciseID( String exerciseID )
	{
		this.exerciseID = exerciseID;
	}

	public String getExerciseType()
	{
		return exerciseType;
	}

	public void setExerciseType( String exerciseType )
	{
		this.exerciseType = exerciseType;
	}

	public Integer getExerciseOrderInWorkshop()
	{
		return exerciseOrderInWorkshop;
	}

	public void setExerciseOrderInWorkshop( Integer exerciseOrderInWorkshop )
	{
		this.exerciseOrderInWorkshop = exerciseOrderInWorkshop;
	}

	public String getExerciseCurrentState()
	{
		return exerciseCurrentState;
	}

	public void setExerciseCurrentState( String exerciseCurrentState )
	{
		this.exerciseCurrentState = exerciseCurrentState;
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

	public Integer getDuration()
	{
		return duration;
	}

	public void setDuration( Integer duration )
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

	public Integer getCardinality()
	{
		return cardinality;
	}

	public void setCardinality( Integer cardinality )
	{
		this.cardinality = cardinality;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp( String help )
	{
		this.help = help;	
	}
}
