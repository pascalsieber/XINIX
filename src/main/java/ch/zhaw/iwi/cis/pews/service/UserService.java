package ch.zhaw.iwi.cis.pews.service;

public interface UserService extends IdentifiableObjectService
{

	public void acceptInvitation( int userID, int sessionID );

}
