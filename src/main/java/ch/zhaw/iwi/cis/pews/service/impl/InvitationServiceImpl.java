package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.dao.InvitationDao;
import ch.zhaw.iwi.cis.pews.dao.SessionDao;
import ch.zhaw.iwi.cis.pews.dao.UserDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.InvitationDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.SessionDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.UserDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.util.MailService;
import ch.zhaw.iwi.cis.pews.service.util.impl.MailServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;

import java.util.List;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class InvitationServiceImpl extends WorkshopObjectServiceImpl implements InvitationService
{
	private InvitationDao   invitationDao;
	private UserDao         userDao;
	private SessionDao      sessionDao;
	private MailService     mailService;
	private WorkshopService workshopService;

	public InvitationServiceImpl()
	{
		invitationDao = ZhawEngine.getManagedObjectRegistry()
				.getManagedObject( InvitationDaoImpl.class.getSimpleName() );
		userDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserDaoImpl.class.getSimpleName() );
		sessionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionDaoImpl.class.getSimpleName() );
		mailService = ZhawEngine.getManagedObjectRegistry().getManagedObject( MailServiceImpl.class.getSimpleName() );
		workshopService = ZhawEngine.getManagedObjectRegistry()
				.getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
	}

	@Override protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return invitationDao;
	}

	@Override public Invitation findInvitationByID( String id )
	{
		return (Invitation)simplifyOwnerInObjectGraph( findByID( id ) );
	}

	@SuppressWarnings( "unchecked" ) @Override public List<Invitation> findAllInvitations()
	{
		return (List<Invitation>)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override public void accept( String invitationID )
	{
		Invitation invitation = findByID( invitationID );
		PrincipalImpl user = userDao.findById( invitation.getInvitee().getID() );
		SessionImpl session = sessionDao.findById( invitation.getSession().getID() );

		user.getSessionAcceptances().add( session );
		userDao.persist( user );

		remove( invitation );
	}

	@Override public List<Invitation> findByUserID( String userID )
	{
		return invitationDao.findByUserID( userID );
	}

	@Override public List<Invitation> findBySessionID( String sessionID )
	{
		List<Invitation> results = invitationDao.findBySessionID( sessionID );

		// simplify session, invitee and inviter objects
		for ( Invitation invitation : results )
		{
			SessionImpl session = new SessionImpl();
			session.setID( invitation.getSession().getID() );
			invitation.setSession( session );

			UserImpl inviter = new UserImpl();
			inviter.setID( invitation.getInviter().getID() );
			inviter.setLoginName( ( (UserImpl)invitation.getInviter() ).getLoginName() );
			invitation.setInviter( inviter );

			UserImpl invitee = new UserImpl();
			invitee.setID( invitation.getInvitee().getID() );
			invitee.setLoginName( ( (UserImpl)invitation.getInvitee() ).getLoginName() );
			invitation.setInvitee( invitee );
		}

		return results;
	}

	@Override public void sendByID( String invitationID )
	{
		Invitation invitation = findByID( invitationID );
		SessionImpl session = sessionDao.findById( invitation.getSession().getID() );

		sendInvitation( invitation, session );
	}

	@Override public void sendBySessionID( String sessionID )
	{
		for ( Invitation invitation : findBySessionID( sessionID ) )
		{
			sendInvitation( invitation, invitation.getSession() );
		}
	}

	@Override public void sendByWorkshopID( String workshopID )
	{
		for ( SessionImpl session : workshopService.findWorkshopByID( workshopID ).getSessions() )
		{
			sendBySessionID( session.getID() );
		}
	}

	private void sendInvitation( Invitation invitation, SessionImpl session )
	{
		mailService.sendInvitation(
				invitation,
				( (PinkElefantWorkshop)session.getWorkshop() ).getEmailText(),
				PewsConfig.getMailSubjectForInvitation(),
				PewsConfig.getMailUserName() );
	}
}
