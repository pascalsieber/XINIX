package ch.zhaw.sml.iwi.cis.pews.test.service.media;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.MediaServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.MediaRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - FIND_BY_TYPE
 * - GET_CONTENT_BY_ID
 * - UPDATE_POSTER_IMAGES
 * - UPDATE_POSTER_VIDEOS
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class MediaRestServiceTest
{
	private static MediaService mediaService;

	private static MediaObject mediaObject      = new MediaObject();
	private static MediaObject otherMediaObject = new MediaObject();

	private static String          MIME;
	private static byte[]          BLOB;
	private static MediaObjectType TYPE;
	private static MediaObjectType OTHER_TYPE;

	@BeforeClass public static void setup()
	{
		// services
		mediaService = ServiceProxyManager.createServiceProxy( MediaServiceProxy.class );

		// setup elements for mediaObject
		try
		{
			MIME = "image/png";
			TYPE = MediaObjectType.POSTERIMAGE;
			OTHER_TYPE = MediaObjectType.POSTERVIDEO;

			File tempFile = new File( "tempFile" );
			FileUtils.copyURLToFile( new URL(
					"http://www.whatnextpawan.com/wp-content/uploads/2014/03/oh-yes-its-free.png" ), tempFile );
			FileInputStream inputStream = new FileInputStream( tempFile );
			BLOB = IOUtils.toByteArray( inputStream );
			inputStream.close();
			tempFile.delete();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		// other media object
		otherMediaObject.setID( mediaService.persist( new MediaObject( MIME, BLOB, OTHER_TYPE ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		// not replicating exact REST method using HttpServletRequest
		// testing with persist method instead
		mediaObject.setID( mediaService.persist( new MediaObject( MIME, BLOB, TYPE ) ) );
		assertTrue( mediaObject.getID() != null );
		assertTrue( !mediaObject.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		MediaObject found = mediaService.findByID( mediaObject.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( mediaObject.getID() ) );
		assertTrue( found.getMimeType().equals( MIME ) );
		assertTrue( Arrays.equals( BLOB, found.getBlob() ) );
		assertTrue( found.getMediaObjectType().equals( TYPE ) );
	}

	@TestOrder( order = 3 ) @Test public void testFindByType()
	{
		MediaObject findable = mediaService.findByID( mediaObject.getID() );
		assertTrue( mediaService.findByType( TYPE ).contains( findable ) );

		MediaObject excludable = mediaService.findByID( otherMediaObject.getID() );
		assertTrue( excludable != null );
		assertTrue( mediaService.findByType( OTHER_TYPE ).contains( excludable ) );
		assertTrue( !mediaService.findByType( TYPE ).contains( excludable ) );
	}

	@TestOrder( order = 4 ) @Test public void testGetContentByID()
	{
		// not tested, as not exposed via proxy
	}

	@TestOrder( order = 5 ) @Test public void testFindAll()
	{
		MediaObject findable = mediaService.findByID( mediaObject.getID() );
		assertTrue( mediaService.findAll().contains( findable ) );
	}

	@TestOrder( order = 6 ) @Test public void testRemove()
	{
		MediaObject removable = mediaService.findByID( mediaObject.getID() );
		assertTrue( mediaService.findAll().contains( removable ) );

		mediaService.remove( mediaObject );
		assertTrue( mediaService.findByID( mediaObject.getID() ) == null );
		assertTrue( !mediaService.findAll().contains( removable ) );
	}
}
