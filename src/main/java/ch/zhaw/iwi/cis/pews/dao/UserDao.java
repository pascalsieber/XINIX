package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserDao extends IdentifiableObjectDao
{

	public PrincipalImpl findByLoginName( String loginName );

}
