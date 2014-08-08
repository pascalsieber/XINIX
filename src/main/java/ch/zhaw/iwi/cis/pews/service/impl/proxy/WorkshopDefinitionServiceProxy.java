package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.rest.WorkshopDefinitionRestService;

public class WorkshopDefinitionServiceProxy extends WorkshopObjectServiceProxy implements WorkshopDefinitionService
{

	protected WorkshopDefinitionServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, WorkshopDefinitionRestService.BASE );
	}

}
