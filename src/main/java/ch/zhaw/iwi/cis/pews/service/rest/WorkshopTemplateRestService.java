package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopTemplateServiceImpl;

@Path( WorkshopTemplateRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class WorkshopTemplateRestService extends WorkshopObjectRestService
{

	public static final String BASE = "/workshopService/template";
	public static final String UPDATE_EXERCISE_TEMPLATES_ORDER = "/updateOrderOfExerciseTemplates";

	private WorkshopTemplateService workshopTemplateService;

	public WorkshopTemplateRestService()
	{
		super();
		workshopTemplateService = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopTemplateServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( WorkshopTemplate obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public WorkshopTemplate findByID( String id )
	{
		return workshopTemplateService.findWorkshopTemplateByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( WorkshopTemplate obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< WorkshopTemplate > findAll()
	{
		return workshopTemplateService.findAllWorkshopTemplates();
	}

	@POST
	@Path( UPDATE_EXERCISE_TEMPLATES_ORDER )
	public void updateOrderOfExerciseTemplates( WorkshopTemplate wrapper )
	{
		workshopTemplateService.updateOrderOfExerciseTemplates( wrapper );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return workshopTemplateService;
	}

}
