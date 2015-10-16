package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.rest.WorkshopRestService;

public class WorkshopServiceProxy extends WorkshopObjectServiceProxy implements WorkshopService
{

	protected WorkshopServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, WorkshopRestService.BASE );
	}

	@Override
	public void start( String id )
	{
		getServiceTarget().path( WorkshopRestService.START ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void stop( String id )
	{
		getServiceTarget().path( WorkshopRestService.STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void renew( String id )
	{
		getServiceTarget().path( WorkshopRestService.RENEW ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopImpl > findAllWorkshopsSimple()
	{
		return getServiceTarget().path( WorkshopRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@Override
	public WorkshopImpl findWorkshopByID( String id )
	{
		return getServiceTarget().path( WorkshopRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( WorkshopImpl.class );
	}

	@Override
	public void reset( String workshopID )
	{
		getServiceTarget().path( WorkshopRestService.RESET ).request( MediaType.APPLICATION_JSON ).post( Entity.json( workshopID ) );
	}

	@Override
	public String generateFromTemplate( WorkshopImpl obj )
	{
		return getServiceTarget().path( WorkshopRestService.GENERATE_FROM_TEMPLATE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) ).readEntity( String.class );
	}
}
