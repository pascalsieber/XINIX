package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.dao.*;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.util.MailService;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class InvitationServiceImpl extends WorkshopObjectServiceImpl implements InvitationService
{
	@Inject private InvitationDao invitationDao;
	@Inject private UserDao userDao;
	@Inject private SessionDao sessionDao;
	@Inject private MailService mailService;
	@Inject private WorkshopService workshopService;

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return invitationDao;
	}

	@Override
	public Invitation findInvitationByID( String id )
	{
		return (Invitation)simplifyOwnerInObjectGraph( findByID( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< Invitation > findAllInvitations()
	{
		return (List< Invitation >)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override
	public void accept( String invitationID )
	{
		Invitation invitation = findByID( invitationID );
		PrincipalImpl user = userDao.findById( invitation.getInvitee().getID() );
		SessionImpl session = sessionDao.findById( invitation.getSession().getID() );

		user.getSessionAcceptances().add( session );
		userDao.persist( user );

		remove( invitation );
	}

	@Override
	public List< Invitation > findByUserID( String userID )
	{
		return invitationDao.findByUserID( userID );
	}

	@Override
	public List< Invitation > findBySessionID( String sessionID )
	{
		return invitationDao.findBySessionID( sessionID );
	}

	@Override
	public void sendByID( String invitationID )
	{
		Invitation invitation = findByID( invitationID );
		SessionImpl session = sessionDao.findById( invitation.getSession().getID() );

		sendInvitation( invitation, session );
	}

	@Override
	public void sendBySessionID( String sessionID )
	{
		for ( Invitation invitation : findBySessionID( sessionID ) )
		{
			sendInvitation( invitation, invitation.getSession() );
		}
	}

	@Override
	public void sendByWorkshopID( String workshopID )
	{
		for ( SessionImpl session : workshopService.findWorkshopByID( workshopID ).getSessions() )
		{
			sendBySessionID( session.getID() );
		}
	}

	private void sendInvitation( Invitation invitation, SessionImpl session )
	{
		mailService.sendInvitation( invitation, ( (PinkElefantWorkshop)session.getWorkshop() ).getEmailText(), PewsConfig.getMailSubjectForInvitation(), PewsConfig.getMailUserName() );
	}
}
