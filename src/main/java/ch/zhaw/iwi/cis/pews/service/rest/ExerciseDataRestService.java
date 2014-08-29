package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;

@Path( ExerciseDataRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseDataRestService extends WorkshopObjectRestService
{
	public final static String BASE = "/exerciseService/data";
	public final static String FIND_BY_EXERCISE_ID = "/findByExerciseID";

	private ExerciseDataService exerciseDataService;

	public ExerciseDataRestService()
	{
		super();
		exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( ExerciseDataImpl obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public ExerciseDataImpl findByID( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( ExerciseDataImpl obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< ExerciseDataImpl > findAll()
	{
		return super.findAll();
	}

	@POST
	@Path( FIND_BY_EXERCISE_ID )
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return exerciseDataService.findByExerciseID( exerciseID );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseDataService;
	}

}
