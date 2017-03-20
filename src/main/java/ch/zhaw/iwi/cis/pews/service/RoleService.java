package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RoleService extends WorkshopObjectService
{

	public List< RoleImpl > findAllRoles();
}
