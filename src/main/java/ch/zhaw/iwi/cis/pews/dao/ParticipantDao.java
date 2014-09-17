package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.instance.Participant;

public interface ParticipantDao extends WorkshopObjectDao
{
	public Participant findByPrincipalIDandSessionID( String principalID, String sessionID );
}
