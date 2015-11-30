package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;

public interface RoleDao extends WorkshopObjectDao
{

	public List< RoleImpl > findAllRoles();

}
