package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.MediaServiceImpl;

@Path( MediaRestService.BASE )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA } )
@Produces( MediaType.APPLICATION_JSON )
public class MediaRestService extends WorkshopObjectRestService
{
	public static final String BASE = "/mediaService";

	public static final String FIND_BY_TYPE = "/findByType";

	private MediaService mediaService;

	public MediaRestService()
	{
		super();
		mediaService = ZhawEngine.getManagedObjectRegistry().getManagedObject( MediaServiceImpl.class.getSimpleName() );
	}

	@POST
	@Consumes( MediaType.MULTIPART_FORM_DATA )
	@Path( PERSIST )
	public String persistMediaObject( @Context HttpServletRequest request )
	{
		return mediaService.persistMediaObject( request );
	}

	@POST
	@Path( FIND_BY_TYPE )
	public List< MediaObject > findMediaByType( MediaObjectType type )
	{
		return mediaService.findByType( type );
	}

	@POST
	@Path( FIND_BY_ID )
	public MediaObject findMediaByID( String id )
	{
		return mediaService.findByID( id );
	}

	@POST
	@Path( FIND_ALL )
	public List< MediaObject > findAllMediaObjects()
	{
		return mediaService.findAll();
	}

	@POST
	@Path( REMOVE )
	public void removeMedia( MediaObject object )
	{
		mediaService.remove( object );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return mediaService;
	}

}
