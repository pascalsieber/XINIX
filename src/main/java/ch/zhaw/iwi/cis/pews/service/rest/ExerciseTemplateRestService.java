package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseTemplateServiceImpl;

@Path( ExerciseTemplateRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseTemplateRestService extends WorkshopObjectRestService
{

	public final static String BASE = "/exerciseService/template";

	private ExerciseTemplateService exerciseTemplateService;

	public ExerciseTemplateRestService()
	{
		super();
		exerciseTemplateService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
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
		return exerciseTemplateService.findExerciseTemplateByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( ExerciseTemplate obj )
	{
		exerciseTemplateService.removeExerciseTemplate( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< ExerciseTemplate > findAll()
	{
		return exerciseTemplateService.findAllExerciseTemplates();
	}
	
	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return exerciseTemplateService;
	}

}
