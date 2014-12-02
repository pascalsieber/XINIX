package ch.zhaw.iwi.cis.pews.service.impl.timed;

import java.util.TimerTask;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.impl.SessionServiceImpl;

public class SetNextExerciseTask extends TimerTask
{
	private String sessionID;
	private SessionService sessionService;

	public SetNextExerciseTask( String sessionID )
	{
		super();
		this.sessionID = sessionID;
		this.sessionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
	}

	@Override
	public void run()
	{
		sessionService.setNextExercise( sessionID );
	}

}
