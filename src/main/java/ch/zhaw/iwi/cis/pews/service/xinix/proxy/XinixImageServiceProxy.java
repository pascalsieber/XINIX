package ch.zhaw.iwi.cis.pews.service.xinix.proxy;

import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopObjectServiceProxy;
import ch.zhaw.iwi.cis.pews.service.rest.XinixImageRestService;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageService;

public class XinixImageServiceProxy extends WorkshopObjectServiceProxy implements XinixImageService
{

	protected XinixImageServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, XinixImageRestService.BASE );
	}

}
