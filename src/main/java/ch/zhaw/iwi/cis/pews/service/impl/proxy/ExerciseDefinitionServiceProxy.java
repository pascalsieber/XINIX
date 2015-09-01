package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseDefinitionRestService;

public class ExerciseDefinitionServiceProxy extends WorkshopObjectServiceProxy implements ExerciseDefinitionService
{

	protected ExerciseDefinitionServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseDefinitionRestService.BASE );
	}

	@Override
	public ExerciseTemplate findExerciseDefinitionByID( String id )
	{
		return getServiceTarget().path( ExerciseDefinitionRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( ExerciseTemplate.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseTemplate > findAllExerciseDefinitions()
	{
		return getServiceTarget().path( ExerciseDefinitionRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@Override
	public void removeExerciseDefinition( ExerciseTemplate obj )
	{
		getServiceTarget().path( ExerciseDefinitionRestService.REMOVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) );
	}

}
