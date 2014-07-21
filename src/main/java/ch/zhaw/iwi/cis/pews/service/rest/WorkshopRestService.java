package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopServiceImpl;

@Path( WorkshopRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class WorkshopRestService extends IdentifiableObjectRestService
{

	public static final String BASE = "/workshopService/workshop";

	public static final String START = "/start";
	public static final String STOP = "/stop";

	private WorkshopService workshopService;

	public WorkshopRestService()
	{
		workshopService = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public int persistWorkshop( WorkshopImpl obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public WorkshopImpl findWorkshopById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeWorkshop( WorkshopImpl obj )
	{
		super.remove( obj );
	}

	@POST
	@Path( FIND_ALL )
	public List< WorkshopImpl > findAllWorkshops()
	{
		return super.findAll( WorkshopImpl.class.getSimpleName() );
	}

	@POST
	@Path( START )
	public void statWorkshop( WorkshopImpl workshop )
	{
		workshopService.start( workshop.getID() );
	}

	@POST
	@Path( STOP )
	public void stopWorkshop( WorkshopImpl workshop )
	{
		workshopService.stop( workshop.getID() );
	}

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return workshopService;
	}

}
