package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedExecutionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedSetCurrentExerciseRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.PollingWrapper;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.SessionServiceImpl;

@Path( SessionRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class SessionRestService extends WorkshopObjectRestService
{

	public static final String BASE = "/workshopService/session";

	public static final String GET_CURRENT_EXERCISE_ID = "/getCurrentExerciseID";

	public final static String GET_CURRENT_EXERCISE = "/getCurrentExercise";
	public final static String SET_CURRENT_EXERCISE = "/setCurrentExercise";
	public final static String SET_CURRENT_EXERCISE_WITH_DELAY = "/setCurrentExerciseWithDelay";

	public final static String GET_NEXT_EXERCISE = "/getNextExercise";
	public final static String GET_PREVIOUS_EXERCISE = "/getPreviousExercise";

	public final static String SET_NEXT_EXERCISE = "/setNextExercise";
	public final static String SET_NEXT_EXERCISE_WITH_DELAY = "/setNextExerciseWithDelay";

	public final static String START = "/start";
	public final static String STOP = "/stop";
	public final static String RENEW = "/renew";

	public static final String JOIN = "/join";
	public static final String LEAVE = "/leave";

	public static final String ADD_EXECUTER = "/addExecuter";
	public static final String REMOVE_EXECUTER = "/removeExecuter";

	public final static String GET_CURRENT_EXERCISE_ID_WITH_OUTPUT = "/getCurrentExerciseIDWithOutput";

	private SessionService sessionService;

	public SessionRestService()
	{
		super();
		sessionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( SessionImpl obj )
	{
		return sessionService.persistSession( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public SessionImpl findByID( String id )
	{
		return sessionService.findSessionByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( SessionImpl obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< SessionImpl > findAll()
	{
		return sessionService.findAllSessions();
	}

	@POST
	@Path( GET_CURRENT_EXERCISE )
	public ExerciseImpl getCurrentExercise( String sessionID )
	{
		return sessionService.getCurrentExercise( sessionID );
	}

	@POST
	@Path( GET_CURRENT_EXERCISE_ID )
	public String getCurrentExerciseID()
	{
		// not delegating to service on purpose, in order to decrease response time since this request will be fired often (client polling)
		return UserContext.getCurrentUser().getSession().getCurrentExercise().getID();
	}

	@POST
	@Path( GET_CURRENT_EXERCISE_ID_WITH_OUTPUT )
	public PollingWrapper getCurrentExerciseIDWithOutput()
	{
		return sessionService.getCurrentExericseIDWithOutput();
	}

	@POST
	@Path( SET_CURRENT_EXERCISE )
	public void setCurrentExercise( SessionImpl request )
	{
		sessionService.setCurrentExercise( request );
	}

	@POST
	@Path( SET_CURRENT_EXERCISE_WITH_DELAY )
	public void setCurrentExerciseWithDelay( DelayedSetCurrentExerciseRequest request )
	{
		sessionService.setCurrentExerciseWithDelay( request );
	}

	@POST
	@Path( GET_NEXT_EXERCISE )
	public ExerciseImpl getNextExercise( String sessionID )
	{
		return sessionService.getNextExercise( sessionID );
	}

	@POST
	@Path( GET_PREVIOUS_EXERCISE )
	public ExerciseImpl getPreviousExercise( String sessionID )
	{
		return sessionService.getPreviousExercise( sessionID );
	}

	@POST
	@Path( SET_NEXT_EXERCISE )
	public String setNextExercise( String sessionID )
	{
		return sessionService.setNextExercise( sessionID );
	}

	@POST
	@Path( SET_NEXT_EXERCISE_WITH_DELAY )
	public String setNextExerciseWithDelay( DelayedExecutionRequest offsetRequest )
	{
		return sessionService.setNextExerciseWithDelay( offsetRequest );
	}

	@POST
	@Path( START )
	public void start( String sessionID )
	{
		sessionService.start( sessionID );
	}

	@POST
	@Path( STOP )
	public void stop( String sessionID )
	{
		sessionService.stop( sessionID );
	}

	@POST
	@Path( RENEW )
	public void renewSession( String sessionID )
	{
		sessionService.renew( sessionID );
	}

	@POST
	@Path( JOIN )
	public void join( Invitation invitation )
	{
		sessionService.join( invitation );
	}

	@POST
	@Path( LEAVE )
	public void leave( Invitation invitation )
	{
		sessionService.leave( invitation );
	}

	@POST
	@Path( ADD_EXECUTER )
	public void addExecuter( Invitation invitation )
	{
		sessionService.addExecuter( invitation );
	}

	@POST
	@Path( REMOVE_EXECUTER )
	public void removeExecuter( Invitation invitation )
	{
		sessionService.removeExecuter( invitation );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return sessionService;
	}

}
