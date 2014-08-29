package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ServletContextFilter;
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
		return "principal=" + ServletContextFilter.getServletRequest().getUserPrincipal();
	}

	@Override
	public Client getRootClient()
	{
		return ZhawEngine.getRootClient();
	}
}
