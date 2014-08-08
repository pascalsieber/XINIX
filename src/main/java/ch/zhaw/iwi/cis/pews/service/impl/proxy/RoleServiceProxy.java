package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.rest.RoleRestService;

public class RoleServiceProxy extends WorkshopObjectServiceProxy implements RoleService
{

	protected RoleServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, RoleRestService.BASE );
	}

}
