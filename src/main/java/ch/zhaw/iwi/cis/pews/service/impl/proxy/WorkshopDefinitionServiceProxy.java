package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.rest.WorkshopDefinitionRestService;

public class WorkshopDefinitionServiceProxy extends WorkshopObjectServiceProxy implements WorkshopDefinitionService
{

	protected WorkshopDefinitionServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, WorkshopDefinitionRestService.BASE );
	}

	@Override
	public WorkshopTemplate findWorkshopDefinitionByID( String id )
	{
		return getServiceTarget().path( WorkshopDefinitionRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( WorkshopTemplate.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopTemplate > findAllWorkshopDefinitions()
	{
		return getServiceTarget().path( WorkshopDefinitionRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

}
