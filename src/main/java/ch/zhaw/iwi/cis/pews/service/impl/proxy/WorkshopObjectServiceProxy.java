package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;

public class WorkshopObjectServiceProxy extends ServiceProxy implements WorkshopObjectService
{

	protected WorkshopObjectServiceProxy( String hostName, int port, String userName, String password, String servicePath )
	{
		super( hostName, port, userName, password, servicePath );
	}

	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		return getServiceTarget().path( IdentifiableObjectRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( object ) ).readEntity( String.class );
	}

	@Override
	public < T extends WorkshopObject > void remove( T object )
	{
		getServiceTarget().path( IdentifiableObjectRestService.REMOVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( object ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > T findByID( String id )
	{
		return (T)getServiceTarget().path( IdentifiableObjectRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( WorkshopObject.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > List< T > findAll( String clientID )
	{
		return getServiceTarget().path( IdentifiableObjectRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( clientID ) ).readEntity( List.class );
	}

}
