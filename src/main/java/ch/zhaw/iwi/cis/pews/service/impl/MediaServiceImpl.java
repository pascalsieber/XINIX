package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.dao.MediaDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.MediaDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class MediaServiceImpl extends WorkshopObjectServiceImpl implements MediaService
{
	private MediaDao mediaDao;

	public MediaServiceImpl()
	{
		mediaDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( MediaDaoImpl.class.getSimpleName() );
	}

	@Override
	public String persistMediaObject( HttpServletRequest request )
	{
		try
		{
			MediaObject mediaObject = new MediaObject();

			// String encoding = request.getCharacterEncoding();

			String mediaLocation = PewsConfig.properties.getProperty( "UPLOAD_DIR" );

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

			mediaObject.setType( MediaObjectType.valueOf( value.toString() ) );

			// map fileName argument to media object
			Part fileNamePart = request.getPart( "fileName" );

			if ( fileNamePart == null )
				throw new RuntimeException( "please specify file name!" );

			reader = new BufferedReader( new InputStreamReader( fileNamePart.getInputStream() ) );
			value = new StringBuilder();
			buffer = new char[ 8192 ];

			for ( int length; ( length = reader.read( buffer ) ) > 0; )
			{
				value.append( buffer, 0, length );
			}

			mediaObject.setFileName( value.toString() );

			// store attachment and map path to mediaObject
			Part filePart = request.getPart( "file" );

			if ( filePart == null )
				throw new RuntimeException( "please specify file to be stored!" );

			String clientFolderURL = URLEncoder.encode( UserContext.getCurrentUser().getClient().getID(), "UTF-8" ).replace( "+", "%20" );

			File dir = new File( mediaLocation + File.separator + clientFolderURL );
			if ( !dir.exists() )
				dir.mkdirs();

			String nameOfFile = filePart.getSubmittedFileName();
			File serverFile = new File( dir.getAbsolutePath() + File.separator + nameOfFile );

			BufferedInputStream input = new BufferedInputStream( filePart.getInputStream(), 8192 );
			BufferedOutputStream output = new BufferedOutputStream( new FileOutputStream( serverFile ), 8192 );
			byte[] bytes = new byte[ 8192 ];
			for ( int length = 0; ( ( length = input.read( bytes ) ) > 0 ); )
			{
				output.write( bytes, 0, length );
			}
			output.close();

			mediaObject.setFilePath( serverFile.getAbsolutePath() );
			mediaObject.setUrl( "pathToURL" );

			// persist mediaObject
			return super.persist( mediaObject );
		}
		catch ( IOException | ServletException e )
		{
			throw new RuntimeException( e );
		}
	}

	@Override
	public List< MediaObject > findByType( MediaObjectType type )
	{
		return mediaDao.findByType( type );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return mediaDao;
	}
}
