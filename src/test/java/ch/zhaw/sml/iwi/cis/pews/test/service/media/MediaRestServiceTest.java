package ch.zhaw.sml.iwi.cis.pews.test.service.media;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.MediaServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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

	private static String MIME;
	private static byte[] BLOB;
	private static MediaObjectType TYPE       = MediaObjectType.POSTERIMAGE;
	private static MediaObjectType OTHER_TYPE = MediaObjectType.P2PONE;

	@BeforeClass public static void setup()
	{
		// services
		mediaService = ServiceProxyManager.createServiceProxy( MediaServiceProxy.class );

		try
		{
			File temp = new File( "tempfilemediarestservicetest.jpg" );
			FileUtils.copyURLToFile( new URL( "http://images.freeimages.com/images/previews/1da/lotus-1377828.jpg" ),
					temp );

			MIME = new MimetypesFileTypeMap().getContentType( temp );
			BLOB = FileUtils.readFileToByteArray( temp );

			otherMediaObject.setID( mediaService.persistMediaObjectFormData( temp,
					OTHER_TYPE,
					ZhawEngine.ROOT_USER_LOGIN_NAME,
					"root" ) );
			temp.delete();
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "error in persisting media object" );
		}
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		try
		{
			File temp = new File( "tempfilemediarestservicetest2.jpg" );
			FileUtils.copyURLToFile( new URL( "http://images.freeimages.com/images/previews/1da/lotus-1377828.jpg" ),
					temp );
			mediaObject.setID( mediaService.persistMediaObjectFormData( temp,
					TYPE,
					ZhawEngine.ROOT_USER_LOGIN_NAME,
					"root" ) );
			temp.delete();
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "error in persisting media object" );
		}
		assertTrue( mediaObject.getID() != null );
		assertTrue( !mediaObject.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		MediaObject found = mediaService.findByID( mediaObject.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( mediaObject.getID() ) );
		assertTrue( found.getMimeType().equals( MIME ) );
		assertTrue( found.getMediaObjectType().equals( TYPE ) );

		// note that the correct persistance of blob is tested with testGetContentByID
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
		// TODO:implement
	}

	@TestOrder( order = 5 ) @Test public void testFindAll()
	{
		MediaObject findable = mediaService.findByID( mediaObject.getID() );
		assertTrue( TestUtil.extractIds( mediaService.findAll() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 6 ) @Test public void testRemove()
	{
		MediaObject removable = mediaService.findByID( mediaObject.getID() );
		assertTrue( TestUtil.extractIds( mediaService.findAll() ).contains( removable.getID() ) );

		mediaService.remove( mediaObject );
		assertTrue( mediaService.findByID( mediaObject.getID() ) == null );
		assertTrue( !TestUtil.extractIds( mediaService.findAll() ).contains( removable.getID() ) );
	}
}
