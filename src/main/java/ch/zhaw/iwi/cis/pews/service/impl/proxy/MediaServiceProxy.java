package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.rest.MediaRestService;

public class MediaServiceProxy extends WorkshopObjectServiceProxy implements MediaService
{

	protected MediaServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, MediaRestService.BASE );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< MediaObject > findByType( MediaObjectType type )
	{
		return getServiceTarget().path( MediaRestService.FIND_BY_TYPE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( type ) ).readEntity( List.class );
	}

	@Override
	public String persistMediaObject( HttpServletRequest request )
	{
		throw new UnsupportedOperationException( "persisting of MediaObject not to be used in proxy service" );
	}
}
