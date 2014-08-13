package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ThreadLocalFilter;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.service.GlobalService;

@ManagedObject( scope = Scope.THREAD )
public class GlobalServiceImpl implements GlobalService
{
	public String shutdown()
	{
		ZhawEngine.getEngine().stop();

		return "shutdown successful";
	}

	public String ping()
	{
		return "ping!";
	}

	public String showPrincipal()
	{
		return "principal=" + ThreadLocalFilter.getServletRequest().getUserPrincipal();
	}

	@SuppressWarnings( "static-access" )
	@Override
	public Client getRootClient()
	{
		return ZhawEngine.getEngine().getRootClient();
	}
}
