package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ParticipantDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ParticipantDaoImpl extends WorkshopObjectDaoImpl implements ParticipantDao
{

	@Override
	@SuppressWarnings( "unchecked" )
	public Participant findByPrincipalIDandSessionID( String principalID, String sessionID )
	{
		List< Participant > results = getEntityManager()
			.createQuery( "select participant FROM Participant participant WHERE participant.session.id = :session_id and participant.principal.id =:principal_id" )
			.setParameter( "session_id", sessionID )
			.setParameter( "principal_id", principalID )
			.getResultList();

		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			throw new UnsupportedOperationException( "no user with ID=" + principalID + " found for session with ID=" + sessionID );
		}
	}

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return Participant.class;
	}

}
