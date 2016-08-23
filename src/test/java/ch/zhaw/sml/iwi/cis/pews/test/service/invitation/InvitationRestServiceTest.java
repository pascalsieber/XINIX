package ch.zhaw.sml.iwi.cis.pews.test.service.invitation;

import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.InvitationRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - ACCEPT
 * - FIND_BY_SESSION_ID
 * - SEND_BY_ID
 * - SEND_BY_SESSION_ID
 * - SEND_BY_WORKSHOP_ID
 * - REMOVE
 * - FIND_ALL
 */
public class InvitationRestServiceTest
{

	private static InvitationService invitationService;
	private static SessionService    sessionService;
	private static UserService       userService;

	private static Invitation invitation = new Invitation();
	private static UserImpl user         = new UserImpl();
	private static WorkshopImpl workshop = new PinkElefantWorkshop();
	private static SessionImpl session   = new SessionImpl();

	@BeforeClass public static void setup()
	{
		// services
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
		userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		invitationService = ServiceProxyManager.createServiceProxy( InvitationServiceProxy.class );

		// setup workshop with session
		WorkshopTemplate workshopTemplate = workshopTemplateService.findWorkshopTemplateByID( workshopTemplateService.persist(
				new PinkElefantTemplate( null, "", "", "", "" ) ) );

		workshop.setID( workshopService.persist( new PinkElefantWorkshop( "",
				"",
				(PinkElefantTemplate)workshopTemplate ) ) );

		session.setID( sessionService.persistSession( new SessionImpl( "",
				"",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				new HashSet<Participant>(),
				new HashSet<PrincipalImpl>(),
				new HashSet<Invitation>(),
				new HashSet<PrincipalImpl>() ) ) );

		// setup user
		user.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( "" ), null, null, "", "", "" ) ) );
	}

	@Test public void testPersist()
	{
		invitation.setID( invitationService.persist( new Invitation( user, user, session ) ) );
		assertTrue( invitation.getID() != null );
		assertTrue( !invitation.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		Invitation found = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( invitation.getID() ) );
		assertTrue( found.getInviter().getID().equals( user.getID() ) );
		assertTrue( found.getInvitee().getID().equals( user.getID() ) );
		assertTrue( found.getSession().getID().equals( session.getID() ) );
	}

	@Test public void testAccept()
	{
		assertTrue( userService.findUserByID( user.getID() ).getParticipation() == null );
		invitationService.accept( invitation.getID() );

		Participant participant = userService.findUserByID( user.getID() ).getParticipation();
		assertTrue( participant != null );
		assertTrue( participant.getPrincipal().getID().equals( user.getID() ) );
		assertTrue( participant.getSession().getID().equals( session.getID() ) );
		assertTrue( sessionService.findSessionByID( session.getID() ).getParticipants().contains( participant ) );
	}

	@Test public void testFindBySessionID()
	{
		Invitation findable = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( findable != null );
		assertTrue( invitationService.findBySessionID( session.getID() ).contains( findable ) );
	}

	@Test public void testSendByID()
	{
		// not checking email, just if API call runs through
		invitationService.sendByID( invitation.getID() );
	}

	@Test public void testSendBySessionID()
	{
		// not checking email, just if API call runs through
		invitationService.sendBySessionID( session.getID() );
	}

	@Test public void testSendByWorkshopID()
	{
		// not checking email, just if API call runs through
		invitationService.sendByWorkshopID( workshop.getID() );
	}

	@Test public void testFindAll()
	{
		Invitation findable = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( invitationService.findAllInvitations().contains( findable ) );
	}

	@Test public void testRemove()
	{
		Invitation removable = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( invitationService.findAllInvitations().contains( removable ) );

		invitationService.remove( removable );
		assertTrue( invitationService.findInvitationByID( invitation.getID() ) == null );
		assertTrue( !invitationService.findAllInvitations().contains( removable ) );
	}
}
