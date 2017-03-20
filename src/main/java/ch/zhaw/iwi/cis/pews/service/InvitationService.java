package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InvitationService extends WorkshopObjectService
{

	public void accept( String invitationID );

	public Invitation findInvitationByID( String id );

	public List< Invitation > findAllInvitations();

	public List< Invitation > findByUserID( String userID );

	public List< Invitation > findBySessionID( String sessionID );

	public void sendByID( String invitationID );

	public void sendBySessionID( String sessionID );

	public void sendByWorkshopID( String workshopID );

}
