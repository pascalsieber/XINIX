package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public abstract class IdentifiableObjectServiceProxy extends ServiceProxy implements IdentifiableObjectService
{
	protected IdentifiableObjectServiceProxy( String hostName, int port, String userName, String password, String servicePath )
	{
		super( hostName, port, userName, password, servicePath );
	}

	public < T extends IdentifiableObject > String persist( T persistentObject )
	{
		return getServiceTarget().path( IdentifiableObjectRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( persistentObject ) ).readEntity( String.class );
	}

	public < T extends IdentifiableObject > void remove( T persistentObject )
	{
		getServiceTarget().path( IdentifiableObjectRestService.REMOVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( persistentObject ) );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > T findByID( String persistentObjectID )
	{
		return (T)getServiceTarget()
			.path( IdentifiableObjectRestService.FIND_BY_ID )
			.request( MediaType.APPLICATION_JSON )
			.post( Entity.json( persistentObjectID ) )
			.readEntity( IdentifiableObject.class );
	}

	public < T extends IdentifiableObject > List< T > findAll( Class< ? > clazz )
	{
		Response response = getServiceTarget().path( IdentifiableObjectRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( clazz ) );
		String jsonString = response.readEntity( String.class );

		try
		{
			List< T > list = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			list = mapper.readValue( jsonString, TypeFactory.defaultInstance().constructCollectionType( ArrayList.class, clazz ) );
			return list;
		}
		catch ( IOException e )
		{
			throw new RuntimeErrorException( null, "IdentifiableObjectServiceProxy could not deserialize json response" );
		}

		// return getServiceTarget().path( IdentifiableObjectRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( className ) ).readEntity( List.class );
	}

	public < T extends Object > String jsonStringify( T object )
	{
		try
		{
			return getMapper().writeValueAsString( object );
		}
		catch ( JsonProcessingException e )
		{
			throw new RuntimeErrorException( null );
		}
	}
}
