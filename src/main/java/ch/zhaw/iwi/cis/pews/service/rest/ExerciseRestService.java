package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;

@Path( ExerciseRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseRestService extends IdentifiableObjectRestService
{

	public final static String BASE = "/exerciseService/exercise";
	public final static String START = "/start";
	public final static String STOP = "/stop";

	private ExerciseService exerciseService;

	public ExerciseRestService()
	{
		exerciseService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public int persistExercise( ExerciseImpl obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public ExerciseImpl findExerciseById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeExercise( ExerciseImpl exercise )
	{
		super.remove( exercise );
	}

	@POST
	@Path( FIND_ALL )
	public List< ExerciseImpl > findAllExercises()
	{
		return super.findAll( ExerciseImpl.class.getSimpleName() );
	}

	@POST
	@Path( START )
	public void statExercise( ExerciseImpl exercise )
	{
		exerciseService.start( exercise.getID() );
	}

	@POST
	@Path( STOP )
	public void stopExercise( ExerciseImpl exercise )
	{
		exerciseService.stop( exercise.getID() );
	}

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return exerciseService;
	}

}
