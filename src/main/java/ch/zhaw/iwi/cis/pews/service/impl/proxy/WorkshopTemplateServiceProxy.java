package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.rest.WorkshopTemplateRestService;

public class WorkshopTemplateServiceProxy extends WorkshopObjectServiceProxy implements WorkshopTemplateService
{

	protected WorkshopTemplateServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, WorkshopTemplateRestService.BASE );
	}

	@Override
	public String persistWorkshopTemplate( WorkshopTemplate obj )
	{
		return getServiceTarget().path( WorkshopTemplateRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) ).readEntity( String.class );
	}

	@Override
	public WorkshopTemplate findWorkshopTemplateByID( String id )
	{
		return getServiceTarget().path( WorkshopTemplateRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( WorkshopTemplate.class );
	}

	@Override
	public void updateOrderOfExerciseTemplates( WorkshopTemplate wrapper )
	{
		getServiceTarget().path( WorkshopTemplateRestService.UPDATE_EXERCISE_TEMPLATES_ORDER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( wrapper ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopTemplate > findAllWorkshopTemplates()
	{
		return getServiceTarget().path( WorkshopTemplateRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

}
