package ch.zhaw.sml.iwi.cis.pews.test.service.authentication;

import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.TokenAuthenticationResponse;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.AuthenticationTokenRestService}
 * - GENERATE_TOKEN
 * - AUTHENTICATE_WITH_TOKEN
 */

@RunWith( OrderedRunner.class ) public class AuthenticationTokenRestServiceTest
{
	private static String PASSWORD = "password";
	private static String LOGIN    = "authenticationtokentestuser";

	private static AuthenticationTokenService authenticationTokenService;

	private static UserImpl    user    = new UserImpl();
	private static SessionImpl session = new SessionImpl();

	@BeforeClass public static void setup()
	{
		// setup workshop for session
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		WorkshopTemplate workshopTemplate = workshopTemplateService.findWorkshopTemplateByID( workshopTemplateService.persist(
				new PinkElefantTemplate( null, "", "", "", "" ) ) );

		WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		WorkshopImpl workshop = workshopService.findWorkshopByID( workshopService.persist( new PinkElefantWorkshop( "",
				"",
				(PinkElefantTemplate)workshopTemplate ) ) );

		// workshop needs exercise, in order to assign currentExercise to session
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );
		PinkLabsTemplate pinkLabsTemplate = (PinkLabsTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
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
		ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
		exerciseService.persistExercise( new PinkLabsExercise( "", "", pinkLabsTemplate, workshop ) );

		// setup session
		SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
		session.setID( sessionService.persistSession( new SessionImpl( "",
				"",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				null,
				null ) ) );

		// setup user and join session
		RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
		RoleImpl role = roleService.findByID( roleService.persist( new RoleImpl( "authservicetestrole",
				"authservicetestrole" ) ) );
		UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		user.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( PASSWORD ),
				role,
				null,
				"",
				"",
				LOGIN ) ) );
		sessionService.join( new Invitation( null, user, session ) );

		// setup auth service
		authenticationTokenService = ServiceProxyManager.createServiceProxyWithUser( AuthenticationTokenServiceProxy.class,
				LOGIN,
				PASSWORD );
	}

	@TestOrder( order = 1 ) @Test public void testGenerateToken()
	{
		String token = authenticationTokenService.generateAuthenticationToken( "target" );
		assertTrue( token != null );
		assertTrue( !token.contains( "html" ) );
		assertTrue( !token.equals( "" ) );

		// verify token is replaced
		String resetToken = authenticationTokenService.generateAuthenticationToken( "target" );
		assertTrue( resetToken != null );
		assertTrue( !token.contains( "html" ) );
		assertTrue( !resetToken.equals( "" ) );
		assertTrue( !resetToken.equals( token ) );
	}

	@TestOrder( order = 2 ) @Test public void testAuthenticateWithToken()
	{
		String token = authenticationTokenService.generateAuthenticationToken( "target" );
		TokenAuthenticationResponse response = authenticationTokenService.authenticateWithToken( token );

		assertTrue( response.getTarget().equals( "target" ) );
		assertTrue( response.getSessionID().equals( session.getID() ) );
		assertTrue( response.getLogin().equals( LOGIN ) );
		assertTrue( response.getCredential().equals( PASSWORD ) );
	}
}
