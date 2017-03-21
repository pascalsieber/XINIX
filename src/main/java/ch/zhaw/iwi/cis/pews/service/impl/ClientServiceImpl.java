package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.ClientDao;
import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ClientDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.ClientService;

import javax.inject.Inject;

public class ClientServiceImpl extends IdentifiableObjectServiceImpl implements ClientService
{
	@Inject private ClientDao clientDao;

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return clientDao;
	}

}
