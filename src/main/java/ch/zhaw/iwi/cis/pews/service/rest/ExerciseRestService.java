package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;

@Path( ExerciseRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseRestService extends WorkshopObjectRestService
{

	public final static String BASE = "/exerciseService/exercise";

	public final static String GETINPUT = "/getInput";
	public final static String GETINPUT_BY_EXERCISEID = "/getInputByExerciseID";
	public final static String SETOUTPUT = "/setOutput";
	public final static String SETOUTPUT_BY_EXERCISEID = "/setOutputByExerciseID";
	public final static String GETOUTPUT = "/getOutput";

	// only for testing
	public final static String GETINPUT_AS_STRING = "/getInputAsString";
	public final static String GETINPUT_BY_EXERCISEID_AS_STRING = "/getInputByExerciseIDAsString";

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
		return exerciseService.persistExercise( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public ExerciseImpl findByID( String id )
	{
		return exerciseService.findExerciseByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( ExerciseImpl exercise )
	{
		exerciseService.remove(exercise);
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< ExerciseImpl > findAll()
	{
		return exerciseService.findAllExercises();
	}

	@POST
	@Path( GETINPUT )
	public Input getExerciseInput()
	{
		return exerciseService.getInput();
	}

	@POST
	@Path( GETINPUT_BY_EXERCISEID )
	public Input getExerciseInputByExerciseID( String exerciseID )
	{
		return exerciseService.getInputByExerciseID( exerciseID );
	}

	@POST
	@Path( GETINPUT_AS_STRING )
	public String getExerciseInputAsString()
	{
		return exerciseService.getInputAsString();
	}

	@POST
	@Path( GETINPUT_BY_EXERCISEID_AS_STRING )
	public String getExerciseInputByExerciseIDAsString( String exerciseID )
	{
		return exerciseService.getInputByExerciseIDAsString( exerciseID );
	}

	@POST
	@Path( SETOUTPUT )
	public void setOutput( String output )
	{
		exerciseService.setOutput( output );
	}

	@POST
	@Path( SETOUTPUT_BY_EXERCISEID )
	public void setOutputByExerciseID( String outputRequestString )
	{
		exerciseService.setOuputByExerciseID( outputRequestString );
	}

	@POST
	@Path( GETOUTPUT )
	public List< ExerciseDataImpl > getOutput()
	{
		return exerciseService.getOutput();
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseService;
	}

}
