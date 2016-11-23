package ch.zhaw.sml.iwi.cis.pews.test.service.xinix;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.MediaServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;
import ch.zhaw.iwi.cis.pews.service.xinix.proxy.XinixImageMatrixServiceProxy;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.XinixImageMatrixRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class XinixRestServiceTest
{
	private static XinixImageMatrixService xinixImageMatrixService;
	private static MediaService            mediaService;

	private static XinixImageMatrix xinixImageMatrix = new XinixImageMatrix();
	private static MediaObject      imageone         = new MediaObject();
	private static MediaObject      imagetwo         = new MediaObject();

	@BeforeClass public static void setup()
	{
		xinixImageMatrixService = ServiceProxyManager.createServiceProxy( XinixImageMatrixServiceProxy.class );
		mediaService = ServiceProxyManager.createServiceProxy( MediaServiceProxy.class );

		try
		{
			File temp = new File( "tempxinixrestservicetest.jpg" );
			FileUtils.copyURLToFile( new URL( "http://images.freeimages.com/images/previews/1da/lotus-1377828.jpg" ),
					temp );

			imageone.setID( mediaService.persistMediaObjectFormData( temp,
					MediaObjectType.XINIX,
					ZhawEngine.ROOT_USER_LOGIN_NAME,
					"root" ) );
			imagetwo.setID( mediaService.persistMediaObjectFormData( temp,
					MediaObjectType.XINIX,
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
		xinixImageMatrix.setID( xinixImageMatrixService.persistImageMatrix( new XinixImageMatrix( Arrays.asList( (MediaObject)mediaService
				.findByID( imageone.getID() ), (MediaObject)mediaService.findByID( imagetwo.getID() ) ) ) ) );
		assertTrue( xinixImageMatrix.getID() != null );
		assertTrue( !xinixImageMatrix.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		XinixImageMatrix found = xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrix.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( xinixImageMatrix.getID() ) );
		assertTrue( found.getXinixImages().size() == 2 );
		for ( MediaObject img : found.getXinixImages() )
		{
			assertTrue( img.getID().equals( imageone.getID() ) || img.getID().equals( imagetwo.getID() ) );
		}
	}

	@TestOrder( order = 3 ) @Test public void testFindAll()
	{
		XinixImageMatrix removable = xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrix.getID() );
		assertTrue( removable != null );
		assertTrue( TestUtil.extractIds( xinixImageMatrixService.findAllXinixImageMatrices() )
				.contains( removable.getID() ) );

		xinixImageMatrixService.remove( xinixImageMatrix );
		assertTrue( xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrix.getID() ) == null );
		assertTrue( !TestUtil.extractIds( xinixImageMatrixService.findAllXinixImageMatrices() )
				.contains( removable.getID() ) );
	}
}
