package ch.zhaw.iwi.cis.pews.framework;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
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
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopDefinitionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.ClientServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDefinitionServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.InvitationServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.RoleServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.SessionServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopDefinitionServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopServiceImpl;
import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.SimplePrototypingDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixImageMatrix;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.You2MeDefinition;
import ch.zhaw.iwi.cis.pinkelefant.workshop.definition.PinkElefantDefinition;
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

	static
	{
		// This only needs to be done once, so am doing it here.
		Runtime.getRuntime().addShutdownHook( new ShutdownThread() );
	}

	public static void main( String[] args )
	{
		Logger.getLogger( ZhawEngine.class.getName() ).info( "Testing" );

		getEngine().start();
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
		configureSampleWorkshop();
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
		serverControl = NetworkServerControlWrapper.__new( InetAddressWrapper.getByName( "0.0.0.0" ), 1527 );
		NetworkServerControlWrapper.start( serverControl, new PrintWriter( System.out ) );
	}

	private static void startWebServer()
	{
		webServer = new Server( new InetSocketAddress( "0.0.0.0", 8082 ) );

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
		mapping.setPathSpec( "/*" );
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

		String roleID = roleService.persist( new RoleImpl( "root", "root" ) );

		UserImpl user = new UserImpl( new PasswordCredentialImpl( "root" ), (RoleImpl)roleService.findByID( roleID ), null, "root first name", "root last name", ROOT_USER_LOGIN_NAME );
		String rootUserID = userService.persist( user );

		rootUser = userService.findByID( rootUserID );
		System.out.println( "root user registered initially" );

	}

	private static void configureSampleWorkshop()
	{
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		WorkshopDefinitionService workshopDefinitionService = getManagedObjectRegistry().getManagedObject( WorkshopDefinitionServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseDefinitionService exerciseDefinitionService = getManagedObjectRegistry().getManagedObject( ExerciseDefinitionServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		ExerciseDataService exerciseDataService = getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		InvitationService invitationService = getManagedObjectRegistry().getManagedObject( InvitationServiceImpl.class.getSimpleName() );

		// configure default roles
		roleService.persist( new RoleImpl( "organizer", "workshop organizer" ) );
		System.out.println( "organizer role created initially" );

		String executerRoleID = roleService.persist( new RoleImpl( "executer", "session executer" ) );
		System.out.println( "executer role created initially" );

		String participantRoleID = roleService.persist( new RoleImpl( "participant", "workshop participant" ) );
		System.out.println( "participant role created initially" );

		// sample workshop definition (pinkelefant)
		String wsDefID = workshopDefinitionService.persist( new PinkElefantDefinition( rootUser, "PinkElefant Definition", "Definition fuer Pinkelefant Workshop", "Produkteinfuehrung Teekocher" ) );

		// sample workshop instance
		String wsID = workshopService.persist( new WorkshopImpl( "PinkElefant Workshop", "Beispiel eines PinkElefant Workshops", (WorkflowElementDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ) ) );

		// pinklabs definition
		String pinklabsDefID = exerciseDefinitionService.persist( new PinkLabsDefinition(
			rootUser,
			TimeUnit.SECONDS,
			120,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Wo oder wie informierst Du Dich im Alltag?" ) );

		// p2pOne definition
		String p2poneDefID = exerciseDefinitionService.persist( new P2POneDefinition(
			rootUser,
			TimeUnit.SECONDS,
			120,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://oncampusadvertising.com/blog/wp-content/uploads/2014/11/college-students-using-smartphones-and-tablets.jpg",
			"Benenne EIGENSCHAFTEN oder BEDUERFNISSE der Anspruchsgruppe auf dem Bild" ) );

		// p2ptwo definition
		String p2ptwoDefID = exerciseDefinitionService.persist( new P2PTwoDefinition(
			rootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Wie kann einer Person erfolgreich kommuniziert werden, welche zwei ausgewaeshlte Eigenschaften hat?" ) );

		// xinix image -> used for xinix definition (as part of XinixImageMatrix)
		List< String > imageUrls = new ArrayList<>();
		for ( int i = 1; i < 37; i++ )
		{
			imageUrls.add( "http://skylla.zhaw.ch/xinix_images/xinix_img_" + i + ".jpg" );
		}

		Set< XinixImage > setOfXinixImages = new HashSet<>();

		for ( String url : imageUrls )
		{
			String xinixImageID = exerciseDataService.persist( new XinixImage( rootUser, null, url ) );
			XinixImage xinixImage = exerciseDataService.findByID( xinixImageID );
			setOfXinixImages.add( xinixImage );
		}

		// xinix image matrix (subclass of ExerciseDefinitionImpl)
		String xinixImageMatrixID = exerciseDefinitionService
			.persist( new XinixImageMatrix( rootUser, null, 0, (WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ), setOfXinixImages ) );

		// xinix definition
		String xinixDefID = exerciseDefinitionService.persist( new XinixDefinition(
			rootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was faellt Dir ein zum Thema ENGAGEMENT?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( xinixImageMatrixID ) ) );

		// you2me definition
		String you2meDefID = exerciseDefinitionService.persist( new You2MeDefinition( rootUser, TimeUnit.MINUTES, 2, (WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ), Arrays
			.asList( "Was moechtests Du gerne lernen?", "Wie kannst DU dies Deinem Gegenueber beibringen?" ) ) );

		// simple prototyping definition
		String simplePrototypingDefID = exerciseDefinitionService.persist( new SimplePrototypingDefinition( rootUser, TimeUnit.SECONDS, 240, (WorkshopDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ), "Mit welcher Aktion wird das Unternehmen weltberuehmt?", "mein mimetype" ) );

		// compression definition
		String compressionDefID = exerciseDefinitionService.persist( new CompressionDefinition(
			rootUser,
			TimeUnit.MINUTES,
			20,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Erarbeite Massnahmenvorschlaege aufgrund des bisherigen Inputs",
			Arrays.asList( "Produkteigenschaften", "Werbung", "Vertriebskanaele" ) ) );

		// evaluation definition
		String evaluationDefID = exerciseDefinitionService.persist( new EvaluationDefinition(
			rootUser,
			TimeUnit.MINUTES,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Wie bewertest Du diese Loesung?" ) );

		// pinklabs exercise
		String pinklabsExID = exerciseService.persist( new ExerciseImpl(
			"pinkLabs",
			"pinklabs Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( pinklabsDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2p one exercise
		String p2poneExID = exerciseService.persist( new ExerciseImpl(
			"P2P Kaskade 1",
			"Post2Paper Kaskade 1 Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2poneDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// you2me exercise
		String you2meExID = exerciseService.persist( new ExerciseImpl(
			"You2me",
			"You2me Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( you2meDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2p two exercise
		String p2ptwoExID = exerciseService.persist( new ExerciseImpl(
			"P2P Kaskade 2",
			"Post2Paper Kaskade 2 Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2ptwoDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise
		String xinixExID = exerciseService.persist( new ExerciseImpl(
			"Xinix",
			"Xinix Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// simple prototyping exercise
		String simplePrototypingExID = exerciseService.persist( new ExerciseImpl( "Simple Prototyping", "Simple Prototyping Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( simplePrototypingDefID ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// compression exercise
		String compressionExID = exerciseService.persist( new ExerciseImpl(
			"Kompression",
			"Kompression Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( compressionDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// evaluation exercise
		String evaluationExID = exerciseService.persist( new ExerciseImpl(
			"Evaluation",
			"Evaluation Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( evaluationDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

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
		exerciseDataService.persist( new XinixData( rootUser, (WorkflowElementImpl)exerciseService.findByID( xinixExID ), associations, setOfXinixImages.iterator().next() ) );

		// simpleprototyping data
		exerciseDataService.persist( new SimplePrototypingData( rootUser, (WorkflowElementImpl)exerciseService.findByID( simplePrototypingExID ), "Mein Blob".getBytes() ) );

		// compression data
		exerciseDataService.persist( new CompressionExerciseData( rootUser, (WorkflowElementImpl)exerciseService.findByID( compressionExID ), Arrays.asList(
			"Werbekampagne auf Youtube",
			"Wertbekampagne auf Facebook" ) ) );

		// evaluation data
		exerciseDataService.persist( new EvaluationExerciseData( rootUser, (WorkflowElementImpl)exerciseService.findByID( evaluationExID ), Arrays.asList( new Evaluation(
			rootUser,
			"Werbekampagne auf Youtube",
			4 ) ) ) );

		// session
		String sessionID = sessionService.persist( new SessionImpl( "Beispiel Session", "Beispiel Session fuer PinkElefant Workshop", null, (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// invitation (so that at least on is there) :)
		invitationService.persist( new Invitation( rootUser, rootUser, (SessionImpl)sessionService.findByID( sessionID ) ) );

		// user joins session (and by that all exercises in workshop)
		sessionService.join( new Invitation( null, rootUser, (SessionImpl)sessionService.findByID( sessionID ) ) );

		// configure second user to join session
		String participantID = userService.persist( new UserImpl(
			new PasswordCredentialImpl( "abc123" ),
			(RoleImpl)roleService.findByID( participantRoleID ),
			null,
			"participating",
			"participant",
			"pews_root_client/participant@pews" ) );

		sessionService.join( new Invitation( null, (UserImpl)userService.findByID( participantID ), (SessionImpl)sessionService.findByID( sessionID ) ) );

		// configure second session with root user as executer

		// code for new user - not used
		// String executerID = userService.persist( new UserImpl(
		// new PasswordCredentialImpl( "root" ),
		// (RoleImpl)roleService.findByID( executerRoleID ),
		// null,
		// "root first name",
		// "root last name",
		// "pews_root_client/executer@pews" ) );
		String secondSessionID = sessionService.persist( new SessionImpl( "Zweite Beispiel Session", "Zweite Beispiel Session fuer PinkElefant Workshop", null, (WorkshopImpl)workshopService
			.findByID( wsID ) ) );
		sessionService.addExecuter( new Invitation( null, (UserImpl)userService.findByID( rootUser.getID() ), (SessionImpl)sessionService.findByID( secondSessionID ) ) );

		System.out.println( "sample workshop configured" );

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
