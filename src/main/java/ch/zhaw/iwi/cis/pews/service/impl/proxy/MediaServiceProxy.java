package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.rest.MediaRestService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.HttpClients;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MediaServiceProxy extends WorkshopObjectServiceProxy implements MediaService
{

	protected MediaServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, MediaRestService.BASE );
	}

	@SuppressWarnings( "unchecked" ) @Override public List<MediaObject> findByType( MediaObjectType type )
	{
		return getServiceTarget().path( MediaRestService.FIND_BY_TYPE )
				.request( MediaType.APPLICATION_JSON )
				.post( Entity.json( type.toString() ) )
				.readEntity( List.class );
	}

	@Override public String persistMediaObjectFormData( File file, MediaObjectType mediaObjectType, String username,
			String password )
	{
		try
		{
			String mimeType = new MimetypesFileTypeMap().getContentType( file );
			HttpEntity entity = MultipartEntityBuilder.create()
					.addTextBody( "type", mediaObjectType.toString() )
					.addBinaryBody( "file", file, ContentType.create( mimeType ), file.getName() )
					.build();

			HttpPost httpPost = new HttpPost( getServiceTarget().getUri() + MediaRestService.PERSIST );
			httpPost.addHeader( new BasicScheme().authenticate( new UsernamePasswordCredentials( username, password ),
					httpPost,
					null ) );

			httpPost.setEntity( entity );
			HttpResponse response = HttpClients.createDefault().execute( httpPost );
			return IOUtils.toString( response.getEntity().getContent() );
		}
		catch ( IOException | AuthenticationException e )
		{
			throw new RuntimeException( "error in persisting media object through proxy" );
		}
	}

	@Override public String persistMediaObject( HttpServletRequest request )
	{
		throw new UnsupportedOperationException( "persisting of MediaObject not to be used in proxy service" );
	}
}
