package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
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
	public String persist( ExerciseTemplate obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public ExerciseTemplate findByID( String id )
	{
		return exerciseDefinitionService.findExerciseDefinitionByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( ExerciseTemplate obj )
	{
		exerciseDefinitionService.removeExerciseDefinition( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< ExerciseTemplate > findAll()
	{
		return exerciseDefinitionService.findAllExerciseDefinitions();
	}
	
	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseDefinitionService;
	}

}
