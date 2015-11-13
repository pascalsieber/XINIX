package ch.zhaw.iwi.cis.pews.service.xinix.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopObjectServiceProxy;
import ch.zhaw.iwi.cis.pews.service.rest.XinixImageMatrixRestService;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;

public class XinixImageMatrixServiceProxy extends WorkshopObjectServiceProxy implements XinixImageMatrixService
{

	protected XinixImageMatrixServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, XinixImageMatrixRestService.BASE );
	}

	@Override
	public XinixImageMatrix findXinixImageMatrixByID( String id )
	{
		return getServiceTarget().path( XinixImageMatrixRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( XinixImageMatrix.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< XinixImageMatrix > findAllXinixImageMatrices()
	{
		return getServiceTarget().path( XinixImageMatrixRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

}
