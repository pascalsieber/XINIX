package ch.zhaw.sml.iwi.cis.pews.test.service.invitation;

import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

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
@RunWith( OrderedRunner.class ) public class InvitationRestServiceTest
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

		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );
		ExerciseTemplate exerciseTemplate = exerciseTemplateService.findExerciseTemplateByID( exerciseTemplateService.persistExerciseTemplate(
				new PinkLabsTemplate( null, false, null, 0, false, false, false, 0, workshopTemplate, "", "", "" ) ) );

		workshop.setID( workshopService.persist( new PinkElefantWorkshop( "",
				"",
				(PinkElefantTemplate)workshopTemplate ) ) );

		ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
		exerciseService.persistExercise( new PinkLabsExercise( "", "", (PinkLabsTemplate)exerciseTemplate, workshop ) );

		session.setID( sessionService.persistSession( new SessionImpl( "",
				"",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				null,
				null ) ) );

		// setup user
		user.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( "" ), null, null, "", "", "" ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		invitation.setID( invitationService.persist( new Invitation( user, user, session ) ) );
		assertTrue( invitation.getID() != null );
		assertTrue( !invitation.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		Invitation found = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( invitation.getID() ) );
		assertTrue( found.getInviter().getID().equals( user.getID() ) );
		assertTrue( found.getInvitee().getID().equals( user.getID() ) );
		assertTrue( found.getSession().getID().equals( session.getID() ) );
	}

	@TestOrder( order = 4 ) @Test public void testFindBySessionID()
	{
		Invitation findable = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( findable != null );
		assertTrue( TestUtil.extractIds( invitationService.findBySessionID( session.getID() ) )
				.contains( findable.getID() ) );
	}

	@Ignore( "minimize e-mail traffic on mail server" ) @TestOrder( order = 5 ) @Test public void testSendByID()
	{
		// not checking email, just if API call runs through
		invitationService.sendByID( invitation.getID() );
	}

	@Ignore( "minimize e-mail traffic on mail server" ) @TestOrder( order = 6 ) @Test public void testSendBySessionID()
	{
		// not checking email, just if API call runs through
		invitationService.sendBySessionID( session.getID() );
	}

	@Ignore( "minimize e-mail traffic on mail server" ) @TestOrder( order = 7 ) @Test public void testSendByWorkshopID()
	{
		// not checking email, just if API call runs through
		invitationService.sendByWorkshopID( workshop.getID() );
	}

	@TestOrder( order = 8 ) @Test public void testFindAll()
	{
		Invitation findable = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( TestUtil.extractIds( invitationService.findAllInvitations() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 9 ) @Test public void testRemove()
	{
		Invitation removable = invitationService.findInvitationByID( invitation.getID() );
		assertTrue( TestUtil.extractIds( invitationService.findAllInvitations() ).contains( removable.getID() ) );

		invitationService.remove( removable );
		assertTrue( invitationService.findInvitationByID( invitation.getID() ) == null );
		assertTrue( !TestUtil.extractIds( invitationService.findAllInvitations() ).contains( removable.getID() ) );
	}
}
