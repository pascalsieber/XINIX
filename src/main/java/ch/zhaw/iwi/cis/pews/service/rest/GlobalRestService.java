package ch.zhaw.iwi.cis.pews.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;

@Path( "/global" )
public class GlobalRestService extends RestService
{
	public static final String SHUTDOWN = "/shutdown";
	public static final String PING = "/ping";

	@GET
	@Path( "/shutdown" )
	public String shutdown()
	{
		try {
			ZhawEngine.getEngine().stop();
		} catch	(Exception e) {
		    return "Shutdown failed (" + e.getMessage() + ")";
		}

		return "Shutdown successful";
	}

	@GET
	@Path( "/ping" )
	public String ping()
	{
	    return "pong!";
	}
}
