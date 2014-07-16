package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserService extends IdentifiableObjectService
{

	public void acceptInvitation( int userID, int sessionID );

	public PrincipalImpl findByLoginName( String loginName );

}
