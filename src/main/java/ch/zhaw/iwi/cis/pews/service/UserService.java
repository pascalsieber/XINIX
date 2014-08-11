package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserService extends WorkshopObjectService
{

	public PrincipalImpl findByLoginName( String loginName );

	public boolean requestNewPassword( String userID );

	public List< PrincipalImpl > findAllUsersForLoginService();

	public String getClientFromAuth( HttpServletRequest request );

}
