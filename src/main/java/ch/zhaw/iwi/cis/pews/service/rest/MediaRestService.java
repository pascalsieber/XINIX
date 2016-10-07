package ch.zhaw.iwi.cis.pews.service.rest;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.MediaServiceImpl;
import ch.zhaw.iwi.cis.pews.service.wrapper.MediaObjectFormData;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path( MediaRestService.BASE ) @Consumes( { MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA } )
@Produces( { MediaType.APPLICATION_JSON } ) public class MediaRestService extends WorkshopObjectRestService
{
	public static final String BASE = "/mediaService";

	public static final String FIND_BY_TYPE      = "/findByType";
	public static final String GET_CONTENT_BY_ID = "/getContentByID";

	public static final String PERSIST_JSON = "/persistJson";

	private MediaService mediaService;

	public MediaRestService()
	{
		super();
		mediaService = ZhawEngine.getManagedObjectRegistry().getManagedObject( MediaServiceImpl.class.getSimpleName() );
	}

	@POST @Consumes( MediaType.MULTIPART_FORM_DATA ) @Path( PERSIST )
	public String persistMediaObject( @Context HttpServletRequest request )
	{
		return mediaService.persistMediaObject( request );
	}

	@POST @Path( FIND_BY_TYPE ) public List<MediaObject> findMediaByType( String type )
	{
		return mediaService.findByType( MediaObjectType.valueOf( type ) );
	}

	/**
	 * returns blob of requested mediaObject. used to enable dynamic urls to media content
	 *
	 * @param id
	 * @return blob of mediaObject
	 */
	@GET @Path( GET_CONTENT_BY_ID + "/{mediaobjectid}.{extension}" ) public Response getMediaContentByID(
			@PathParam( "mediaobjectid" ) String id )
	{
		MediaObject obj = mediaService.findByID( id );
		return Response.ok( obj.getBlob(), obj.getMimeType() ).build();
	}

	@POST @Path( FIND_BY_ID ) public MediaObject findMediaByID( String id )
	{
		return mediaService.findByID( id );
	}

	@POST @Path( FIND_ALL ) public List<MediaObject> findAllMediaObjects()
	{
		return mediaService.findAll();
	}

	@POST @Path( REMOVE ) public void removeMedia( MediaObject object )
	{
		mediaService.remove( object );
	}

	@Override protected WorkshopObjectService getWorkshopObjectService()
	{
		return mediaService;
	}

}
