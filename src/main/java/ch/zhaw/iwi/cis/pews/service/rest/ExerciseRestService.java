package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;

@Path( ExerciseRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseRestService extends WorkshopObjectRestService
{

	public final static String BASE = "/exerciseService/exercise";

	public final static String START = "/start";
	public final static String STOP = "/stop";
	public final static String SUSPEND = "/suspend";
	public final static String RESUME = "/resume";

	public final static String START_USER = "/user/start";
	public final static String STOP_USER = "/user/stop";
	public final static String RESET_USER = "/user/reset";
	public final static String SUSPEND_USER = "/user/suspend";
	public final static String RESUME_USER = "/user/resume";
	public final static String CANCEL_USER = "/user/cancel";
	public final static String GET_USER_PARTICIPANT = "/user/getParticipant";

	public final static String GETINPUT = "/getInput";
	public final static String SETOUTPUT = "/setOutput";

	private ExerciseService exerciseService;

	public ExerciseRestService()
	{
		super();
		exerciseService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( ExerciseImpl obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public ExerciseImpl findByID( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( ExerciseImpl exercise )
	{
		super.remove( exercise );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< ExerciseImpl > findAll()
	{
		return super.findAll();
	}

	@POST
	@Path( START )
	public void startExercise( String exerciseID )
	{
		exerciseService.start( exerciseID );
	}

	@POST
	@Path( STOP )
	public void stopExercise( String exerciseID )
	{
		exerciseService.stop( exerciseID );
	}

	@POST
	@Path( SUSPEND )
	public void suspendExercise( SuspensionRequest suspensionRequest )
	{
		exerciseService.suspend( suspensionRequest );
	}

	@POST
	@Path( RESUME )
	public double resumeExercise( String exerciseID )
	{
		return exerciseService.resume( exerciseID );
	}

	@POST
	@Path( GETINPUT )
	public Input getExerciseInput()
	{
		return exerciseService.getInput();
	}

	@POST
	@Path( SETOUTPUT )
	public void setOutput( String output )
	{
		exerciseService.setOutput( output );
	}

	@POST
	@Path( START_USER )
	public void startUser()
	{
		exerciseService.startUser();
	}

	@POST
	@Path( STOP_USER )
	public void stopUser()
	{
		exerciseService.stopUser();
	}

	@POST
	@Path( RESET_USER )
	public void resetUser()
	{
		exerciseService.resetUser();
	}

	@POST
	@Path( SUSPEND_USER )
	public void suspendUser( TimerRequest request )
	{
		exerciseService.suspendUser( request );
	}

	@POST
	@Path( RESUME_USER )
	public TimerRequest resumeUser()
	{
		return exerciseService.resumeUser();
	}

	@POST
	@Path( CANCEL_USER )
	public void cancelUser()
	{
		exerciseService.cancelUser();
	}

	@POST
	@Path( GET_USER_PARTICIPANT )
	public Participant findUserParticipant()
	{
		return exerciseService.findUserParticipant();
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseService;
	}

}
