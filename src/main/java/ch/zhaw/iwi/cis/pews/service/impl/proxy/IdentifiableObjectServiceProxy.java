package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.management.RuntimeErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;

public abstract class IdentifiableObjectServiceProxy extends ServiceProxy implements IdentifiableObjectService
{
	protected IdentifiableObjectServiceProxy( String hostName, int port, String userName, String password, String servicePath )
	{
		super( hostName, port, userName, password, servicePath );
	}

	public < T extends IdentifiableObject > int persist( T persistentObject )
	{
		return getServiceTarget().path( IdentifiableObjectRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( jsonStringify( persistentObject ) ) ).readEntity( int.class );
	}

	public < T extends IdentifiableObject > void remove( T persistentObject )
	{
		getServiceTarget().path( IdentifiableObjectRestService.REMOVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json(persistentObject  ) );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > T findByID( int persistentObjectID )
	{
		return (T)getServiceTarget()
			.path( IdentifiableObjectRestService.FIND_BY_ID )
			.request( MediaType.APPLICATION_JSON )
			.post( Entity.json( persistentObjectID ) )
			.readEntity( IdentifiableObject.class );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > List< T > findAll( String className )
	{
		return getServiceTarget().path( IdentifiableObjectRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( className ) ).readEntity( List.class );
	}
	
	public <T extends Object > String jsonStringify (T object) {
		try
		{
			return getMapper().writeValueAsString( object );
		}
		catch ( JsonProcessingException e )
		{
			throw new RuntimeErrorException(null);
		}
	}
}
