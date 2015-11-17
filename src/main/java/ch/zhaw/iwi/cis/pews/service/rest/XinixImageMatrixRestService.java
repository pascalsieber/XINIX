package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.model.xinix.dto.XinixImageMatrixSimpleView;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;
import ch.zhaw.iwi.cis.pews.service.xinix.impl.XinixImageMatrixServiceImpl;

@Path( XinixImageMatrixRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class XinixImageMatrixRestService extends WorkshopObjectRestService
{
	public static final String BASE = "/xinixService/imageMatrix";
	private XinixImageMatrixService xinixImageMatrixService;

	public XinixImageMatrixRestService()
	{
		super();
		xinixImageMatrixService = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageMatrixServiceImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return xinixImageMatrixService;
	}

	@POST
	@Path( PERSIST )
	public String persistXinixImageMatrix( XinixImageMatrix obj )
	{
		return xinixImageMatrixService.persistImageMatrix( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public XinixImageMatrix findXinixImageMatrixByID( String id )
	{
		return xinixImageMatrixService.findXinixImageMatrixByID( id );
	}

	@POST
	@Path( FIND_ALL )
	public List< XinixImageMatrixSimpleView > findAllXinixImageMatrices()
	{
		return xinixImageMatrixService.findAllXinixImageMatrices();
	}

	@POST
	@Path( REMOVE )
	public void removeXinixImageMatrix( XinixImageMatrix obj )
	{
		xinixImageMatrixService.remove( obj );
	}
}
