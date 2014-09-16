package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.concurrent.TimeUnit;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@Entity
public class Timer extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private TimeUnit timeUnit;
	private int value;

	@Embedded
	private WorkflowElementStatusImpl status;

	public Timer()
	{
		super();
	}

	public Timer( TimeUnit timeUnit, int value, WorkflowElementStatusImpl status )
	{
		super();
		this.timeUnit = timeUnit;
		this.value = value;
		this.status = status;
	}

	public TimeUnit getTimeUnit()
	{
		return timeUnit;
	}

	public void setTimeUnit( TimeUnit timeUnit )
	{
		this.timeUnit = timeUnit;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue( int value )
	{
		this.value = value;
	}

	public WorkflowElementStatusImpl getStatus()
	{
		return status;
	}

	public void setStatus( WorkflowElementStatusImpl status )
	{
		this.status = status;
	}

}
