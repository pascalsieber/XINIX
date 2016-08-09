package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

public interface UserService extends WorkshopObjectService
{

	public PrincipalImpl findByLoginName( String loginName );

	public PrincipalImpl findByLoginNameForUserContext( String loginName );

	public List< PrincipalImpl > findAllUsersForLoginService();

	public PrincipalImpl findUserByID( String id );

	public void sendProfile( String userID );

	/**
	 * specialized method for persisting user belonging to a different client than the one with whom the user making the request is connected. this is required for admins to create users for varying
	 * clients
	 * 
	 * @param principal
	 *            user / principal to be persisted
	 * @return ID of persisted user / principal
	 */
	public String persistForClient( PrincipalImpl principal );
}
