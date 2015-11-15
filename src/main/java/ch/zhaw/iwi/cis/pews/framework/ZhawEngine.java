package ch.zhaw.iwi.cis.pews.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.derby.drda.NetworkServerControl;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.ClientServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseTemplateServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.InvitationServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.MediaServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.RoleServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.SessionServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopTemplateServiceImpl;
import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;
import ch.zhaw.iwi.cis.pews.service.xinix.impl.XinixImageMatrixServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Score;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationResultExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2POneExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PosterExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.SimplyPrototypingExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.You2MeExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.exwrapper.java.net.InetAddressWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.org.apache.derby.drda.NetworkServerControlWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.org.eclipse.jetty.server.ServerWrapper;

public class ZhawEngine implements LifecycleObject
{
	private static NetworkServerControl serverControl;
	private static Server webServer;
	private static ManagedObjectRegistry managedObjectRegistry;
	private static ZhawEngine zhawEngine;
	private static Client rootClient;
	private static UserImpl rootUser;

	public static String ROOT_CLIENT_ID;

	// defining these globally, since multiple pre-configured workshops might make use of the same role
	public static String ROOT_ROLE_ID;
	public static String PARTICIPANT_ROLE_ID;
	public static String EXECUTER_ROLE_ID;

	// defining this globally, since multiple pre-configured workshops might make use of the same xinix-image-matrix
	public static String XINIX_IMAGE_MATRIX_ID;
	public static List< MediaObject > XINIX_IMAGES;

	static
	{
		// This only needs to be done once, so am doing it here.
		Runtime.getRuntime().addShutdownHook( new ShutdownThread() );
	}

	public static void main( String[] args )
	{
		Logger.getLogger( ZhawEngine.class.getName() ).info( "Testing" );
		initProperties();

		getEngine().start();
	}

	public static void initProperties()
	{
		PewsConfig.loadProperties();
	}

	public static ZhawEngine getEngine()
	{
		if ( zhawEngine == null )
			zhawEngine = new ZhawEngine();

		return zhawEngine;
	}

	public void start()
	{
		startDatabase();

		setupEntityManager();
		startWebServer();
		configureRootUser();
		// configureSampleWorkshop();
		configureDemoWorkshop();
		// configurePostWorkshop();
		// configureSBBWorkshop();

		System.out.println( "PEWS running and ready to go!" );
	}

	private static void setupEntityManager()
	{
		ManagedObjectRegistry registry = ZhawEngine.getManagedObjectRegistry();
		registry.registerManagedObjectType( new EntityManagerFactoryLifecycleManager( "pews" ), "pewsFactory", Scope.CLASSLOADER );
		EntityManagerLifecycleManager lifecycleManager = new EntityManagerLifecycleManager( (EntityManagerFactory)registry.getManagedObject( "pewsFactory" ) );
		registry.registerManagedObjectType( lifecycleManager, "pews", Scope.THREAD );
	}

	public void stop()
	{
		stopWebServer();

		try
		{
			serverControl.ping();
			stopDatabase();
			getManagedObjectRegistry().stop();
		}
		catch ( Exception e )
		{}
	}

	public static ManagedObjectRegistry getManagedObjectRegistry()
	{
		if ( managedObjectRegistry == null )
			managedObjectRegistry = new ManagedObjectRegistryImpl();

		return managedObjectRegistry;
	}

	private static void startDatabase()
	{
		serverControl = NetworkServerControlWrapper.__new( InetAddressWrapper.getByName( "0.0.0.0" ), PewsConfig.getDerbyPort() );
		NetworkServerControlWrapper.start( serverControl, new PrintWriter( System.out ) );
	}

	private static void startWebServer()
	{
		webServer = new Server( new InetSocketAddress( "0.0.0.0", PewsConfig.getApplicationPort() ) );

		// Setup session ID manager.
		webServer.setSessionIdManager( new HashSessionIdManager() );

		HandlerCollection handlers = new HandlerCollection();
		handlers.addHandler( getSecurityHandler( getServletContextHandler() ) );

		handlers.addHandler( getRequestLogHandler() );
		handlers.addHandler( getResourceHandler() );
		webServer.setHandler( handlers );

		// Start the server.
		ServerWrapper.start( webServer );
	}

	private static ResourceHandler getResourceHandler()
	{
		ResourceHandler handler = new ResourceHandler();
		handler.setDirectoriesListed( true );
		handler.setWelcomeFiles( new String[] { "index.html" } );
		handler.setResourceBase( PewsConfig.getWebDir() );

		return handler;
	}

	private static ServletContextHandler getServletContextHandler()
	{
		// Setup servlet context handler
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages( "ch.zhaw.iwi.cis.pews.service.rest" );
		// resourceConfig.register( CustomObjectMapperProviderServer.class );
		ServletHolder holder = new ServletHolder( new ServletContainer( resourceConfig ) );
		holder.setInitParameter( "jersey.config.server.provider.classnames", LoggingFilter.class.getName() );
		holder.setInitOrder( 1 );
		holder.getRegistration().setMultipartConfig( new MultipartConfigElement( "data/tmp", 1048576, 1048576, 262144 ) );
		ServletContextHandler handler = new ServletContextHandler();
		handler.setContextPath( IdentifiableObjectRestService.SERVICES_BASE );
		handler.addServlet( holder, "/*" );
		handler.addFilter( ServletContextFilter.class, "/*", EnumSet.of( DispatcherType.INCLUDE, DispatcherType.REQUEST ) );

		// Setup session handler.
		HashSessionManager manager = new HashSessionManager();
		SessionHandler sessionHandler = new SessionHandler( manager );
		handler.setHandler( sessionHandler );

		return handler;
	}

	private static RequestLogHandler getRequestLogHandler()
	{
		NCSARequestLog requestLog = new NCSARequestLog( PewsConfig.getLogDir() + "/jetty-yyyy_mm_dd.request.log" );
		requestLog.setRetainDays( 90 );
		requestLog.setAppend( true );
		requestLog.setExtended( false );
		requestLog.setLogTimeZone( "GMT" );

		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog( requestLog );

		return requestLogHandler;
	}

	private static SecurityHandler getSecurityHandler( Handler delegateHandler )
	{
		// URL url = URIWrapper.toURL( new File( PewsConfig.getConfDir() + "/realm.properties" ).toURI() );

		// HashLoginService loginService = new HashLoginService( "PewsRealm", url.toString() );
		ZhawJDBCLoginService loginService = new ZhawJDBCLoginService();

		webServer.addBean( loginService );

		// secure all urls with basic auth
		Constraint constraint = new Constraint( Constraint.__BASIC_AUTH, "registered" );
		constraint.setAuthenticate( true );

		ConstraintMapping mapping = new ConstraintMapping();
		mapping.setPathSpec( "/pews/*" );
		mapping.setConstraint( constraint );

		// ping method and request new password go to whitelist
		Constraint whitelist = new Constraint();
		whitelist.setAuthenticate( false );

		ConstraintMapping pingMapping = new ConstraintMapping();
		pingMapping.setPathSpec( "/pews/global/ping" );
		pingMapping.setConstraint( whitelist );

		ConstraintMapping passwordHelpMapping = new ConstraintMapping();
		passwordHelpMapping.setPathSpec( "/pews/userService/user/requestPassword" );
		passwordHelpMapping.setConstraint( whitelist );

		List< ConstraintMapping > mappings = new ArrayList< ConstraintMapping >();
		mappings.add( mapping );
		mappings.add( pingMapping );
		mappings.add( passwordHelpMapping );

		ConstraintSecurityHandler handler = new ConstraintSecurityHandler();
		handler.setConstraintMappings( mappings );

		// handler.setConstraintMappings( Collections.singletonList( mapping ) );
		handler.setAuthenticator( new BasicAuthenticator() );
		handler.setLoginService( loginService );

		handler.setHandler( delegateHandler );

		return handler;
	}

	public static final String ROOT_CLIENT_NAME = "pews_root_client";
	public static final String ROOT_USER_LOGIN_NAME = ROOT_CLIENT_NAME + "/root@pews";

	private static void configureRootUser()
	{
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		ClientService clientService = getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );

		if ( rootClient == null )
		{
			rootClient = clientService.findByID( clientService.persist( new Client( ROOT_CLIENT_NAME ) ) );
			ROOT_CLIENT_ID = rootClient.getID();

			// Since the database is initialized by invoking the services directly
			// (rather than via HTTP/JSON) we have to setup a bootstrap user/client
			// context for the data to be created in. Since the user here is only
			// used to determine the client, the user data itself is left empty (null)
			// except for the client field.
			UserImpl bootstrapUser = new UserImpl( null, null, null, null, null, null );
			bootstrapUser.setClient( rootClient );
			UserContext.setCurrentUser( bootstrapUser );

			System.out.println( "root client registered initially" );
		}

