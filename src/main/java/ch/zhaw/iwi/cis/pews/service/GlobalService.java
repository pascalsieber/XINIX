package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.Client;

public interface GlobalService extends Service
{
	public String shutdown();

	public String ping();

	public String showPrincipal();

	public Client getRootClient();
}
