package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public final static String REMOVE_BY_ID = "/removeByID";
	public final static String EXPORT_BY_EXERCISE_ID = "/exportByExerciseID/{exerciseID}";
	public final static String EXPORT_BY_WORKSHOP_ID = "/exportByWorkshopID/{workshopID}";

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
		return exerciseDataService.persistExerciseData( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public ExerciseDataImpl findByID( String id )
	{
		return exerciseDataService.findExerciseDataByID( id );
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
		return exerciseDataService.findAllExerciseData();
	}

	@POST
	@Path( FIND_BY_EXERCISE_ID )
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return exerciseDataService.findByExerciseID( exerciseID );
	}

	@POST
	@Path( REMOVE_BY_ID )
	public void removeExerciseDataByID( String id )
	{
		exerciseDataService.removeExerciseDataByID( id );
	}

	@GET
	@Path( EXPORT_BY_EXERCISE_ID )
	@Produces("application/vnd.ms-excel")
	public Response exportDataByExerciseID( @PathParam("exerciseID") String exerciseID )

	{
		byte[] file = exerciseDataService.exportByExerciseID( exerciseID );

		Response.ResponseBuilder response = Response.ok((Object)file);
		response.header("Content-Disposition",
				"attachment; filename=export.xlsx");
		return response.build();
	}

	@GET
	@Path( EXPORT_BY_WORKSHOP_ID )
	@Produces("application/vnd.ms-excel")
	public Response exportDataByWorkshopID( @PathParam("workshopID") String workshopID )
	{
		byte[] file = exerciseDataService.exportByWorkshopID( workshopID );

		Response.ResponseBuilder response = Response.ok((Object)file);
		response.header("Content-Disposition",
				"attachment; filename=export.xlsx");
		return response.build();
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseDataService;
	}

}
