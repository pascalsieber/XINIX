package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.Invitation;

public interface InvitationService extends WorkshopObjectService
{

	public void accept( String invitationID );

	public Invitation findInvitationByID( String id );

	public List< Invitation > findAllInvitations();

	public List< Invitation > findByUserID( String userID );

}
