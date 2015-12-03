package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseTemplateRestService;

public class ExerciseTemplateServiceProxy extends WorkshopObjectServiceProxy implements ExerciseTemplateService
{

	protected ExerciseTemplateServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseTemplateRestService.BASE );
	}

	@Override
	public String persistExerciseTemplate( ExerciseTemplate obj )
	{
		return getServiceTarget().path( ExerciseTemplateRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) ).readEntity( String.class );
	}

	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		return getServiceTarget().path( ExerciseTemplateRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( ExerciseTemplate.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseTemplate > findAllExerciseTemplates()
	{
		return getServiceTarget().path( ExerciseTemplateRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@Override
	public void removeExerciseTemplate( ExerciseTemplate obj )
	{
		getServiceTarget().path( ExerciseTemplateRestService.REMOVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) );
	}

}
