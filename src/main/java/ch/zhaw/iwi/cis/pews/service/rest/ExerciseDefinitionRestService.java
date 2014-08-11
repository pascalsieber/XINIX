package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDefinitionServiceImpl;

@Path( ExerciseDefinitionRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseDefinitionRestService extends WorkshopObjectRestService
{

	public final static String BASE = "/exerciseService/definition";

	private ExerciseDefinitionService exerciseDefinitionService;

	public ExerciseDefinitionRestService()
	{
		super();
		exerciseDefinitionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDefinitionServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persistExerciseDefinition( ExerciseDefinitionImpl obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public ExerciseDefinitionImpl findExerciseDefinitionById( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeExerciseDefinition( ExerciseDefinitionImpl obj )
	{
		super.remove( obj );
	}

	@POST
	@Path( FIND_ALL )
	public List< ExerciseDefinitionImpl > findAll( @Context HttpServletRequest request )
	{
		return super.findAll( getUserService().getClientFromAuth( request ) );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseDefinitionService;
	}

}
