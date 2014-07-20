package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.rest.WorkshopRestService;

public class WorkshopServiceProxy extends IdentifiableObjectServiceProxy implements WorkshopService
{

	protected WorkshopServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, WorkshopRestService.BASE );
	}

	@Override
	public void start( int id )
	{
		getServiceTarget().path( WorkshopRestService.START ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void stop( int id )
	{
		getServiceTarget().path( WorkshopRestService.STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}


}