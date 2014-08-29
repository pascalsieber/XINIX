package ch.zhaw.iwi.cis.pews.model.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class ExerciseDefinitionImpl extends WorkflowElementDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private TimeUnit timeUnit;
	private int duration;

	@ManyToOne
	private WorkshopDefinitionImpl workshopDefinition;

	public ExerciseDefinitionImpl()
	{
		super();
	}

	public ExerciseDefinitionImpl( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition )
	{
		super( owner );
		this.timeUnit = timeUnit;
		this.duration = duration;
		this.workshopDefinition = workshopDefinition;
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

	public WorkshopDefinitionImpl getWorkshopDefinition()
	{
		return workshopDefinition;
	}

	public void setWorkshopDefinition( WorkshopDefinitionImpl workshopDefinition )
	{
		this.workshopDefinition = workshopDefinition;
	}

}
