package ch.zhaw.iwi.cis.pews.model.wrappers;

import java.util.concurrent.TimeUnit;

public class TimerRequest
{
	private TimeUnit timeUnit;
	private int value;

	public TimerRequest()
	{

	}

	public TimerRequest( TimeUnit timeUnit, int value )
	{
		super();
		this.timeUnit = timeUnit;
		this.value = value;
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

}
