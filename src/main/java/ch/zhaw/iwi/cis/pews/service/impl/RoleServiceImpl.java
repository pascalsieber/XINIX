package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.RoleDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.RoleDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.service.RoleService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class RoleServiceImpl extends WorkshopObjectServiceImpl implements RoleService
{
	private final RoleDao roleDao;

	public RoleServiceImpl()
	{
		roleDao = new RoleDaoImpl();
		//roleDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( RoleDaoImpl.class.getSimpleName() );

	}

	@Override
	public List< RoleImpl > findAllRoles()
	{
		return roleDao.findAllRoles();
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return roleDao;
	}
}
