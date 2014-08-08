package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.rest.ClientRestService;

public class ClientServiceProxy extends IdentifiableObjectServiceProxy implements ClientService
{

	protected ClientServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ClientRestService.BASE );
	}

}
