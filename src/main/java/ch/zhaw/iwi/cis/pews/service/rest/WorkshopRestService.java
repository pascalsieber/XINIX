package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopServiceImpl;

@Path( WorkshopRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class WorkshopRestService extends WorkshopObjectRestService
{

	public static final String BASE = "/workshopService/workshop";

	public static final String START = "/start";
	public static final String STOP = "/stop";
	public static final String RESET = "/resetByID";

	private WorkshopService workshopService;

	public WorkshopRestService()
	{
		super();
		workshopService = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( WorkshopImpl obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public WorkshopImpl findByID( String id )
	{
		return workshopService.findWorkshopByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( WorkshopImpl obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< WorkshopImpl > findAll()
	{
		return workshopService.findAllWorkshopsSimple();
	}

	@POST
	@Path( START )
	public void statWorkshop( String workshopID )
	{
		workshopService.start( workshopID );
	}

	@POST
	@Path( STOP )
	public void stopWorkshop( String workshopID )
	{
		workshopService.stop( workshopID );
	}

	@POST
	@Path( RESET )
	public void resetWorkshop( String workshopID )
	{
		workshopService.reset( workshopID );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return workshopService;
	}

}
