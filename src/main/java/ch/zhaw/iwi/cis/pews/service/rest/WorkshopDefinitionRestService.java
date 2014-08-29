package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	public String persist( WorkshopDefinitionImpl obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public WorkshopDefinitionImpl findByID( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( WorkshopDefinitionImpl obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< WorkshopDefinitionImpl > findAll()
	{
		return super.findAll();
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return workshopDefinitionService;
	}

}
