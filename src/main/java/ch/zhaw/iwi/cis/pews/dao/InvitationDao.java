package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.user.Invitation;

public interface InvitationDao extends WorkshopObjectDao
{

	public List< Invitation > findByUserID( String userID );

	public List< Invitation > findBySessionID( String sessionID );
}
