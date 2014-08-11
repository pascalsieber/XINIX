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
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopDefinitionServiceImpl;

@Path( WorkshopDefinitionRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class WorkshopDefinitionRestService extends WorkshopObjectRestService
{

	public static final String BASE = "/workshopService/definition";

	private WorkshopDefinitionService workshopDefinitionService;

	public WorkshopDefinitionRestService()
	{
		super();
		workshopDefinitionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDefinitionServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persistWorkshopDefinition( WorkshopDefinitionImpl obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public WorkshopDefinitionImpl findWorkshopDefinitionById( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeWorkshopDefinition( WorkshopDefinitionImpl obj )
	{
		super.remove( obj );
	}

	@POST
	@Path( FIND_ALL )
	public List< WorkshopDefinitionImpl > findAllWorkshopDefinitions(@Context HttpServletRequest request)
	{
		return super.findAll( getUserService().getClientFromAuth( request ) );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return workshopDefinitionService;
	}

}
