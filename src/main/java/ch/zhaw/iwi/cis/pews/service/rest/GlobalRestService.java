package ch.zhaw.iwi.cis.pews.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.service.GlobalService;
import ch.zhaw.iwi.cis.pews.service.impl.GlobalServiceImpl;

@Path( GlobalRestService.GLOBAL_BASE )
public class GlobalRestService extends RestService
{
	public static final String GLOBAL_BASE = "/global";
	public static final String SHUTDOWN = "/shutdown";
	public static final String PING = "/ping";
	public static final String SHOW_PRINCIPAL = "/showPrincipal";
	public static final String GET_ROOT_CLIENT = "/getRootClient";

	private GlobalService globalService;

	public GlobalRestService()
	{
		globalService = ZhawEngine.getManagedObjectRegistry().getManagedObject( GlobalServiceImpl.class.getSimpleName() );
	}

	@GET
	@Path( SHUTDOWN )
	public String shutdown()
	{
		return globalService.shutdown();
	}

	@GET
	@Path( PING )
	public String ping()
	{
		return globalService.ping();
	}

	@GET
	@Path( SHOW_PRINCIPAL )
	public String showPrincipal()
	{
		return globalService.showPrincipal();
	}

	// used for testing
	@GET
	@Path( GET_ROOT_CLIENT )
	public Client getRootClient()
	{
		return globalService.getRootClient();
	}
}
