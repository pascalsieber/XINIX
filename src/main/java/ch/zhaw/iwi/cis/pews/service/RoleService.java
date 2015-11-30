package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;


public interface RoleService extends WorkshopObjectService
{

	public List< RoleImpl > findAllRoles();
}
