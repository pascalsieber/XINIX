package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserDao extends WorkshopObjectDao
{

	public PrincipalImpl findByLoginName( String loginName );

	public List< PrincipalImpl > finAllUsersForLoginService();

	public PrincipalImpl findUserByID( String id );

}