		ROOT_ROLE_ID = roleService.persist( new RoleImpl( "root", "root" ) );

		UserImpl user = new UserImpl( new PasswordCredentialImpl( "root" ), (RoleImpl)roleService.findByID( ROOT_ROLE_ID ), null, "root first name", "root last name", ROOT_USER_LOGIN_NAME );
		String rootUserID = userService.persist( user );

		rootUser = userService.findByID( rootUserID );
		System.out.println( "root user registered initially" );

		// configure default roles
		roleService.persist( new RoleImpl( "organizer", "workshop organizer" ) );
		System.out.println( "organizer role created initially" );

		EXECUTER_ROLE_ID = roleService.persist( new RoleImpl( "executer", "session executer" ) );
		System.out.println( "executer role created initially" );

		PARTICIPANT_ROLE_ID = roleService.persist( new RoleImpl( "participant", "workshop participant" ) );
		System.out.println( "participant role created initially" );
	}

	@SuppressWarnings( "unused" )
	private static void configureSampleWorkshop()
	{
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		WorkshopTemplateService workshopTemplateService = getManagedObjectRegistry().getManagedObject( WorkshopTemplateServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseTemplateService exerciseTemplateService = getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		ExerciseDataService exerciseDataService = getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		InvitationService invitationService = getManagedObjectRegistry().getManagedObject( InvitationServiceImpl.class.getSimpleName() );
		XinixImageMatrixService xinixImageMatrixService = getManagedObjectRegistry().getManagedObject( XinixImageMatrixServiceImpl.class.getSimpleName() );
		MediaService mediaService = getManagedObjectRegistry().getManagedObject( MediaServiceImpl.class.getSimpleName() );

		// sample workshop template (pinkelefant)
		String wsTemplateID = workshopTemplateService.persist( new PinkElefantTemplate(
			rootUser,
			"p.i.n.k.elefant Template",
			"Template für p.i.n.k.elefant Workshop",
			"Produkteinfuehrung Teekocher",
			"Willkommensemail" ) );

		// sample workshop instance
		String wsID = workshopService.persist( new PinkElefantWorkshop( "p.i.n.k.elefant Workshop", "Beispiel eines p.i.n.k.elefant Workshops", (PinkElefantTemplate)workshopTemplateService
			.findByID( wsTemplateID ) ) );

		// workshop start (poster template)
		String startTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 180, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "Workshop Start", "Start des Workshops", "Willkommen zum p.i.n.k.elefant Workshop", "Der Workshop beginnt in Kürze." ) );

		// workshop end (poster template)
		String endTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 180, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "Workshop Ende", "Ende des Workshops", "Ende des p.i.n.k.elefant Workshops", "Vielen Dank für Ihre Teilnahme." ) );

		// pinklabs template
		String pinklabsTemplateID = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wo oder wie informierst Du Dich im Alltag?", "p.i.n.k.labs", "p.i.n.k.labs Tool" ) );

		// p2pOne template
		String p2poneTemplateID = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Benenne Eigenschaften oder Bedürfnisse der Anspruchsgruppe auf dem Bild.", "Post2Paper 1", "Post2Paper 1 Tool", PewsConfig.getImageDir() + "/business.jpg" ) );

		// p2ptwo template
		String p2pTwoTemplateID = exerciseTemplateService.persist( new P2PTwoTemplate( rootUser, true, TimeUnit.SECONDS, 180, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wie kann mit einer Person erfolgreich kommuniziert werden, welche die zwei ausgewählte Eigenschaften hat?", "Post2Paper 2", "Post2Paper 2 Tool" ) );

		// xinix image -> used for xinix template (as part of XinixImageMatrix)
		List< String > imageUrls = new ArrayList<>();
		for ( int i = 1; i < 37; i++ )
		{
			imageUrls.add( PewsConfig.getImageDir() + "/xinix_img_" + i + ".png" );
		}

		XINIX_IMAGES = new ArrayList< MediaObject >();

		for ( String url : imageUrls )
		{
			try
			{
				File tempFile = new File( "tempFile" );
				FileUtils.copyURLToFile( new URL( url ), tempFile );
				FileInputStream input = new FileInputStream( tempFile );
				XINIX_IMAGES.add( (MediaObject)mediaService.findByID( mediaService.persist( new MediaObject( "image/png", IOUtils.toByteArray( input ), MediaObjectType.XINIX ) ) ) );
			}
			catch ( IOException e )
			{
				throw new RuntimeException( "an error occured while configuring the XINIX images for sample workshop" );
			}

		}

		// xinix image matrix
		XINIX_IMAGE_MATRIX_ID = xinixImageMatrixService.persist( new XinixImageMatrix( XINIX_IMAGES ) );

		// xinix template
		String xinixTemplateID = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 60, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was fällt Dir ein zum Thema ENGAGEMENT?", "XINIX", "XINIX-Tool", (XinixImageMatrix)xinixImageMatrixService.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// you2me template
		String you2meTemplateID = exerciseTemplateService.persist( new You2MeTemplate( rootUser, true, TimeUnit.MINUTES, 2, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Führe mit jemandem einen Dialog.", "You2Me", "You2Me Tool", new HashSet<>( Arrays.asList(
			"Was möchtests Du gerne lernen?",
			"Wie kannst DU dies Deinem Gegenüber beibringen?" ) ) ) );

		// simply prototyping template
		String simplyprotoTemplateID = exerciseTemplateService.persist( new SimplyPrototypingTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			300,
			true,
			true,
			true,
			1,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"Mit welcher Aktion wird das Unternehmen weltberühmt?",
			"Simply Prototyping",
			"Simply Prototyping Tool",
			"mein mimetype" ) );

		// kompression template
		String compressionTemplateID = exerciseTemplateService.persist( new CompressionTemplate( rootUser, true, TimeUnit.MINUTES, 20, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Erarbeite Massnahmenvorschläge aufgrund des bisherigen Inputs.", "Kompression", "Kompression Tool", Arrays.asList(
			"Produkteigenschaften",
			"Werbung",
			"Vertrieb" ) ) );

		// evaluation template
		String evaluationTemplateID = exerciseTemplateService.persist( new EvaluationTemplate( rootUser, true, TimeUnit.SECONDS, 60, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wie bewertest Du diese Lösungen?", "Evaluation", "Evaluation Tool", 5 ) );

		// evaluation result template
		String evaluationResultTemplateID = exerciseTemplateService.persist( new EvaluationResultTemplate(
			rootUser,
			false,
			TimeUnit.MINUTES,
			10,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"Resultat der Abstimmung",
			"Auswertung der Evaluation",
			"Tool zur Auswertung der Evaluation" ) );

		// instantiate exercises based on templates
		exerciseService.persist( new PosterExercise( "Start", "p.i.n.k.elefant Start", (PosterTemplate)exerciseTemplateService.findByID( startTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		String pinklabsExID = exerciseService.persist( new PinkLabsExercise(
			"p.i.n.k.labs",
			"p.i.n.k.labs Tool",
			(PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		String p2poneExID = exerciseService.persist( new P2POneExercise( "Post2Paper 1", "Post2Paper 1 Tool", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String you2meExID = exerciseService.persist( new You2MeExercise( "You2me", "You2me Tool", (You2MeTemplate)exerciseTemplateService.findByID( you2meTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String p2ptwoExID = exerciseService.persist( new P2PTwoExercise( "Post2Paper 2", "Post2Paper 2 Tool", (P2PTwoTemplate)exerciseTemplateService.findByID( p2pTwoTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String xinixExID = exerciseService.persist( new XinixExercise( "XINIX", "XINIX-Tool", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String simplyPrototypingExID = exerciseService.persist( new SimplyPrototypingExercise( "Simply Prototyping", "Simply Prototyping Tool", (SimplyPrototypingTemplate)exerciseTemplateService
			.findByID( simplyprotoTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		String compressionExID = exerciseService.persist( new CompressionExercise(
			"Kompression",
			"Kompression Tool",
			(CompressionTemplate)exerciseTemplateService.findByID( compressionTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		String evaluationExID = exerciseService.persist( new EvaluationExercise(
			"Evaluation",
			"Evaluation Tool",
			(EvaluationTemplate)exerciseTemplateService.findByID( evaluationTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new EvaluationResultExercise( "Auswertung der Evaluation", "Tool zur Auswertung der Evaluation", (EvaluationResultTemplate)exerciseTemplateService
			.findByID( evaluationResultTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Ende", "p.i.n.k.elefant Workshop Ende", (PosterTemplate)exerciseTemplateService.findByID( endTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );

		// sample exercise data

		// pinklabs data
		exerciseDataService.persist( new PinkLabsExerciseData( rootUser, (WorkflowElementImpl)exerciseService.findByID( pinklabsExID ), Arrays.asList( "Internet", "Zeitungen" ) ) );

		// p2pone data
		String p2pOneDataID = exerciseDataService.persist( new P2POneData( rootUser, (WorkflowElementImpl)exerciseService.findByID( p2poneExID ), Arrays.asList( "Spass", "Spontan" ) ) );

		// you2me data
		exerciseDataService.persist( new You2MeExerciseData( rootUser, (WorkflowElementImpl)exerciseService.findByID( you2meExID ), Arrays.asList(
			new DialogEntry( DialogRole.RoleA, "Englisch" ),
			new DialogEntry( DialogRole.RoleB, "Nach Kalifornien in die Ferien gehen" ) ) ) );

		// p2ptwo data
		Set< P2POneKeyword > keywords = new HashSet<>();
		P2POneData p2pOneData = exerciseDataService.findByID( p2pOneDataID );
		keywords.add( p2pOneData.getKeywords().get( 0 ) );
		keywords.add( p2pOneData.getKeywords().get( 1 ) );

		exerciseDataService.persist( new P2PTwoData(
			rootUser,
			(WorkflowElementImpl)exerciseService.findByID( p2ptwoExID ),
			Arrays.asList( "Online Werbung", "Wettbewerb mit Kinogutscheinen" ),
			keywords ) );

		// xinix data
		Set< String > associations = new HashSet<>();
		associations.addAll( Arrays.asList( "Sicherheit", "Verbindung", "Zahlen" ) );
		exerciseDataService.persist( new XinixData( rootUser, (WorkflowElementImpl)exerciseService.findByID( xinixExID ), associations, XINIX_IMAGES.iterator().next() ) );

		// simpleprototyping data
		try
		{

			File file = new File( "file" );
			FileUtils.copyURLToFile( new URL( "http://91ef69bade70f992a001-b6054e05bb416c4c4b6f3b0ef3e0f71d.r93.cf3.rackcdn.com/laptop-with-business-chart-10032918.jpg" ), file );
			byte[] blob = IOUtils.toByteArray( new FileInputStream( file ) );
			String mediaObjectID = mediaService.persist( new MediaObject( "image/jpeg", blob, MediaObjectType.SIMPLYPROTOTYPING ) );

			exerciseDataService.persist( new SimplePrototypingData( rootUser, (WorkflowElementImpl)exerciseService.findByID( simplyPrototypingExID ), (MediaObject)mediaService
				.findByID( mediaObjectID ) ) );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "an error occured while configuring sample data for Simply Prorotyping" );
		}

		// compression data
		CompressionExerciseData compressionData = exerciseDataService.findByID( exerciseDataService.persist( new CompressionExerciseData( rootUser, (WorkflowElementImpl)exerciseService
			.findByID( compressionExID ), Arrays.asList(
			new CompressionExerciseDataElement( "Werbekampagne auf Youtube", "Werbekampagne auf Youtube wird unserem Produkt helfen." ),
			new CompressionExerciseDataElement( "Werbekampagne auf Facebook", "Werbekampagne auf Facebook wird unserem Produkt helfen." ) ) ) ) );

		// evaluation data
		exerciseDataService.persist( new EvaluationExerciseData( rootUser, (WorkflowElementImpl)exerciseService.findByID( evaluationExID ), new Evaluation( rootUser, compressionData
			.getSolutions()
			.get( 0 ), new Score( rootUser, 4 ) ) ) );

		// session
		String sessionID = sessionService.persist( new SessionImpl(
			"Beispiel Session",
			"Beispiel Session für p.i.n.k.elefant Workshop",
			null,
			SessionSynchronizationImpl.SYNCHRONOUS,
			(WorkshopImpl)workshopService.findByID( wsID ),
			null,
			null,
			null,
			null,
			null ) );

		// invitation (so that at least on is there) :)
		invitationService.persist( new Invitation( rootUser, rootUser, (SessionImpl)sessionService.findByID( sessionID ) ) );

		// user joins session (and by that all exercises in workshop)
		sessionService.join( new Invitation( null, rootUser, (SessionImpl)sessionService.findByID( sessionID ) ) );

		// configure second user to join session (as participant)
		String participantID = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating",
			"participant",
			"pews_root_client/participant@pews" ) );

		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// configure third user to join session (as participant)
		String participant2ID = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating 2",
			"participant 2",
			"pews_root_client/participant2@pews" ) );

		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participant2ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// configure second session with root user as executer

		// code for new user - not used
		// String executerID = userService.persist( new UserImpl(
		// new PasswordCredentialImpl( "root" ),
		// (RoleImpl)roleService.findByID( executerRoleID ),
		// null,
		// "root first name",
		// "root last name",
		// "pews_root_client/executer@pews" ) );
		String secondSessionID = sessionService.persist( new SessionImpl(
			"Zweite Beispiel Session",
			"Zweite Beispiel Session für p.i.n.k.elefant Workshop",
			null,
			SessionSynchronizationImpl.SYNCHRONOUS,
			(WorkshopImpl)workshopService.findByID( wsID ),
			null,
			null,
			null,
			null,
			null ) );
		sessionService.addExecuter( new Invitation( null, (UserImpl)userService.findByID( rootUser.getID() ), (SessionImpl)sessionService.findByID( secondSessionID ) ) );

		sessionService.start( sessionID );

		// add some invitations
		invitationService.persist( new Invitation( (UserImpl)userService.findByID( rootUser.getID() ), (UserImpl)userService.findByID( rootUser.getID() ), (SessionImpl)sessionService
			.findByID( sessionID ) ) );
		invitationService.persist( new Invitation( (UserImpl)userService.findByID( rootUser.getID() ), (UserImpl)userService.findByID( participant2ID ), (SessionImpl)sessionService
			.findByID( sessionID ) ) );
		invitationService.persist( new Invitation( (UserImpl)userService.findByID( rootUser.getID() ), (UserImpl)userService.findByID( participantID ), (SessionImpl)sessionService
			.findByID( sessionID ) ) );

		System.out.println( "sample workshop configured" );

	}

	public static final String DEMO_CLIENT_NAME = "demo";
	public static final String DEMO_ROOT_USER_LOGIN_NAME = "root";

	@SuppressWarnings( "unused" )
	private static void configureDemoWorkshop()
	{
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		WorkshopTemplateService workshopTemplateService = getManagedObjectRegistry().getManagedObject( WorkshopTemplateServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseTemplateService exerciseTemplateService = getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		ExerciseDataService exerciseDataService = getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		InvitationService invitationService = getManagedObjectRegistry().getManagedObject( InvitationServiceImpl.class.getSimpleName() );
		ClientService clientService = getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );
		XinixImageMatrixService xinixImageMatrixService = getManagedObjectRegistry().getManagedObject( XinixImageMatrixServiceImpl.class.getSimpleName() );
		MediaService mediaService = getManagedObjectRegistry().getManagedObject( MediaServiceImpl.class.getSimpleName() );

		// Demo client
		Client demoClient = clientService.findByID( clientService.persist( new Client( DEMO_CLIENT_NAME ) ) );

		UserImpl bootstrapUser = new UserImpl( null, null, null, null, null, null );
		bootstrapUser.setClient( demoClient );
		UserContext.setCurrentUser( bootstrapUser );

		// Post root role
		RoleImpl postRootRole = new RoleImpl( "root", "root" );
		postRootRole.setClient( demoClient );
		String postRootRoleID = roleService.persist( postRootRole );

		// Post root user
		UserImpl user = new UserImpl( new PasswordCredentialImpl( "root" ), (RoleImpl)roleService.findByID( postRootRoleID ), null, "root", "demo", DEMO_ROOT_USER_LOGIN_NAME );
		UserImpl demoRootUser = userService.findByID( userService.persist( user ) );

		// sample workshop template (pinkelefant)
		String wsTemplateID = workshopTemplateService.persist( new PinkElefantTemplate(
			rootUser,
			"p.i.n.k.elefant Demo",
			"Demo für p.i.n.k.elefant Workshop",
			"Wie können unsere Unternehmenswerte den Mitarbeitenden vermittelt werden?",
			"Willkommen!<br/>Sie sind eingeladen, an einem Workshop teilzunehmen<br/>Mit freundlichen Gruessen,<br/>p.i.n.k.elefant" ) );

		// sample workshop instance
		String wsID = workshopService.persist( new PinkElefantWorkshop( "p.i.n.k.elefant Workshop", "Demo p.i.n.k.elefant Workshops", (PinkElefantTemplate)workshopTemplateService
			.findByID( wsTemplateID ) ) );

		// workshop start (poster template)
		String startTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			180,
			false,
			false,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"",
			"Start",
			"p.i.n.k.elefant Start",
			"Willkommen zum p.i.n.k.elefant Workshop. Es soll die folgende Frage beantwortet werden: \"Wie können unsere Unternehmenswerte den Mitarbeitenden vermittelt werden?\"",
			"Der Workshop beginnt in Kürze." ) );

		// workshop end (poster template)
		String endTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 60, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "Ende", "p.i.n.k.elefant Workshop Ende", "Ende des p.i.n.k.elefant Workshops", "Vielen Dank für Ihre Teilnahme." ) );

		// pinklabs template
		String pinklabsTemplateID = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 60, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wo oder wie informierst Du Dich im Alltag?", "p.i.n.k.labs", "p.i.n.k.labs Tool" ) );

		// p2pOne template
		String p2poneTemplateID = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 60, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Benenne Eigenschaften oder Bedürfnisse der Anspruchsgruppe auf dem Bild.", "Post2Paper 1", "Post2Paper 1 Tool", PewsConfig.getImageDir() + "/business.jpg" ) );

		// p2ptwo template
		String p2pTwoTemplateID = exerciseTemplateService.persist( new P2PTwoTemplate( rootUser, true, TimeUnit.SECONDS, 60, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wie kann mit einer Person erfolgreich kommuniziert werden, welche die zwei ausgewählte Eigenschaften hat?", "Post2Paper 2", "Post2Paper 2 Tool" ) );

		// xinix image -> used for xinix template (as part of XinixImageMatrix)
		List< String > imageUrls = new ArrayList<>();
		for ( int i = 1; i < 37; i++ )
		{
			imageUrls.add( PewsConfig.getImageDir() + "/xinix_img_" + i + ".png" );
		}

		XINIX_IMAGES = new ArrayList< MediaObject >();

		for ( String url : imageUrls )
		{
			try
			{
				File tempFile = new File( "tempFile" );
				FileUtils.copyURLToFile( new URL( url ), tempFile );
				FileInputStream input = new FileInputStream( tempFile );
				XINIX_IMAGES.add( (MediaObject)mediaService.findByID( mediaService.persist( new MediaObject( "image/png", IOUtils.toByteArray( input ), MediaObjectType.XINIX ) ) ) );
			}
			catch ( IOException e )
			{
				throw new RuntimeException( "an error occured while configuring the XINIX images for Demo workshop" );
			}
		}

		// xinix image matrix
		XINIX_IMAGE_MATRIX_ID = xinixImageMatrixService.persist( new XinixImageMatrix( XINIX_IMAGES ) );

		// xinix template
		String xinixTemplateID = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 60, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findWorkshopTemplateByID( wsTemplateID ), "Was fällt Dir ein zum Thema ENGAGEMENT?", "XINIX", "XINIX-Tool", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// you2me template
		String you2meTemplateID = exerciseTemplateService.persist( new You2MeTemplate( rootUser, true, TimeUnit.MINUTES, 1, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findWorkshopTemplateByID( wsTemplateID ), "Führe mit jemandem einen Dialog.", "You2me", "You2me Tool", new HashSet< String >( Arrays.asList(
			"Was möchtests Du gerne lernen?",
			"Wie kannst DU dies Deinem Gegenüber beibringen?" ) ) ) );

		// simple prototyping template
		String simplyprotoTemplateID = exerciseTemplateService.persist( new SimplyPrototypingTemplate(
			rootUser,
			true,
			TimeUnit.SECONDS,
			60,
			true,
			false,
			false,
			1,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"Mit welcher Aktion wird das Unternehmen weltberühmt?",
			"Simply Prototyping",
			"Simply Prototyping Tool",
			"mein mimetype" ) );

		// kompression template
		String compressionTemplateID = exerciseTemplateService.persist( new CompressionTemplate( rootUser, true, TimeUnit.MINUTES, 1, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Erarbeite Massnahmenvorschläge aufgrund des bisherigen Inputs.", "Kompression", "Kompression Tool", Arrays.asList(
			"Produkteigenschaften",
			"Werbung",
			"Vertrieb" ) ) );

		// evaluation template
		String evaluationTemplateID = exerciseTemplateService.persist( new EvaluationTemplate( rootUser, true, TimeUnit.SECONDS, 60, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wie bewertest Du diese Lösungen?", "Evaluation", "Evaluation Tool", 5 ) );

		// evaluation result template
		String evaluationResultTemplateID = exerciseTemplateService.persist( new EvaluationResultTemplate(
			rootUser,
			false,
			TimeUnit.MINUTES,
			1,
			false,
			false,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"Resultat der Abstimmung",
			"Auswertung der Evaluation",
			"Tool zur Auswertung der Evaluation" ) );

		// instantiate exercises based on templates
		WorkshopImpl workshop = workshopService.findWorkshopByID( wsID );

		exerciseService.persist( new PosterExercise( "Start", "p.i.n.k.elefant Start", (PosterTemplate)exerciseTemplateService.findByID( startTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		String pinklabsExID = exerciseService.persist( new PinkLabsExercise(
			"p.i.n.k.labs",
			"p.i.n.k.labs Tool",
			(PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		String p2poneExID = exerciseService.persist( new P2POneExercise( "Post2Paper 1", "Post2Paper 1 Tool", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String you2meExID = exerciseService.persist( new You2MeExercise( "You2me", "You2me Tool", (You2MeTemplate)exerciseTemplateService.findByID( you2meTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String p2ptwoExID = exerciseService.persist( new P2PTwoExercise( "Post2Paper 2", "Post2Paper 2 Tool", (P2PTwoTemplate)exerciseTemplateService.findByID( p2pTwoTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String xinixExID = exerciseService.persist( new XinixExercise( "XINIX", "XINIX-Tool", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		String simplyPrototypingExID = exerciseService.persist( new SimplyPrototypingExercise( "Simply Prototyping", "Simply Prototyping Tool", (SimplyPrototypingTemplate)exerciseTemplateService
			.findByID( simplyprotoTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		String compressionExID = exerciseService.persist( new CompressionExercise(
			"Kompression",
			"Kompression Tool",
			(CompressionTemplate)exerciseTemplateService.findByID( compressionTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		String evaluationExID = exerciseService.persist( new EvaluationExercise(
			"Evaluation",
			"Evaluation Tool",
			(EvaluationTemplate)exerciseTemplateService.findByID( evaluationTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new EvaluationResultExercise( "Auswertung der Evaluation", "Tool zur Auswertung der Evaluation", (EvaluationResultTemplate)exerciseTemplateService
			.findByID( evaluationResultTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Ende", "p.i.n.k.elefant Workshop Ende", (PosterTemplate)exerciseTemplateService.findByID( endTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );

		// session
		String sessionID = sessionService.persist( new SessionImpl(
			"Demo Session",
			"Demo Session für p.i.n.k.elefant Workshop",
			null,
			SessionSynchronizationImpl.SYNCHRONOUS,
			(WorkshopImpl)workshopService.findByID( wsID ),
			null,
			null,
			null,
			null,
			null ) );

		// user joins session (and by that all exercises in workshop)
		sessionService.join( new Invitation( null, demoRootUser, (SessionImpl)sessionService.findByID( sessionID ) ) );

		// executor
		String executorID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( EXECUTER_ROLE_ID ), null, "executor", "@demo", "executor" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( executorID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// make second async session demo1
		String asyncSessionID1 = sessionService.persist( new SessionImpl(
			"Demo Session ASYNC",
			"Asynchrone Demo Session für p.i.n.k.elefant Workshop",
			null,
			SessionSynchronizationImpl.ASYNCHRONOUS,
			(WorkshopImpl)workshopService.findByID( wsID ),
			null,
			null,
			null,
			null,
			null ) );

		// demo1
		String demo1ID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 1", "@demo", "demo1" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( demo1ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// demo2
		String demo2ID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 2", "@demo", "demo2" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( demo2ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// demo3
		String demo3ID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 3", "@demo", "demo3" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( demo3ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// demo4
		String demo4ID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 4", "@demo", "demo4" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( demo4ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// demo5
		String demo5ID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 5", "@demo", "demo5" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( demo5ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// demo6
		String demo6ID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 6", "@demo", "demo6" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( demo6ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// async executor
		String asyncExecutorID = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc" ),
			(RoleImpl)roleService.findByID( EXECUTER_ROLE_ID ),
			null,
			"async executor",
			"@demo",
			"asyncexecutor" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( asyncExecutorID ), (SessionImpl)sessionService.findByID( asyncSessionID1 ) ) );

		// async user
		String asyncUserID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "participant 7", "@demo", "async" ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( asyncUserID ), (SessionImpl)sessionService.findByID( asyncSessionID1 ) ) );

		// invitations, in case we show user profile
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( executorID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		// invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( executorID ), (SessionImpl)sessionService.findByID( asyncSessionID ) ) );
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo1ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		// invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo1ID ), (SessionImpl)sessionService.findByID( asyncSessionID ) ) );
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo2ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		// invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo2ID ), (SessionImpl)sessionService.findByID( asyncSessionID ) ) );
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo3ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		// invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo3ID ), (SessionImpl)sessionService.findByID( asyncSessionID ) ) );
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo4ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		// invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo4ID ), (SessionImpl)sessionService.findByID( asyncSessionID ) ) );
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo5ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		// invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo5ID ), (SessionImpl)sessionService.findByID( asyncSessionID ) ) );

		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( demo6ID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( asyncUserID ), (SessionImpl)sessionService.findByID( asyncSessionID1 ) ) );
		invitationService.persist( new Invitation( demoRootUser, (UserImpl)userService.findByID( asyncExecutorID ), (SessionImpl)sessionService.findByID( asyncSessionID1 ) ) );

		sessionService.start( sessionID );
		sessionService.start( asyncSessionID1 );

		System.out.println( "demo workshop configured" );
	}

	public static final String POST_ROOT_CLIENT_NAME = "post";
	public static final String POST_ROOT_USER_LOGIN_NAME = POST_ROOT_CLIENT_NAME + "/root@post";

	@SuppressWarnings( "unused" )
	private static void configurePostWorkshop()
	{
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		ClientService clientService = getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );
		WorkshopTemplateService workshopTemplateService = getManagedObjectRegistry().getManagedObject( WorkshopTemplateServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseTemplateService exerciseTemplateService = getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		XinixImageMatrixService xinixImageMatrixService = getManagedObjectRegistry().getManagedObject( XinixImageMatrixServiceImpl.class.getSimpleName() );

		// Post client
		Client postClient = clientService.findByID( clientService.persist( new Client( POST_ROOT_CLIENT_NAME ) ) );

		UserImpl bootstrapUser = new UserImpl( null, null, null, null, null, null );
		bootstrapUser.setClient( postClient );
		UserContext.setCurrentUser( bootstrapUser );

		// Post root role
		RoleImpl postRootRole = new RoleImpl( "post_root", "post_root" );
		postRootRole.setClient( postClient );
		String postRootRoleID = roleService.persist( postRootRole );

		// Post root user
		UserImpl user = new UserImpl(
			new PasswordCredentialImpl( "root" ),
			(RoleImpl)roleService.findByID( postRootRoleID ),
			null,
			"post root first name",
			"post root last name",
			POST_ROOT_USER_LOGIN_NAME );
		UserImpl postRootUser = userService.findByID( userService.persist( user ) );

		// workshop template (pinkelefant)
		String wsTemplateID = workshopTemplateService.persist( new PinkElefantTemplate(
			postRootUser,
			"Post Workshop Template",
			"Template für p.i.n.k.elefant Workshop mit der Post",
			"Massnahmen Begleit-Service Paketdienst im Jahr 2020",
			"E-mail Text" ) );

		// workshop instance
		String wsID = workshopService
			.persist( new PinkElefantWorkshop( "Post Workshop", "p.i.n.k.elefant Workshop mit der Post", (PinkElefantTemplate)workshopTemplateService.findByID( wsTemplateID ) ) );

		// pinklabs template 1
		String pinklabsTemplateID1 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was macht dir Spass?", "p.i.n.k.labs (1/4)", "p.i.n.k.labs Tool 1" ) );

		// pinklabs template 2
		String pinklabsTemplateID2 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wie kann man dich überraschen?", "p.i.n.k.labs (2/4)", "p.i.n.k.labs Tool 2" ) );

		// pinklabs template 3
		String pinklabsTemplateID3 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Wofür gibst du gerne Geld aus?", "p.i.n.k.labs (3/4)", "p.i.n.k.labs Tool 3" ) );

		// pinklabs template 4
		String pinklabsTemplateID4 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was kostet dich zu viel Zeit?", "p.i.n.k.labs (4/4)", "p.i.n.k.labs Tool 4" ) );

		// p2p one iteration 1
		String p2poneTemplateID1 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?", "Post2Paper 1 (1/4)", "Post2Paper 1 Tool 1", PewsConfig.getImageDir()
				+ "/single.jpg" ) );

		// p2p one iteration 2
		String p2poneTemplateID2 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?", "Post2Paper 1 (2/4)", "Post2Paper 1 Tool 2", PewsConfig.getImageDir()
				+ "/empfangsdame.jpg" ) );

		// p2p one iteration 3
		String p2poneTemplateID3 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?", "Post2Paper 1 (3/4)", "Post2Paper 1 Tool 3", PewsConfig.getImageDir()
				+ "/hausfrau.jpg" ) );

		// p2p one iteration 4
		String p2poneTemplateID4 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?", "Post2Paper 1 (4/4)", "Post2Paper 1 Tool 4", PewsConfig.getImageDir()
				+ "/senioren.jpg" ) );

		// p2p two
		String p2pTwoTemplateID = exerciseTemplateService.persist( new P2PTwoTemplate( rootUser, true, TimeUnit.SECONDS, 180, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Formuliere je eine mögliche neue Dienstleistung, welche die Post anbieten könnte.", "Post2Paper 2", "Post2Paper 2 Tool" ) );

		// xinix template iteration 1
		String xinixTemplateID1 = exerciseTemplateService.persist( new XinixTemplate(
			rootUser,
			true,
			TimeUnit.SECONDS,
			180,
			false,
			false,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findByID( wsTemplateID ),
			"Was fällt dir ein zum Thema Kundenservice in Verbindung mit dem gewürfelten Bild?",
			"XINIX-Tool (1/4)",
			"XINIX-Tool 1",
			(XinixImageMatrix)xinixImageMatrixService.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix template iteration 2
		String xinixTemplateID2 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 180, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was fällt dir ein zum Thema Paket in Verbindung mit dem gewürfelten Bild?", "XINIX-Tool (2/4)", "XINIX-Tool 2", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix template iteration 3
		String xinixTemplateID3 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 180, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was fällt dir ein zum Thema Postbote in Verbindung mit dem gewürfelten Bild?", "XINIX-Tool (3/4)", "XINIX-Tool 3", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix template iteration 4
		String xinixTemplateID4 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 180, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was fällt dir ein zum Thema Jahr 2020 in Verbindung mit dem gewürfelten Bild?", "XINIX-Tool (4/4)", "XINIX-Tool 4", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// simply prototyping template
		String simplyprotoTemplateID = exerciseTemplateService.persist( new SimplyPrototypingTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			600,
			true,
			true,
			true,
			1,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"Zeichne oder Bastle den Postboten 2030!",
			"Simply Prototyping",
			"Simply Prototyping Tool",
			"tbd" ) );

		// kompression template
		String compressionTemplateID = exerciseTemplateService.persist( new CompressionTemplate(
			rootUser,
			true,
			TimeUnit.MINUTES,
			45,
			true,
			false,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"ALLE INPUTS aus Aufgaben 1-5 erscheinen auf dem Screen. Nun werden konkrete Massnahmen zum Thema \"Massnahmen Begleit-Service Paketdiesnst im Jahr 2020\" formuliert.",
			"Kompression",
			"Kompression Tool",
			new ArrayList< String >() ) );

		// evaluation template
		String evaluationTemplateID = exerciseTemplateService.persist( new EvaluationTemplate( rootUser, true, TimeUnit.SECONDS, 600, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Die einzelnen Ideen werden nun bewertet.", "Bewertungsrunde", "Tool zur Evaluation der erarbeiteten Lösungen", 5 ) );

		// evaluation result template
		String evaluationResultTemplateID = exerciseTemplateService.persist( new EvaluationResultTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			600,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"Resultat der Abstimmung",
			"Resultate der Bewertungsrunde",
			"Tool zur Auswertung der Resultate aus der Bewertungsrunde" ) );

		// end template (poster)
		String endTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 180, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "Abschluss", "Workshop End Tool", "Abschluss", "Vielen Dank, dass Sie an diesem XINIX-Workshop teilgenommen haben!" ) );

		// intro templates (posters)

		String introTemplateID1 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				120,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Begrüssung",
				"Workshop Start Tool",
				"Willkommen",
				"Herzlich Willkommen beim XINIX-Workshop zum Thema <b>Massnahmen Begleit-Service Paketdienst im Jahr 2020</b>.<br/>Bei diesem Wokshop kommen folgende Phasen auf dich zu:<br/><b>Inspirationsphase</b>: Hier werden möglichst viele Gedanken gesammelt, welche in der Kompressionsphase helfen sollen, konkrete Ideen zu generieren.<br/><b>Kompressionsphase</b>: Hier werden die Inspirationen miteinander kombiniert und konkrete Ideen ausformuliert.<br/><b>Bewertung</b>: Die Ideen werden entsprechend gewissen Kriterien bewertet." ) );

		String introTemplateID2 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				30,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Intro",
				"Intro Tool",
				"Inspirationsphase",
				"Du befindest dich jetzt in der <b>Inspirationsphase</b>. Hier erwarten dich 5 unterschiedliche Kreativitätstools. Wichtig bei all diesen Tools ist folgender Grundsatz: Ohne lange zu überlegen, schreib alles auf, was dir in den Sinn kommt. Ohne wenn und aber. Je mehr Antworten, desto besser." ) );

		String pinklabsIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 15, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "p.i.n.k.labs", "Beantworte die folgenden 4 unterschiedlichen Fragen.", "p.i.n.k.labs Intro", "p.i.n.k.labs Intro Tool" ) );

		String p2pOneIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			15,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findByID( wsTemplateID ),
			"",
			"Post2Paper 1 Intro",
			"Post2Paper 1 Intro Tool",
			"Post2Paper 1",
			"Du siehst nun nacheinander 4 unterschiedliche Zielgruppen. Beantworte zu jeder dieser Zielgruppen die folgende Frage." ) );

		String p2pTwoIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			15,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findByID( wsTemplateID ),
			"",
			"Post2Paper 2 Intro",
			"Post2Paper 2 Intro Tool",
			"Post2Paper 2",
			"Nimm jeweils 2 Antworten der letzten Aufgabe und beantworte die folgende Frage. Umschreibe die Dienstleistung mit 1-2 Sätzen." ) );

		String xinixIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			15,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"",
			"XINIX-Tool Intro",
			"XINIX-Tool Intro",
			"XINIX-Tool",
			"Nun folgen 4 unterschiedliche Themen. Bitte würfeln und das angezeigte Bild mit dem Thema verknüpfen. Pro Bild sind mehrere Antworten möglich. Du darfst beliebig oft würfeln. " ) );

		String simplyprotoIntroTemplateID = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				120,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Simply Prototyping Intro",
				"Simply Prototyping Intro Tool",
				"Simply Prototyping",
				"Beantworte die folgende Frage indem du zeichnest, bastelst oder schreibst. Nimm dafür ein A3-Blatt zur Hand.<br/>iPad: Das Resultat bitte direkt mit dem Tablet fotografieren und hochladen.<br/>PC & Laptop: Das Resultat bitte mit dem PC oder Laptop via QR Code hochladen." ) );

		String introTemplateID3 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				30,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Kompression Intro",
				"Kompression Intro Tool",
				"Kompressionsphase",
				"Du befindest dich jetzt in der <b>Kompressionsphase</b>. Nun kommen wir zurück auf unser Workshop-Thema: <b>Massnahmen Begleit-Service Paketdienst im Jahr 2020</b>. In den nächsten 45 Minuten geht es darum, konkrete Ideen dazu zu entwicklen. Gib jeder Massnahme einen Titel und beschreibe die Massnahme in mind. 4 Sätzen.<br/>Erarbeite so viele Massnahmen wie möglich.<br/>Wichtig: Lass dich von den Inspirationen, die auf dem Bildschirm erscheinen, anregen.<br/><br/>iPad: Die Inspirationen werden mit einem Zufallsgenerator aufgeführt. Drücke Random um den Zufallsgenerator auszulösen.<br/>PC & Laptop:  Alle Inspirationen sind den Aufgaben nach aufgelistet." ) );

		String introTemplateID4 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				30,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Bewertung Intro",
				"Bewertung Intro Tool",
				"Bewertungsphase",
				"Gratuliere. Du bist bald am Ende dieses XINIX-Workshops. Nun kommt die <b>Bewertungsphase</b>. Die Ideen aller Teilnehmenden von diesem Workshop erscheinen nun auf deinem Screen. Lies diese durch und bewerte sie unter Einbezug des Kriteriums 'Umsetzbarkeit bei der Post'.<br/>Bestimme deine 5 favorisierten Ideen und gewichte diese noch gemäss einer Skala von 1-10." ) );

		String outroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			120,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findByID( wsTemplateID ),
			"",
			"Outro Intro",
			"Outro Tool",
			"",
			"Gratulation, der XINIX-Workshop ist beendet. Die besten Ideen werden am kommenden Meeting vom xx.xx.2015 im Plenum besprochen und weiterentwickelt." ) );

		// exercise instances
		exerciseService
			.persist( new PosterExercise( "Begrüssung", "Workshop Start Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID1 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Intro", "Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID2 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "p.i.n.k.labs Intro", "p.i.n.k.labs Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( pinklabsIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (1/4)", "p.i.n.k.labs Tool 1", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID1 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (2/4)", "p.i.n.k.labs Tool 2", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID2 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (3/4)", "p.i.n.k.labs Tool 3", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID3 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (4/4)", "p.i.n.k.labs Tool 4", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID4 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Post2Paper 1 Intro", "Post2Paper 1 Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( p2pOneIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (1/4)", "Post2Paper 1 Tool 1", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID1 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (2/4)", "Post2Paper 1 Tool 2", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID2 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (3/4)", "Post2Paper 1 Tool 3", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID3 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (4/4)", "Post2Paper 1 Tool 4", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID4 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Post2Paper 2 Intro", "Post2Paper 2 Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( p2pTwoIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new P2PTwoExercise( "Post2Paper 2", "Post2Paper 2 Tool", (P2PTwoTemplate)exerciseTemplateService.findByID( p2pTwoTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "XINIX-Tool Intro", "XINIX-Tool Intro", (PosterTemplate)exerciseTemplateService.findByID( xinixIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (1/4)", "XINIX-Tool 1", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID1 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (2/4)", "XINIX-Tool 2", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID2 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (3/4)", "XINIX-Tool 3", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID3 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (4/4)", "XINIX-Tool 4", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID4 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise(
			"Simply Prototyping Intro",
			"Simply Prototyping Intro Tool",
			(PosterTemplate)exerciseTemplateService.findByID( simplyprotoIntroTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new SimplyPrototypingExercise(
			"Simply Prototyping",
			"Simply Prototyping Tool",
			(SimplyPrototypingTemplate)exerciseTemplateService.findByID( simplyprotoTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Kompression Intro", "Kompression Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID3 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new CompressionExercise( "Kompression", "Kompression Tool", (CompressionTemplate)exerciseTemplateService.findByID( compressionTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Bewertung Intro", "Bewertung Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID4 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new EvaluationExercise( "Bewertungsrunde", "Tool zur Evaluation der erarbeiteten Lösungen", (EvaluationTemplate)exerciseTemplateService
			.findByID( evaluationTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new EvaluationResultExercise(
			"Resultate der Bewertungsrunde",
			"Tool zur Auswertung der Resultate aus der Bewertungsrunde",
			(EvaluationResultTemplate)exerciseTemplateService.findByID( evaluationResultTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Outro Intro", "Outro Tool", (PosterTemplate)exerciseTemplateService.findByID( outroTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Abschluss", "Workshop End Tool", (PosterTemplate)exerciseTemplateService.findByID( endTemplateID ), workshopService.findWorkshopByID( wsID ) ) );

		// session
		String sessionID = sessionService.persist( new SessionImpl(
			"Beispiel Session",
			"Beispiel Session für p.i.n.k.elefant Workshop mit der Post",
			null,
			SessionSynchronizationImpl.SYNCHRONOUS,
			(WorkshopImpl)workshopService.findByID( wsID ),
			null,
			null,
			null,
			null,
			null ) );

		// configure participants to join session
		String participantID1 = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating",
			"participant 1",
			POST_ROOT_CLIENT_NAME + "/p1@post" ) );

		String participantID2 = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating",
			"participant 2",
			POST_ROOT_CLIENT_NAME + "/p2@post" ) );

		String participantID3 = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating",
			"participant 3",
			POST_ROOT_CLIENT_NAME + "/p3@post" ) );

		String participantID4 = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating",
			"participant 4",
			POST_ROOT_CLIENT_NAME + "/p4@post" ) );

		String participantID5 = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
			null,
			"participating",
			"participant 5",
			POST_ROOT_CLIENT_NAME + "/p5@post" ) );

		String executerID = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( EXECUTER_ROLE_ID ),
			null,
			"executing",
			"executer",
			POST_ROOT_CLIENT_NAME + "/e@post" ) );

		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID1 ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID2 ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID3 ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID4 ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID5 ), (SessionImpl)sessionService.findByID( sessionID ) ) );
		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( executerID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// start session
		sessionService.start( sessionID );

		System.out.println( "workshop for Post configured" );

	}

	public static final String SBB_ROOT_CLIENT_NAME = "sbb";
	public static final String SBB_ROOT_USER_LOGIN_NAME = POST_ROOT_CLIENT_NAME + "/root@sbb";

	@SuppressWarnings( "unused" )
	private static void configureSBBWorkshop()
	{
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		ClientService clientService = getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );
		WorkshopTemplateService workshopTemplateService = getManagedObjectRegistry().getManagedObject( WorkshopTemplateServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseTemplateService exerciseTemplateService = getManagedObjectRegistry().getManagedObject( ExerciseTemplateServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		XinixImageMatrixService xinixImageMatrixService = getManagedObjectRegistry().getManagedObject( XinixImageMatrixServiceImpl.class.getSimpleName() );
		// SBB client
		Client sbb = clientService.findByID( clientService.persist( new Client( SBB_ROOT_CLIENT_NAME ) ) );

		UserImpl bootstrapUser = new UserImpl( null, null, null, null, null, null );
		bootstrapUser.setClient( sbb );
		UserContext.setCurrentUser( bootstrapUser );

		// sbb root role
		RoleImpl sbbRootRole = new RoleImpl( "sbb_root", "sbb_root" );
		sbbRootRole.setClient( sbb );
		String sbbRootRoleID = roleService.persist( sbbRootRole );

		// sbb root user
		UserImpl user = new UserImpl(
			new PasswordCredentialImpl( "root" ),
			(RoleImpl)roleService.findByID( sbbRootRoleID ),
			null,
			"sbb root first name",
			"sbb root last name",
			SBB_ROOT_USER_LOGIN_NAME );
		UserImpl sbbRootUser = userService.findByID( userService.persist( user ) );

		// workshop template (pinkelefant)
		String wsTemplateID = workshopTemplateService.persist( new PinkElefantTemplate(
			sbbRootUser,
			"SBB Workshop Template",
			"Template für p.i.n.k.elefant Workshop mit der SBB",
			"Was wünsche ich mir am Bahnhof",
			"E-mail Text" ) );

		// workshop instance
		String wsID = workshopService
			.persist( new PinkElefantWorkshop( "SBB Workshop", "p.i.n.k.elefant Workshop mit der SBB", (PinkElefantTemplate)workshopTemplateService.findByID( wsTemplateID ) ) );

		// pinklabs template 1
		String pinklabsTemplateID1 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was mache ich alles online?", "p.i.n.k.labs (1/4)", "p.i.n.k.labs Tool 1" ) );

		// pinklabs template 2
		String pinklabsTemplateID2 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Das macht mir Spass?", "p.i.n.k.labs (2/4)", "p.i.n.k.labs Tool 2" ) );

		// pinklabs template 3
		String pinklabsTemplateID3 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Das spart mir Zeit?", "p.i.n.k.labs (3/4)", "p.i.n.k.labs Tool 3" ) );

		// pinklabs template 4
		String pinklabsTemplateID4 = exerciseTemplateService.persist( new PinkLabsTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was würde ich nie online kaufen?", "p.i.n.k.labs (4/4)", "p.i.n.k.labs Tool 4" ) );

		// p2p one iteration 1
		String p2poneTemplateID1 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)", "Post2Paper 1 (1/4)", "Post2Paper 1 Tool 1", PewsConfig.getImageDir()
				+ "/familie.jpg" ) );

		// p2p one iteration 2
		String p2poneTemplateID2 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)", "Post2Paper 1 (2/4)", "Post2Paper 1 Tool 2", PewsConfig.getImageDir()
				+ "/senioren.jpg" ) );

		// p2p one iteration 3
		String p2poneTemplateID3 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)", "Post2Paper 1 (3/4)", "Post2Paper 1 Tool 3", PewsConfig.getImageDir()
				+ "/business.jpg" ) );

		// p2p one iteration 4
		String p2poneTemplateID4 = exerciseTemplateService.persist( new P2POneTemplate( rootUser, true, TimeUnit.SECONDS, 120, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)", "Post2Paper 1 (4/4)", "Post2Paper 1 Tool 4", PewsConfig.getImageDir()
				+ "/schueler.jpg" ) );

		// p2p two
		String p2pTwoTemplateID = exerciseTemplateService.persist( new P2PTwoTemplate(
			rootUser,
			true,
			TimeUnit.SECONDS,
			240,
			true,
			false,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findByID( wsTemplateID ),
			"Formuliere eine mögliche neue Dienstleistung, welche dafür am Bahnhof angeboten werden könnte. Umschreibe die Dienstleistung mit 1-2 Sätzen.",
			"Post2Paper 2",
			"Post2Paper 2 Tool" ) );

		// xinix template iteration 1
		String xinixTemplateID1 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 90, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was bedeutet für mich \"Service\" in Verbindung mit dem gewürfelten Bild?", "XINIX-Tool (1/4)", "XINIX-Tool 1", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix template iteration 2
		String xinixTemplateID2 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 90, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Neue Dienstleistung am Bahnhof in Verbindung mit dem gewürfelten Bild.", "XINIX-Tool (2/4)", "XINIX-Tool 2", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix template iteration 3
		String xinixTemplateID3 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 90, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was erwartest du vom Bahnhof 2050 in Verbindung mit dem gewürfelten Bild?", "XINIX-Tool (3/4)", "XINIX-Tool 3", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix template iteration 4
		String xinixTemplateID4 = exerciseTemplateService.persist( new XinixTemplate( rootUser, true, TimeUnit.SECONDS, 90, false, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findWorkshopTemplateByID( wsTemplateID ), "Was erwartest du vom ÖV in Verbindung mit dem gewürfelten Bild?", "XINIX-Tool (4/4)", "XINIX-Tool 4", (XinixImageMatrix)xinixImageMatrixService
			.findXinixImageMatrixByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// simple prototyping template
		String simplyprotoTemplateID = exerciseTemplateService.persist( new SimplyPrototypingTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			480,
			true,
			true,
			true,
			1,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"So sieht mein optimaler Bahnhof aus",
			"Simply Prototyping",
			"Simply Prototyping Tool",
			"tbd" ) );

		// Kompression template
		String compressionTemplateID = exerciseTemplateService.persist( new CompressionTemplate( rootUser, true, TimeUnit.MINUTES, 12, true, false, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Was wünsche ich mir am Bahnhof?", "Kompression", "Kompression Tool", new ArrayList< String >() ) );

		// evaluation template
		String evaluationTemplateID = exerciseTemplateService.persist( new EvaluationTemplate( rootUser, true, TimeUnit.SECONDS, 480, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "Die einzelnen Ideen werden nun bewertet.", "Bewertungsrunde", "Tool zur Evaluation der erarbeiteten Lösungen", 5 ) );

		// poster templates

		String introTemplateID1 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				120,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Begrüssung",
				"Workshop Start Tool",
				"Willkommen",
				"Herzlich Willkommen beim XINIX-Workshop zum Thema <b>Was wünsche ich mir am Bahnhof?</b>.<br/>Bei diesem Wokshop kommen folgende Phasen auf dich zu:<br/><b>Inspirationsphase</b>: Hier werden möglichst viele Gedanken gesammelt, welche in der Kompressionsphase helfen sollen, konkrete Ideen zu generieren.<br/><b>Kompressionsphase</b>: Hier werden die Inspirationen miteinander kombiniert und konkrete Ideen ausformuliert.<br/><b>Bewertung</b>: Die Ideen werden entsprechend gewissen Kriterien bewertet.<br/>Bitte nimm dir ca. 45 Minuten Zeit, um diesen Workshop durchzuspielen." ) );

		String introTemplateID2 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				30,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Intro",
				"Intro Tool",
				"Inspirationsphase",
				"Du befindest dich jetzt in der <b>Inspirationsphase</b>. Hier erwarten dich 5 unterschiedliche Kreativitätstools. Wichtig bei all diesen Tools ist folgender Grundsatz: Ohne lange zu überlegen, schreib alles auf, was dir in den Sinn kommt. Ohne wenn und aber. Je mehr Antworten, desto besser." ) );

		String pinklabsIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 30, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "p.i.n.k.labs", "Beantworte die folgenden 4 unterschiedlichen Fragen.", "p.i.n.k.labs Intro", "p.i.n.k.labs Intro Tool" ) );

		String p2pOneIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			30,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findByID( wsTemplateID ),
			"",
			"Post2Paper 1 Intro",
			"Post2Paper 1 Intro Tool",
			"Post2Paper 1",
			"Du siehst nun nacheinander 4 unterschiedliche Zielgruppen. Beantworte zu jeder dieser Zielgruppen die folgende Frage." ) );

		String p2pTwoIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 30, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "Post2Paper 2", "Nimm jeweils 2 Antworten der letzten Aufgabe und beantworte die folgende Frage.", "Post2Paper 2 Intro", "Post2Paper 2 Intro Tool" ) );

		String xinixIntroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			15,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"",
			"XINIX-Tool Intro",
			"XINIX-Tool Intro",
			"XINIX-Tool",
			"Nun folgen 4 unterschiedliche Themen.<br/>Bitte würfeln und das angezeigte Bild mit dem Thema verknüpfen. Pro Bild sind mehrere Antworten möglich. Du darfst beliebig oft würfeln." ) );

		String simplyprotoIntroTemplateID = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				120,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Simply Prototyping Intro",
				"Simply Prototyping Intro Tool",
				"Simply Prototyping",
				"Beantworte die folgende Frage indem du zeichnest, bastelst oder schreibst. Nimm dafür ein A3-Blatt zur Hand.<br/>iPad: Das Resultat bitte direkt mit dem Tablet fotografieren und hochladen.<br/>PC & Laptop: Das Resultat bitte mit dem PC oder Laptop via QR Code hochladen." ) );

		String introTemplateID3 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				30,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Kompression Intro",
				"Kompression Intro Tool",
				"Kompressionsphase",
				"Du befindest dich jetzt in der <b>Kompressionsphase</b>. Nun kommen wir zurück auf unser Workshop-Thema: <b>Was wünsche ich mir am Bahnhof?</b>.<br/>In den nächsten 12 Minuten geht es darum, konkrete Ideen dazu zu entwicklen. Gib jeder Massnahme einen Titel und beschreibe die Massnahme<br/>Erarbeite so viele Massnahmen wie möglich.<br/>Wichtig: Lass dich von den Inspirationen, die auf dem Bildschirm erscheinen, anregen.<br/><br/>iPad: Die Inspirationen werden mit einem Zufallsgenerator aufgeführt. Drücke Random um den Zufallsgenerator auszulösen.<br/>PC & Laptop:  Alle Inspirationen sind den Aufgaben nach aufgelistet." ) );

		String introTemplateID4 = exerciseTemplateService
			.persist( new PosterTemplate(
				rootUser,
				false,
				TimeUnit.SECONDS,
				30,
				false,
				true,
				false,
				0,
				(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
				"",
				"Bewertung Intro",
				"Bewertung Intro Tool",
				"Bewertungsphase",
				"Gratuliere. Du bist bald am Ende dieses XINIX-Workshops. Nun kommt die <b>Bewertungsphase</b>. Die Ideen aller Teilnehmenden von diesem Workshop erscheinen nun auf deinem Screen. Lies diese durch und bewerte sie unter Einbezug des Kriteriums \"Umsetzbarkeit realistisch\".<br/>Bestimme deine 5 favorisierten Ideen und gewichte diese noch gemäss einer Skala von 1-10." ) );

		String outroTemplateID = exerciseTemplateService.persist( new PosterTemplate(
			rootUser,
			false,
			TimeUnit.SECONDS,
			120,
			false,
			true,
			false,
			0,
			(WorkshopTemplate)workshopTemplateService.findWorkshopTemplateByID( wsTemplateID ),
			"",
			"Outro Intro",
			"Outro Tool",
			"",
			"Gratulation, der XINIX-Workshop ist beendet. Die besten Ideen werden am 01.07.2015 im Rahmen eines kreativ.workshops besprochen und weiterentwickelt." ) );

		String endTemplateID = exerciseTemplateService.persist( new PosterTemplate( rootUser, false, TimeUnit.SECONDS, 180, false, true, false, 0, (WorkshopTemplate)workshopTemplateService
			.findByID( wsTemplateID ), "", "Abschluss", "Workshop Abschluss Tool", "Abschluss", "Vielen Dank, dass du an diesem XINIX-Workshop teilgenommen hast!" ) );

		// exercise instances
		exerciseService
			.persist( new PosterExercise( "Begrüssung", "Workshop Start Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID1 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Intro", "Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID2 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "p.i.n.k.labs Intro", "p.i.n.k.labs Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( pinklabsIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (1/4)", "p.i.n.k.labs Tool 1", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID1 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (2/4)", "p.i.n.k.labs Tool 2", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID2 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (3/4)", "p.i.n.k.labs Tool 3", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID3 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PinkLabsExercise( "p.i.n.k.labs (4/4)", "p.i.n.k.labs Tool 4", (PinkLabsTemplate)exerciseTemplateService.findByID( pinklabsTemplateID4 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Post2Paper 1 Intro", "Post2Paper 1 Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( p2pOneIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (1/4)", "Post2Paper 1 Tool 1", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID1 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (2/4)", "Post2Paper 1 Tool 2", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID2 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (3/4)", "Post2Paper 1 Tool 3", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID3 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new P2POneExercise( "Post2Paper 1 (4/4)", "Post2Paper 1 Tool 4", (P2POneTemplate)exerciseTemplateService.findByID( p2poneTemplateID4 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Post2Paper 2 Intro", "Post2Paper 2 Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( p2pTwoIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new P2PTwoExercise( "Post2Paper 2", "Post2Paper 2 Tool", (P2PTwoTemplate)exerciseTemplateService.findByID( p2pTwoTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "XINIX-Tool Intro", "XINIX-Tool Intro", (PosterTemplate)exerciseTemplateService.findByID( xinixIntroTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (1/4)", "XINIX-Tool 1", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID1 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (2/4)", "XINIX-Tool 2", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID2 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (3/4)", "XINIX-Tool 3", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID3 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new XinixExercise( "XINIX-Tool (4/4)", "XINIX-Tool 4", (XinixTemplate)exerciseTemplateService.findByID( xinixTemplateID4 ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise(
			"Simply Prototyping Intro",
			"Simply Prototyping Intro Tool",
			(PosterTemplate)exerciseTemplateService.findByID( simplyprotoIntroTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new SimplyPrototypingExercise(
			"Simply Prototyping",
			"Simply Prototyping Tool",
			(SimplyPrototypingTemplate)exerciseTemplateService.findByID( simplyprotoTemplateID ),
			workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Kompression Intro", "Kompression Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID3 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new CompressionExercise( "Kompression", "Kompression Tool", (CompressionTemplate)exerciseTemplateService.findByID( compressionTemplateID ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Bewertung Intro", "Bewertung Intro Tool", (PosterTemplate)exerciseTemplateService.findByID( introTemplateID4 ), workshopService
			.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new EvaluationExercise( "Bewertungsrunde", "Tool zur Evaluation der erarbeiteten Lösungen", (EvaluationTemplate)exerciseTemplateService
			.findByID( evaluationTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService.persist( new PosterExercise( "Outro Intro", "Outro Tool", (PosterTemplate)exerciseTemplateService.findByID( outroTemplateID ), workshopService.findWorkshopByID( wsID ) ) );
		exerciseService
			.persist( new PosterExercise( "Abschluss", "Workshop Abschluss Tool", (PosterTemplate)exerciseTemplateService.findByID( endTemplateID ), workshopService.findWorkshopByID( wsID ) ) );

		// sessions and participants
		// TODO: change theses to participants
		for ( int i = 0; i < 10; i++ )
		{
			String sessionID = sessionService.persist( new SessionImpl(
				"Session für Teilnehmer " + i,
				"Session für Teilnehmer " + i + " für p.i.n.k.elefant Workshop mit SBB",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				(WorkshopImpl)workshopService.findByID( wsID ),
				null,
				null,
				null,
				null,
				null ) );

			String participantID = userService.persist( new UserImpl(
				new PasswordCredentialImpl( "abc123" ),
				(RoleImpl)roleService.findByID( EXECUTER_ROLE_ID ),
				null,
				"teilnehmer",
				"teilnehmer " + i,
				SBB_ROOT_CLIENT_NAME + "/e" + i + "@sbb" ) );

			sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
			sessionService.start( sessionID );
		}

		for ( int i = 0; i < 2; i++ )
		{
			String sessionID = sessionService.persist( new SessionImpl(
				"Asynchrone Session für Teilnehmer " + i,
				"Asynchrone Session für Teilnehmer " + i + " für p.i.n.k.elefant Workshop mit SBB",
				null,
				SessionSynchronizationImpl.ASYNCHRONOUS,
				(WorkshopImpl)workshopService.findByID( wsID ),
				null,
				null,
				null,
				null,
				null ) );

			String participantID = userService.persist( new UserImpl( new PasswordCredentialImpl( "abc123" ), (RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ), null, "teilnehmer", "teilnehmer "
					+ i, SBB_ROOT_CLIENT_NAME + "/p" + i + "@sbb" ) );

			sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID ), (SessionImpl)sessionService.findByID( sessionID ) ) );
			sessionService.start( sessionID );
		}

		System.out.println( "workshop for SBB configured" );

	}

	private static void stopDatabase()
	{
		NetworkServerControlWrapper.shutdown( serverControl );
	}

	private static void stopWebServer()
	{
		ServerWrapper.stop( webServer );
	}

	public static Client getRootClient()
	{
		return rootClient;
	}
}
