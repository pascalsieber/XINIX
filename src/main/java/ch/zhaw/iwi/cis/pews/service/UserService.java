package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserService extends IdentifiableObjectService
{

	public PrincipalImpl findByLoginName( String loginName );

	public String requestNewPassword( int userID );

}
