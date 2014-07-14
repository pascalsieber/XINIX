package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.service.GlobalService;
import ch.zhaw.iwi.cis.pews.service.rest.GlobalRestService;

public class GlobalServiceProxy extends ServiceProxy implements GlobalService
{
	protected GlobalServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, GlobalRestService.GLOBAL_BASE );
	}

	@Override
	public String shutdown()
	{
		return getServiceTarget().path( GlobalRestService.SHUTDOWN ).request( MediaType.APPLICATION_JSON ).get().readEntity( String.class );
	}

	@Override
	public String ping()
	{
		return getServiceTarget().path( GlobalRestService.PING ).request( MediaType.APPLICATION_JSON ).get().readEntity( String.class );
	}

	@Override
	public String showPrincipal()
	{
		return getServiceTarget().path( GlobalRestService.SHOW_PRINCIPAL ).request( MediaType.APPLICATION_JSON ).get().readEntity( String.class );
	}
}
