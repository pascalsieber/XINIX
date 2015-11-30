package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.RoleDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class RoleDaoImpl extends WorkshopObjectDaoImpl implements RoleDao
{

	@SuppressWarnings( "unchecked" )
	@Override
	public List< RoleImpl > findAllRoles()
	{
		return getEntityManager().createQuery( "from " + this.getWorkshopObjectClass().getSimpleName() ).getResultList();
	}

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return RoleImpl.class;
	}

}
