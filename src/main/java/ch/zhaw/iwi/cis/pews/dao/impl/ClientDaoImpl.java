package ch.zhaw.iwi.cis.pews.dao.impl;

import ch.zhaw.iwi.cis.pews.dao.ClientDao;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

public class ClientDaoImpl extends IdentifiableObjectDaoImpl implements ClientDao
{

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return Client.class;
	}

}
