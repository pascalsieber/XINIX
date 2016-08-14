package ch.zhaw.sml.iwi.cis.pews.test.service.authentication;

import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.TokenAuthenticationResponse;
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
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.AuthenticationTokenRestService}
 * - GENERATE_TOKEN
 * - AUTHENTICATE_WITH_TOKEN
 */

public class AuthenticationTokenRestServiceTest
{
	private static String PASSWORD = "password";
	private static String LOGIN    = "login";

	private AuthenticationTokenService authenticationTokenService;

	private UserImpl    user    = new UserImpl();
	private SessionImpl session = new SessionImpl();

	@BeforeClass public void setup()
	{
		// setup session for user
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		WorkshopTemplate workshopTemplate = workshopTemplateService.findWorkshopTemplateByID( workshopTemplateService.persist(
				new PinkElefantTemplate( null, "", "", "", "" ) ) );

		WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		WorkshopImpl workshop = workshopService.findWorkshopByID( workshopService.persist( new PinkElefantWorkshop( "",
				"",
				(PinkElefantTemplate)workshopTemplate ) ) );

		SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );
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
		UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		user.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( PASSWORD ),
				null,
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

	@Test public void testGenerateToken()
	{
		String token = authenticationTokenService.generateAuthenticationToken( "target" );
		assertTrue( token != null );
		assertTrue( !token.equals( "" ) );

		// verify token is replaced
		String resetToken = authenticationTokenService.generateAuthenticationToken( "target" );
		assertTrue( resetToken != null );
		assertTrue( !resetToken.equals( "" ) );
		assertTrue( !resetToken.equals( token ) );
	}

	@Test public void testAuthenticateWithToken()
	{
		String token = authenticationTokenService.generateAuthenticationToken( "target" );
		TokenAuthenticationResponse response = authenticationTokenService.authenticateWithToken( token );

		assertTrue( response.getTarget().equals( "target" ) );
		assertTrue( response.getSessionID().equals( session.getID() ) );
		assertTrue( response.getLogin().equals( LOGIN ) );
		assertTrue( response.getCredential().equals( PASSWORD ) );
	}
}
