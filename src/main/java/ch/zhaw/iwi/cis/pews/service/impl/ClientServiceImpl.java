package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.ClientDao;
import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ClientDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.ClientService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ClientServiceImpl extends IdentifiableObjectServiceImpl implements ClientService
{
	private final ClientDao clientDao;

	public ClientServiceImpl()
	{
		clientDao = new ClientDaoImpl();
		//clientDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ClientDaoImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return clientDao;
	}

}
