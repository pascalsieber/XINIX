package ch.zhaw.iwi.cis.pews.service.impl.timed;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.impl.SessionServiceImpl;

public class SetNextExerciseJob implements Job
{
	@Override
	public void execute( JobExecutionContext context ) throws JobExecutionException
	{
		try
		{
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			SessionService sessionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
			sessionService.setNextExercise( (String)schedulerContext.get( "sessionID" ) );
		}
		catch ( SchedulerException e )
		{
			throw new RuntimeException( e );
		}
	}
}
