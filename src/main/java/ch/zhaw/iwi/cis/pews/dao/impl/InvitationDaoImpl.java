package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.InvitationDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class InvitationDaoImpl extends WorkshopObjectDaoImpl implements InvitationDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return Invitation.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< Invitation > findByUserID( String userID )
	{
		return em
			.createQuery( "from Invitation as i LEFT JOIN FETCH i.inviter LEFT JOIN FETCH i.invitee as user LEFT JOIN FETCH i.session as s LEFT JOIN FETCH s.workshop as w where user.id = :_user_id" )
			.setParameter( "_user_id", userID )
			.getResultList();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< Invitation > findBySessionID( String sessionID )
	{
		return em
			.createQuery( "from Invitation as i LEFT JOIN FETCH i.inviter LEFT JOIN FETCH i.invitee as user LEFT JOIN FETCH i.session as s LEFT JOIN FETCH s.workshop as w where s.id = :_session_id" )
			.setParameter( "_session_id", sessionID )
			.getResultList();
	}
}
