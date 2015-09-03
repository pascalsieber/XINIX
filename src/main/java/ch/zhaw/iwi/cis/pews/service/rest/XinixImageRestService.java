package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImage;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageService;
import ch.zhaw.iwi.cis.pews.service.xinix.impl.XinixImageServiceImpl;

@Path( XinixImageRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class XinixImageRestService extends WorkshopObjectRestService
{
	public static final String BASE = "/xinixService/image";
	private XinixImageService xinixImageService;

	public XinixImageRestService()
	{
		super();
		xinixImageService = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageServiceImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return xinixImageService;
	}
	
	@POST
	@Path( PERSIST )
	public String persistXinixImage( XinixImage obj )
	{
		return xinixImageService.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public XinixImage findXinixImageByID( String id )
	{
		return xinixImageService.findByID( id );
	}

	@POST
	@Path( FIND_ALL )
	public List< XinixImage > findAllXinixImages()
	{
		return xinixImageService.findAll();
	}

	@POST
	@Path( REMOVE )
	public void removeXinixImage( XinixImage obj )
	{
		xinixImageService.remove( obj );
	}
	

}
