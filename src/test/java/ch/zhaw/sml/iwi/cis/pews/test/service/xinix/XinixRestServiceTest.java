package ch.zhaw.sml.iwi.cis.pews.test.service.xinix;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.MediaServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;
import ch.zhaw.iwi.cis.pews.service.xinix.proxy.XinixImageMatrixServiceProxy;
import org.junit.BeforeClass;
import org.junit.Test;

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
public class XinixRestServiceTest
{
	private XinixImageMatrixService xinixImageMatrixService;
	private MediaService            mediaService;

	private XinixImageMatrix xinixImageMatrix = new XinixImageMatrix();
	private MediaObject      imageone         = new MediaObject();
	private MediaObject      imagetwo         = new MediaObject();

	@BeforeClass public void setup()
	{
		xinixImageMatrixService = ServiceProxyManager.createServiceProxy( XinixImageMatrixServiceProxy.class );
		mediaService = ServiceProxyManager.createServiceProxy( MediaServiceProxy.class );

		imageone.setID( mediaService.persist( new MediaObject( "", "".getBytes(), MediaObjectType.XINIX ) ) );
		imagetwo.setID( mediaService.persist( new MediaObject( "", "".getBytes(), MediaObjectType.XINIX ) ) );
	}

	@Test public void testPersist()
	{
		xinixImageMatrix.setID( xinixImageMatrixService.persistImageMatrix( new XinixImageMatrix( Arrays.asList( imageone,
				imagetwo ) ) ) );
		assertTrue( xinixImageMatrix.getID() != null );
		assertTrue( !xinixImageMatrix.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		XinixImageMatrix found = xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrix.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( xinixImageMatrix.getID() ) );
		assertTrue( found.getXinixImages()
				.containsAll( Arrays.asList( mediaService.findByID( imageone.getID() ),
						mediaService.findByID( imagetwo.getID() ) ) ) );
	}

	@Test public void testFindAll()
	{
		XinixImageMatrix removable = xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrix.getID() );
		assertTrue( xinixImageMatrixService.findAllXinixImageMatrices().contains( removable ) );

		xinixImageMatrixService.remove( xinixImageMatrix );
		assertTrue( xinixImageMatrixService.findXinixImageMatrixByID( xinixImageMatrix.getID() ) == null );
		assertTrue( xinixImageMatrixService.findAllXinixImageMatrices().contains( removable ) );
	}
}
