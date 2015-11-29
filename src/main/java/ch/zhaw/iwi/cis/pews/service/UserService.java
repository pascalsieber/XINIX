package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserService extends WorkshopObjectService
{

	public PrincipalImpl findByLoginName( String loginName );

	public boolean requestNewPassword( String userID );

	public List< PrincipalImpl > findAllUsersForLoginService();

	public PrincipalImpl findUserByID( String id );

	public void sendProfile( String userID );
}
