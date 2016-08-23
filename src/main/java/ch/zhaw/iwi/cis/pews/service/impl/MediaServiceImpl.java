package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.MediaDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.MediaDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class MediaServiceImpl extends WorkshopObjectServiceImpl implements MediaService
{
	private MediaDao        mediaDao;
	private ExerciseService exerciseService;

	public MediaServiceImpl()
	{
		mediaDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( MediaDaoImpl.class.getSimpleName() );
		exerciseService = ZhawEngine.getManagedObjectRegistry()
				.getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
	}

	@Override public String persistMediaObject( HttpServletRequest request )
	{
		try
		{
			MediaObject mediaObject = new MediaObject();

			// String encoding = request.getCharacterEncoding();

			Part typePart = request.getPart( "type" );

			if ( typePart == null )
				throw new RuntimeException( "please specify type of media object!" );

			BufferedReader reader = new BufferedReader( new InputStreamReader( typePart.getInputStream() ) );
			StringBuilder value = new StringBuilder();
			char[] buffer = new char[ 8192 ];
			for ( int length; ( length = reader.read( buffer ) ) > 0; )
			{
				value.append( buffer, 0, length );
			}

			mediaObject.setMediaObjectType( MediaObjectType.valueOf( value.toString() ) );

			// get byte array of file and store as blob
			Part filePart = request.getPart( "file" );

			if ( filePart == null )
				throw new RuntimeException( "please specify file to be stored!" );

			BufferedInputStream input = new BufferedInputStream( filePart.getInputStream(), 8192 );
			byte[] bytes = IOUtils.toByteArray( input );

			mediaObject.setBlob( bytes );

			// get content type
			mediaObject.setMimeType( filePart.getContentType() );

			// persist mediaObject
			return super.persist( mediaObject );
		}
		catch ( IOException | ServletException e )
		{
			throw new RuntimeException( e );
		}
	}

	@Override public List<MediaObject> findByType( MediaObjectType type )
	{
		return mediaDao.findByType( type );
	}

	@Override protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return mediaDao;
	}
}
