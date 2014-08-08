package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ClientServiceImpl;

@Path( ClientRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ClientRestService extends IdentifiableObjectRestService
{
	public static final String BASE = "/clientService/client";

	private ClientService clientService;

	public ClientRestService()
	{
		clientService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persistClient( Client obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public Client findClientByID( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeClient( Client obj )
	{
		super.remove( obj );
	}

	@POST
	@Path( FIND_ALL )
	public List< Client > findAllClients()
	{
		return super.findAll();
	}

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return clientService;
	}

}
