package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.InvitationDao;
import ch.zhaw.iwi.cis.pews.dao.InvitationDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.InvitationService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class InvitationServiceImpl extends IdentifiableObjectServiceImpl implements InvitationService
{
	private InvitationDao invitationDao;
	
	public InvitationServiceImpl()
	{
		invitationDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( InvitationDaoImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return invitationDao;
	}

	@Override
	public void accept( int invitationID )
	{
		Invitation invitation = findByID( invitationID );
		PrincipalImpl user = findByID( invitation.getInvitee().getID() );
		SessionImpl session = findByID( invitation.getSession().getID() );
		
		user.setSession( session );
		persist( user );
		
		session.getAcceptees().add( user );
		persist( session );
		
		remove( invitation );
	}
	
	
	
	
}
