package ch.zhaw.iwi.cis.pews.service.impl.timed;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.SessionServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetNextExerciseJob implements Job
{
	private static Logger LOG = LoggerFactory.getLogger(SetNextExerciseJob.class);

	@Override
	public void execute( JobExecutionContext context ) throws JobExecutionException
	{
		try
		{
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			UserImpl currentUser = (UserImpl)schedulerContext.get( "currentUser" );

			SessionService sessionService = ServiceProxyManager.createServiceProxyWithUser( SessionServiceProxy.class, currentUser.getLoginName(), currentUser.getCredential().getPassword() );
			sessionService.setNextExercise( (String)schedulerContext.get( "sessionID" ) );
			LOG.info( "executed setNextExercise with delay for session " + (String)schedulerContext.get( "sessionID" ) );
		}
		catch ( SchedulerException e )
		{
			throw new RuntimeException( e );
		}
	}
}
