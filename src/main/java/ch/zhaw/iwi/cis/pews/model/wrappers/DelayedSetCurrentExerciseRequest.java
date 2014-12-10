package ch.zhaw.iwi.cis.pews.model.wrappers;

import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

public class DelayedSetCurrentExerciseRequest
{
	private long delayInMilliSeconds;
	private SessionImpl session;

	public DelayedSetCurrentExerciseRequest()
	{
		super();
	}

	public DelayedSetCurrentExerciseRequest( long delayInMilliSeconds, SessionImpl session )
	{
		super();
		this.delayInMilliSeconds = delayInMilliSeconds;
		this.session = session;
	}

	public long getDelayInMilliSeconds()
	{
		return delayInMilliSeconds;
	}

	public void setDelayInMilliSeconds( long delayInMilliSeconds )
	{
		this.delayInMilliSeconds = delayInMilliSeconds;
	}

	public SessionImpl getSession()
	{
		return session;
	}

	public void setSession( SessionImpl session )
	{
		this.session = session;
	}

}
