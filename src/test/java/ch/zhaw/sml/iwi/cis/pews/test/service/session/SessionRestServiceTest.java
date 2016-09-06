package ch.zhaw.sml.iwi.cis.pews.test.service.session;

import ch.zhaw.iwi.cis.pews.model.instance.*;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests
 * {@link ch.zhaw.iwi.cis.pews.service.rest.SessionRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 * - START
 * - STOP
 * - RENEW
 * - JOIN
 * - LEAVE
 * - SET_NEXT_EXERCISE
 * - SET_CURRENT_EXERCISE
 * - GET_PREVIOUS_EXERCISE
 * - GET_CURRENT_EXERCISE_ID_WITH_OUTPUT
 */
@RunWith( OrderedRunner.class ) public class SessionRestServiceTest
{
	private static SessionService sessionService;
	private static UserService userService;
	private static ExerciseDataService exerciseDataService;

	private static WorkshopTemplate workshopTemplate = new PinkElefantTemplate();
	private static WorkshopImpl     workshop         = new PinkElefantWorkshop();

	private static ExerciseTemplate exerciseTemplate = new PinkLabsTemplate();
	private static ExerciseImpl     exerciseOne      = new PinkLabsExercise();
	private static ExerciseImpl     exerciseTwo      = new PinkLabsExercise();

	private static UserImpl user = new UserImpl();

	private static SessionImpl session = new SessionImpl();

	@BeforeClass public static void setup()
	{
		// service
		sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
		userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );
		ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );

		workshopTemplate.setID( workshopTemplateService.persist( new PinkElefantTemplate( null, "", "", "", "" ) ) );

		workshop.setID( workshopService.persist( new PinkElefantWorkshop( "",
				"",
				(PinkElefantTemplate)workshopTemplate ) ) );

		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				workshopTemplate,
				"",
				"",
				"" ) ) );

		exerciseOne.setID( exerciseService.persistExercise( new PinkLabsExercise( "",
				"",
				(PinkLabsTemplate)exerciseTemplate,
				workshop ) ) );
		exerciseTwo.setID( exerciseService.persistExercise( new PinkLabsExercise( "",
				"",
				(PinkLabsTemplate)exerciseTemplate,
				workshop ) ) );

		user.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( "password" ),
				null,
				null,
				"",
				"",
				"login" ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		session.setID( sessionService.persist( new SessionImpl( "sessionname",
				"sessiondescription",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				new HashSet<Participant>(),
				new HashSet<PrincipalImpl>(),
				new HashSet<Invitation>(),
				new HashSet<PrincipalImpl>() ) ) );
		assertTrue( session.getID() != null );
		assertTrue( !session.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		assertTrue( sessionService.findSessionByID( session.getID() ) != null );
		assertTrue( sessionService.findSessionByID( session.getID() ).getName().equals( "sessionname" ) );
		assertTrue( sessionService.findSessionByID( session.getID() ).getDescription().equals( "sessiondescription" ) );
		assertTrue( sessionService.findSessionByID( session.getID() ).getDerivedFrom() == null );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getSynchronization()
				.equals( SessionSynchronizationImpl.SYNCHRONOUS ) );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getWorkshop()
				.getID()
				.equals( workshop.getID() ) );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentExercise()
				.getID()
				.equals( exerciseOne.getID() ) );
		assertTrue( sessionService.findSessionByID( session.getID() ).getParticipants().isEmpty() );
		assertTrue( sessionService.findSessionByID( session.getID() ).getInvitations().isEmpty() );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentState()
				.equals( WorkflowElementStatusImpl.NEW.toString() ) );
	}

	@TestOrder( order = 3 ) @Test public void testStart()
	{
		sessionService.start( session.getID() );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentState()
				.equals( WorkflowElementStatusImpl.RUNNING.toString() ) );
	}

	@TestOrder( order = 4 ) @Test public void testStop()
	{
		sessionService.stop( session.getID() );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentState()
				.equals( WorkflowElementStatusImpl.TERMINATED.toString() ) );
	}

	@TestOrder( order = 5 ) @Test public void testRenew()
	{
		sessionService.renew( session.getID() );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentState()
				.equals( WorkflowElementStatusImpl.NEW.toString() ) );
	}

	@TestOrder( order = 6 ) @Test public void testJoin()
	{
		sessionService.join( new Invitation( null, user, session ) );
		assertTrue( !sessionService.findSessionByID( session.getID() ).getParticipants().isEmpty() );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getParticipants()
				.contains( userService.findUserByID( user.getID() ).getParticipation() ) );
	}

	@TestOrder( order = 7 ) @Test public void testLeave()
	{
		sessionService.leave( new Invitation( null, user, session ) );
		assertTrue( sessionService.findSessionByID( session.getID() ).getParticipants().isEmpty() );
	}

	@TestOrder( order = 8 ) @Test public void testSetNextExercise()
	{
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentExercise()
				.getID()
				.equals( exerciseOne.getID() ) );
		sessionService.setNextExercise( session.getID() );
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentExercise()
				.getID()
				.equals( exerciseTwo.getID() ) );
	}

	@TestOrder( order = 9 ) @Test public void testGetPreviousExercise()
	{
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentExercise()
				.getID()
				.equals( exerciseTwo.getID() ) );
		assertTrue( sessionService.getPreviousExercise( session.getID() ).getID().equals( exerciseOne.getID() ) );
	}

	@TestOrder( order = 10 ) @Test public void testSetCurrentExercise()
	{
		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentExercise()
				.getID()
				.equals( exerciseTwo.getID() ) );

		SessionImpl changed = new SessionImpl();
		changed.setID( session.getID() );
		changed.setCurrentExercise( exerciseOne );
		sessionService.setCurrentExercise( changed );

		assertTrue( sessionService.findSessionByID( session.getID() )
				.getCurrentExercise()
				.getID()
				.equals( exerciseOne.getID() ) );
	}

	@TestOrder( order = 11 ) @Test public void testGetCurrentExerciseIDWithOutput()
	{
		// NOTE: test for output is simple, as getOutput / setOutput is tested in-depth separately

		// join user and get service proxy for him to test this
		// reason: session is derived from user making the request
		SessionService proxy = ServiceProxyManager.createServiceProxyWithUser( SessionServiceProxy.class,
				"login",
				"password" );

		proxy.join( new Invitation( null, user, session ) );

		// first iteration (exercise one, no data)
		assertTrue( proxy.getCurrentExericseIDWithOutput().getCurrentExerciseID().equals( exerciseOne.getID() ) );
		assertTrue( proxy.getCurrentExericseIDWithOutput().getOutput().isEmpty() );

		// second iteration (exercise one, some data)
		exerciseDataService.persist( new PinkLabsExerciseData( user, exerciseOne, Arrays.asList( "", "" ) ) );
		assertTrue( proxy.getCurrentExericseIDWithOutput().getCurrentExerciseID().equals( exerciseOne.getID() ) );
		assertTrue( !proxy.getCurrentExericseIDWithOutput().getOutput().isEmpty() );

		// third iteration (exercise two, no data)
		proxy.setNextExercise( session.getID() );
		assertTrue( proxy.getCurrentExericseIDWithOutput().getCurrentExerciseID().equals( exerciseTwo.getID() ) );
		assertTrue( proxy.getCurrentExericseIDWithOutput().getOutput().isEmpty() );

		// fourth iteration (exercise two, some data)
		exerciseDataService.persist( new PinkLabsExerciseData( user, exerciseOne, Arrays.asList( "", "" ) ) );
		assertTrue( proxy.getCurrentExericseIDWithOutput().getCurrentExerciseID().equals( exerciseTwo.getID() ) );
		assertTrue( !proxy.getCurrentExericseIDWithOutput().getOutput().isEmpty() );

	}

	@TestOrder( order = 12 ) @Test public void testFindAll()
	{
		SessionImpl result = sessionService.findSessionByID( session.getID() );
		assertTrue( result != null );
		assertTrue( sessionService.findAllSessions().contains( result ) );
	}

	@TestOrder( order = 13 ) @Test public void testRemove()
	{
		// ensure that not already removed
		SessionImpl result = sessionService.findSessionByID( session.getID() );
		assertTrue( result != null );
		assertTrue( sessionService.findAllSessions().contains( result ) );

		// remove and check
		sessionService.remove( session );
		SessionImpl removed = sessionService.findSessionByID( session.getID() );
		assertTrue( removed == null );
		assertTrue( !sessionService.findAllSessions().contains( result ) );
	}
}
