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
import javax.xml.bind.DatatypeConverter;

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
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
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
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationResultDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PosterDefinition;
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

	public static String ROOT_CLIENT_ID;

	// defining these globally, since multiple pre-configured workshops might make use of the same role
	public static String ROOT_ROLE_ID;
	public static String PARTICIPANT_ROLE_ID;
	public static String EXECUTER_ROLE_ID;

	// defining this globally, since multiple pre-configured workshops might make use of the same xinix-image-matrix
	public static String XINIX_IMAGE_MATRIX_ID;
	public static List< XinixImage > XINIX_IMAGES;

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
		configurePostWorkshop();
		configureSBBWorkshop();

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

		EXECUTER_ROLE_ID = roleService.persist( new RoleImpl( "executer", "session executer" ) );
		System.out.println( "executer role created initially" );

		PARTICIPANT_ROLE_ID = roleService.persist( new RoleImpl( "participant", "workshop participant" ) );
		System.out.println( "participant role created initially" );

		// sample workshop definition (pinkelefant)
		String wsDefID = workshopDefinitionService.persist( new PinkElefantDefinition(
			rootUser,
			"p.i.n.k.elefant Definition",
			"Definition für p.i.n.k.elefant Workshop",
			"Produkteinfuehrung Teekocher" ) );

		// sample workshop instance
		String wsID = workshopService.persist( new WorkshopImpl( "p.i.n.k.elefant Workshop", "Beispiel eines p.i.n.k.elefant Workshops", (WorkflowElementDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ) ) );

		// workshop start definition
		String startDefID = exerciseDefinitionService.persist( new PosterDefinition(
			rootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Willkommen zum p.i.n.k.elefant Workshop",
			"Der Workshop beginnt in Kürze." ) );

		// workshop end definition
		String endDefID = exerciseDefinitionService.persist( new PosterDefinition(
			rootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Ende des p.i.n.k.elefant Workshops",
			"Vielen Dank für Ihre Teilnahme." ) );

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
			"Wie kann einer Person erfolgreich kommuniziert werden, welche zwei ausgewählte Eigenschaften hat?" ) );

		// xinix image -> used for xinix definition (as part of XinixImageMatrix)
		List< String > imageUrls = new ArrayList<>();
		for ( int i = 1; i < 37; i++ )
		{
			imageUrls.add( "http://skylla.zhaw.ch/xinix_images/xinix_img_" + i + ".jpg" );
		}

		XINIX_IMAGES = new ArrayList<>();

		for ( String url : imageUrls )
		{
			String xinixImageID = exerciseDataService.persist( new XinixImage( rootUser, null, url ) );
			XinixImage xinixImage = exerciseDataService.findByID( xinixImageID );
			XINIX_IMAGES.add( xinixImage );
		}

		// xinix image matrix (subclass of ExerciseDefinitionImpl)
		XINIX_IMAGE_MATRIX_ID = exerciseDefinitionService.persist( new XinixImageMatrix( rootUser, null, 0, (WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ), XINIX_IMAGES ) );

		// xinix definition
		String xinixDefID = exerciseDefinitionService.persist( new XinixDefinition(
			rootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was fällt Dir ein zum Thema ENGAGEMENT?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// you2me definition
		String you2meDefID = exerciseDefinitionService.persist( new You2MeDefinition( rootUser, TimeUnit.MINUTES, 2, (WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ), Arrays
			.asList( "Was möchtests Du gerne lernen?", "Wie kannst DU dies Deinem Gegenüber beibringen?" ) ) );

		// simple prototyping definition
		String simplePrototypingDefID = exerciseDefinitionService.persist( new SimplePrototypingDefinition( rootUser, TimeUnit.SECONDS, 240, (WorkshopDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ), "Mit welcher Aktion wird das Unternehmen weltberühmt?", "mein mimetype" ) );

		// kompression definition
		String compressionDefID = exerciseDefinitionService.persist( new CompressionDefinition(
			rootUser,
			TimeUnit.MINUTES,
			20,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Erarbeite Massnahmenvorschläge aufgrund des bisherigen Inputs",
			Arrays.asList( "Produkteigenschaften", "Werbung", "Vertriebskanaele" ) ) );

		// evaluation definition
		String evaluationDefID = exerciseDefinitionService.persist( new EvaluationDefinition(
			rootUser,
			TimeUnit.MINUTES,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Wie bewertest Du diese Lösungen?",
			5 ) );

		// evaluation result definition
		String evaluationResultDefID = exerciseDefinitionService.persist( new EvaluationResultDefinition( rootUser, TimeUnit.MINUTES, 10, (WorkshopDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ) ) );

		// workshop start exercise (poster)
		exerciseService.persist( new ExerciseImpl( "start", "p.i.n.k.elefant Start", (WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( startDefID ), (WorkshopImpl)workshopService
			.findByID( wsID ) ) );

		// pinklabs exercise
		String pinklabsExID = exerciseService.persist( new ExerciseImpl(
			"p.i.n.k.labs",
			"p.i.n.k.labs Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( pinklabsDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2p one exercise
		String p2poneExID = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1",
			"Post2Paper 1 Tool",
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
			"Post2Paper 2",
			"Post2Paper 2 Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2ptwoDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise
		String xinixExID = exerciseService.persist( new ExerciseImpl(
			"XINIX",
			"XINIX-Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// simple prototyping exercise
		String simplePrototypingExID = exerciseService.persist( new ExerciseImpl( "Simply Prototyping", "Simply Prototyping Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService
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

		// evaluation result exercise
		exerciseService.persist( new ExerciseImpl( "Auswertung der Evaluation", "Tool zur Auswertung der Evaluation", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( evaluationResultDefID ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// workshop end exercise (poster)
		exerciseService.persist( new ExerciseImpl(
			"Ende",
			"p.i.n.k.elefant Workshop Ende",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( endDefID ),
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
		exerciseDataService.persist( new XinixData( rootUser, (WorkflowElementImpl)exerciseService.findByID( xinixExID ), associations, XINIX_IMAGES.iterator().next() ) );

		// simpleprototyping data
		exerciseDataService
			.persist( new SimplePrototypingData(
				rootUser,
				(WorkflowElementImpl)exerciseService.findByID( simplePrototypingExID ),
				DatatypeConverter
					.parseBase64Binary( "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" ) ) );

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
		String sessionID = sessionService.persist( new SessionImpl( "Beispiel Session", "Beispiel Session für p.i.n.k.elefant Workshop", null, SessionSynchronizationImpl.SYNCHRONOUS, (WorkshopImpl)workshopService.findByID( wsID ), null, null, null, null, null ) );

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
		String secondSessionID = sessionService.persist( new SessionImpl( "Zweite Beispiel Session", "Zweite Beispiel Session für p.i.n.k.elefant Workshop", null, SessionSynchronizationImpl.SYNCHRONOUS, (WorkshopImpl)workshopService
			.findByID( wsID ), null, null, null, null, null ) );
		sessionService.addExecuter( new Invitation( null, (UserImpl)userService.findByID( rootUser.getID() ), (SessionImpl)sessionService.findByID( secondSessionID ) ) );

		sessionService.start( sessionID );

		System.out.println( "sample workshop configured" );

	}

	public static final String POST_ROOT_CLIENT_NAME = "post";
	public static final String POST_ROOT_USER_LOGIN_NAME = POST_ROOT_CLIENT_NAME + "/root@post";

	@SuppressWarnings( "unused" )
	private static void configurePostWorkshop()
	{
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		ClientService clientService = getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );
		WorkshopDefinitionService workshopDefinitionService = getManagedObjectRegistry().getManagedObject( WorkshopDefinitionServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseDefinitionService exerciseDefinitionService = getManagedObjectRegistry().getManagedObject( ExerciseDefinitionServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );

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

		// workshop definition (pinkelefant)
		String wsDefID = workshopDefinitionService.persist( new PinkElefantDefinition(
			postRootUser,
			"Post Workshop Definition",
			"Definition für p.i.n.k.elefant Workshop mit der Post",
			"Massnahmen Begleit-Service Paketdienst im Jahr 2020" ) );

		// workshop instance
		String wsID = workshopService
			.persist( new WorkshopImpl( "Post Workshop", "p.i.n.k.elefant Workshop mit der Post", (WorkflowElementDefinitionImpl)workshopDefinitionService.findByID( wsDefID ) ) );

		// pinklabs definition 1
		String pinklabsDefID1 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was macht dir Spass?" ) );

		// pinklabs definition 2
		String pinklabsDefID2 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Wie kann man dich überraschen?" ) );

		// pinklabs definition 3
		String pinklabsDefID3 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Wofür gibst du gerne Geld aus?" ) );

		// pinklabs definition 4
		String pinklabsDefID4 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was kostet dich zu viel Zeit?" ) );

		// p2p one iteration 1
		String p2pOneDefID1 = exerciseDefinitionService.persist( new P2POneDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/single.jpg",
			"Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?" ) );

		// p2p one iteration 2
		String p2pOneDefID2 = exerciseDefinitionService.persist( new P2POneDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/empfangsdame.png",
			"Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?" ) );

		// p2p one iteration 3
		String p2pOneDefID3 = exerciseDefinitionService.persist( new P2POneDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/hausfrau.jpg",
			"Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?" ) );

		// p2p one iteration 4
		String p2pOneDefID4 = exerciseDefinitionService.persist( new P2POneDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/senioren.jpg",
			"Welche Dinge haben folgende Personen in ihrem Alltag regelmässig zu erledigen?" ) );

		// p2p two
		String p2pTwoDefID = exerciseDefinitionService.persist( new P2PTwoDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Formuliere je eine mögliche neue Dienstleistung, welche die Post anbieten könnte." ) );

		// xinix definition iteration 1
		String xinixDefID1 = exerciseDefinitionService.persist( new XinixDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was fällt dir ein zum Thema Kundenservice in Verbindung mit dem gewürfelten Bild?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix definition iteration 2
		String xinixDefID2 = exerciseDefinitionService.persist( new XinixDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was fällt dir ein zum Thema Paket in Verbindung mit dem gewürfelten Bild?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix definition iteration 3
		String xinixDefID3 = exerciseDefinitionService.persist( new XinixDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was fällt dir ein zum Thema Postbote in Verbindung mit dem gewürfelten Bild?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix definition iteration 4
		String xinixDefID4 = exerciseDefinitionService.persist( new XinixDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was fällt dir ein zum Thema Jahr 2020 in Verbindung mit dem gewürfelten Bild?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// simple prototyping definition
		String simplePrototypingDefID = exerciseDefinitionService.persist( new SimplePrototypingDefinition( postRootUser, TimeUnit.SECONDS, 600, (WorkshopDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ), "Zeichne oder Bastle den Postboten 2030!", "tbd" ) );

		// kompression definition
		String compressionDefID = exerciseDefinitionService.persist( new CompressionDefinition(
			postRootUser,
			TimeUnit.MINUTES,
			45,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"ALLE INPUTS aus Aufgaben 1-5 erscheinen auf dem Screen. Nun werden konkrete Massnahmen zum Thema \"Massnahmen Begleit-Service Paketdiesnst im Jahr 2020\" formuliert.",
			new ArrayList< String >() ) );

		// evaluation definition
		String evaluationDefID = exerciseDefinitionService.persist( new EvaluationDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			600,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Die einzelnen Ideen werden nun bewertet",
			5 ) );

		// evaluation result definition
		String evaluationResultDefID = exerciseDefinitionService.persist( new EvaluationResultDefinition( postRootUser, TimeUnit.SECONDS, 600, (WorkshopDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ) ) );

		// end definition
		String endDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Abschluss",
			"Vielen Dank, dass Sie an diesem XINIX-Workshop teilgenommen haben!" ) );

		// intro definitions

		// intro 1
		String introDefID1 = exerciseDefinitionService
			.persist( new PosterDefinition(
				postRootUser,
				TimeUnit.SECONDS,
				120,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Willkommen",
				"Herzlich Willkommen beim XINIX-Workshop zum Thema <b>Massnahmen Begleit-Service Paketdienst im Jahr 2020</b>.<br/>Bei diesem Wokshop kommen folgende Phasen auf dich zu:<br/><b>Inspirationsphase</b>: Hier werden möglichst viele Gedanken gesammelt, welche in der Kompressionsphase helfen sollen, konkrete Ideen zu generieren.<br/><b>Kompressionsphase</b>: Hier werden die Inspirationen miteinander kombiniert und konkrete Ideen ausformuliert.<br/><b>Bewertung</b>: Die Ideen werden entsprechend gewissen Kriterien bewertet." ) );

		// intro 2
		String introDefID2 = exerciseDefinitionService
			.persist( new PosterDefinition(
				postRootUser,
				TimeUnit.SECONDS,
				30,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Inspirationsphase",
				"Du befindest dich jetzt in der <b>Inspirationsphase</b>. Hier erwarten dich 5 unterschiedliche Kreativitätstools. Wichtig bei all diesen Tools ist folgender Grundsatz: Ohne lange zu überlegen, schreib alles auf, was dir in den Sinn kommt. Ohne wenn und aber. Je mehr Antworten, desto besser." ) );

		String pinklabsIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"p.i.n.k.labs",
			"Beantworte die folgenden 4 unterschiedlichen Fragen." ) );

		String p2pOneIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Post2Paper 1",
			"Du siehst nun nacheinander 4 unterschiedliche Zielgruppen. Beantworte zu jeder dieser Zielgruppen die folgende Frage." ) );

		String p2pTwoIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Post2Paper 2",
			"Nimm jeweils 2 Antworten der letzten Aufgabe und beantworte die folgende Frage. Umschreibe die Dienstleistung mit 1-2 Sätzen." ) );

		String xinixIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"XINIX-Tool",
			"Nun folgen 4 unterschiedliche Themen. Bitte würfeln und das angezeigte Bild mit dem Thema verknüpfen. Pro Bild sind mehrere Antworten möglich. Du darfst beliebig oft würfeln. " ) );

		String simpleprotoIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			120,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Simply Prototyping",
			"Beantworte die folgende Frage indem du zeichnest, bastelst oder schreibst. Nimm dafür ein A3-Blatt zur Hand.<br/>iPad: Das Resultat bitte direkt mit dem Tablet fotografieren und hochladen.<br/>PC & Laptop: Das Resultat bitte mit dem PC oder Laptop via QR Code hochladen." ) );

		// intro 3
		String introDefID3 = exerciseDefinitionService
			.persist( new PosterDefinition(
				postRootUser,
				TimeUnit.SECONDS,
				30,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Kompressionsphase",
				"Du befindest dich jetzt in der <b>Kompressionsphase</b>. Nun kommen wir zurück auf unser Workshop-Thema: <b>Massnahmen Begleit-Service Paketdienst im Jahr 2020</b>. In den nächsten 45 Minuten geht es darum, konkrete Ideen dazu zu entwicklen. Gib jeder Massnahme einen Titel und beschreibe die Massnahme in mind. 4 Sätzen.<br/>Erarbeite so viele Massnahmen wie möglich.<br/>Wichtig: Lass dich von den Inspirationen, die auf dem Bildschirm erscheinen, anregen.<br/><br/>iPad: Die Inspirationen werden mit einem Zufallsgenerator aufgeführt. Drücke Random um den Zufallsgenerator auszulösen.<br/>PC & Laptop:  Alle Inspirationen sind den Aufgaben nach aufgelistet." ) );

		// intro 4
		String introDefID4 = exerciseDefinitionService
			.persist( new PosterDefinition(
				postRootUser,
				TimeUnit.SECONDS,
				30,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Bewertungsphase",
				"Gratuliere. Du bist bald am Ende dieses XINIX-Workshops. Nun kommt die <b>Bewertungsphase</b>. Die Ideen aller Teilnehmenden von diesem Workshop erscheinen nun auf deinem Screen. Lies diese durch und bewerte sie unter Einbezug des Kriteriums 'Umsetzbarkeit bei der Post'.<br/>Bestimme deine 5 favorisierten Ideen und gewichte diese noch gemäss einer Skala von 1-10." ) );

		// outro
		String outroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			postRootUser,
			TimeUnit.SECONDS,
			120,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"",
			"Gratulation, der XINIX-Workshop ist beendet. Die besten Ideen werden am kommenden Meeting vom xx.xx.2015 im Plenum besprochen und weiterentwickelt." ) );

		// exercise instances

		// start exercise / intro 1
		String startExID = exerciseService.persist( new ExerciseImpl(
			"Begruessung",
			"Workshop Start Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID1 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// intro 2
		exerciseService.persist( new ExerciseImpl( "Intro", "Intro Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID2 ), (WorkshopImpl)workshopService
			.findByID( wsID ) ) );

		// pinklabs intro
		exerciseService.persist( new ExerciseImpl(
			"p.i.n.k.labs Intro",
			"p.i.n.k.labs Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( pinklabsIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 1
		String pinklabsExID1 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (1/4)", "p.i.n.k.labs Tool 1", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID1 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 2
		String pinklabsExID2 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (2/4)", "p.i.n.k.labs Tool 2", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID2 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 3
		String pinklabsExID3 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (3/4)", "p.i.n.k.labs Tool 3", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID3 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 4
		String pinklabsExID4 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (4/4)", "p.i.n.k.labs Tool 4", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID4 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne intro
		exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 Intro",
			"Post2Paper 1 Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 1
		String p2pOneExID1 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (1/4)",
			"Post2Paper 1 Tool 1",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID1 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 2
		String p2pOneExID2 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (2/4)",
			"Post2Paper 1 Tool 2",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID2 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 3
		String p2pOneExID3 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (3/4)",
			"Post2Paper 1 Tool 3",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID3 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 4
		String p2pOneExID4 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (4/4)",
			"Post2Paper 1 Tool 4",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID4 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pTwo intro
		exerciseService.persist( new ExerciseImpl(
			"Post2Paper 2 Intro",
			"Post2Paper 2 Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pTwoIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pTwo exercise
		String p2pTwoExID = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 2",
			"Post2Paper 2 Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pTwoDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix intro
		exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool Intro",
			"XINIX-Tool Intro",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 1
		String xinixExID1 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (1/4)",
			"XINIX-Tool 1",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID1 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 2
		String xinixExID2 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (2/4)",
			"XINIX-Tool 2",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID2 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 3
		String xinixExID3 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (3/4)",
			"XINIX-Tool 3",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID3 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 4
		String xinixExID4 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (4/4)",
			"XINIX-Tool 4",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID4 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// simplyproto intro
		exerciseService.persist( new ExerciseImpl( "Simply Prototyping Intro", "Simply Prototyping Intro Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( simpleprotoIntroDefID ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// simple prototyping exercise
		String simplePrototypingExID = exerciseService.persist( new ExerciseImpl( "Simply Prototyping", "Simply Prototyping Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( simplePrototypingDefID ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// intro 3 / kompression
		exerciseService.persist( new ExerciseImpl(
			"Kompression Intro",
			"Kompression Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID3 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// kompression exercise
		String compressionExID = exerciseService.persist( new ExerciseImpl(
			"Kompression",
			"Kompression Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( compressionDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// intro 4 / Bewertung
		exerciseService.persist( new ExerciseImpl(
			"Bewertung Intro",
			"Bewertung Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID4 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// evaluation exercise
		String evaluationExID = exerciseService.persist( new ExerciseImpl(
			"Bewertungsrunde",
			"Tool zur Evaluation der erarbeiteten Loesungen",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( evaluationDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// evaluation result exercise
		// TODO: include this, once we agree on specs with tablet implementers
		// String evaluationResultExID = exerciseService.persist( new ExerciseImpl(
		// "Resultate der Bewertungsrunde",
		// "Tool zur Auswertung der Resultate aus der Bewertungsrunde",
		// (WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( ),
		// (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// outro
		exerciseService.persist( new ExerciseImpl( "Outro", "Outro Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( outroDefID ), (WorkshopImpl)workshopService
			.findByID( wsID ) ) );

		// end exercise
		String endExID = exerciseService.persist( new ExerciseImpl(
			"Abschluss",
			"Workshop End Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( endDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// session
		String sessionID = sessionService.persist( new SessionImpl( "Beispiel Session", "Beispiel Session für p.i.n.k.elefant Workshop mit der Post", null, SessionSynchronizationImpl.SYNCHRONOUS, (WorkshopImpl)workshopService
			.findByID( wsID ), null, null, null, null, null ) );

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
		WorkshopDefinitionService workshopDefinitionService = getManagedObjectRegistry().getManagedObject( WorkshopDefinitionServiceImpl.class.getSimpleName() );
		WorkshopService workshopService = getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		ExerciseDefinitionService exerciseDefinitionService = getManagedObjectRegistry().getManagedObject( ExerciseDefinitionServiceImpl.class.getSimpleName() );
		ExerciseService exerciseService = getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
		SessionService sessionService = getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );

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

		// workshop definition (pinkelefant)
		String wsDefID = workshopDefinitionService.persist( new PinkElefantDefinition(
			sbbRootUser,
			"SBB Workshop Definition",
			"Definition für p.i.n.k.elefant Workshop mit der SBB",
			"Was wünsche ich mir am Bahnhof" ) );

		// workshop instance
		String wsID = workshopService
			.persist( new WorkshopImpl( "SBB Workshop", "p.i.n.k.elefant Workshop mit der SBB", (WorkflowElementDefinitionImpl)workshopDefinitionService.findByID( wsDefID ) ) );

		// pinklabs definition 1
		String pinklabsDefID1 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was mache ich alles online?" ) );

		// pinklabs definition 2
		String pinklabsDefID2 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Das macht mir Spass?" ) );

		// pinklabs definition 3
		String pinklabsDefID3 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Das spart mir Zeit?" ) );

		// pinklabs definition 4
		String pinklabsDefID4 = exerciseDefinitionService.persist( new PinkLabsDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was würde ich nie online kaufen?" ) );

		// p2p one iteration 1
		String p2pOneDefID1 = exerciseDefinitionService.persist( new P2POneDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/familie.jpg",
			"Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)" ) );

		// p2p one iteration 2
		String p2pOneDefID2 = exerciseDefinitionService.persist( new P2POneDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/senioren.jpg",
			"Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)" ) );

		// p2p one iteration 3
		String p2pOneDefID3 = exerciseDefinitionService.persist( new P2POneDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/business.jpg",
			"Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)" ) );

		// p2p one iteration 4
		String p2pOneDefID4 = exerciseDefinitionService.persist( new P2POneDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			60,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"http://skylla.zhaw.ch/p2p_one_images/schueler.jpg",
			"Was machen diese Personen an einem Bahnhof? (ausser in den Zug zu steigen)" ) );

		// p2p two
		String p2pTwoDefID = exerciseDefinitionService.persist( new P2PTwoDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			240,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Formuliere eine mögliche neue Dienstleistung, welche dafür am Bahnhof angeboten werden könnte. Umschreibe die Dienstleistung mit 1-2 Sätzen." ) );

		// xinix definition iteration 1
		String xinixDefID1 = exerciseDefinitionService.persist( new XinixDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			90,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was bedeutet für mich \"Service\" in Verbindung mit dem gewürfelten Bild",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix definition iteration 2
		String xinixDefID2 = exerciseDefinitionService.persist( new XinixDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			90,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Neue Dienstleistung am Bahnhof in Verbindung mit dem gewürfelten Bild",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix definition iteration 3
		String xinixDefID3 = exerciseDefinitionService.persist( new XinixDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			90,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was erwartest du vom Bahnhof 2050 in Verbindung mit dem gewürfelten Bild?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// xinix definition iteration 4
		String xinixDefID4 = exerciseDefinitionService.persist( new XinixDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			90,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was erwartest du vom ÖV in Verbindung mit dem gewürfelten Bild?",
			(XinixImageMatrix)exerciseDefinitionService.findByID( XINIX_IMAGE_MATRIX_ID ) ) );

		// simple prototyping definition
		String simplePrototypingDefID = exerciseDefinitionService.persist( new SimplePrototypingDefinition( sbbRootUser, TimeUnit.SECONDS, 480, (WorkshopDefinitionImpl)workshopDefinitionService
			.findByID( wsDefID ), "So sieht mein optimaler Bahnhof aus", "tbd" ) );

		// Kompression definition
		String compressionDefID = exerciseDefinitionService.persist( new CompressionDefinition(
			sbbRootUser,
			TimeUnit.MINUTES,
			12,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Was wünsche ich mir am Bahnhof?",
			new ArrayList< String >() ) );

		// evaluation definition
		String evaluationDefID = exerciseDefinitionService.persist( new EvaluationDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			480,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Die einzelnen Ideen werden nun bewertet",
			5 ) );

		// intro definitions

		// intro 1
		String introDefID1 = exerciseDefinitionService
			.persist( new PosterDefinition(
				sbbRootUser,
				TimeUnit.SECONDS,
				120,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Willkommen",
				"Herzlich Willkommen beim XINIX-Workshop zum Thema <b>Was wünsche ich mir am Bahnhof?</b>.<br/>Bei diesem Wokshop kommen folgende Phasen auf dich zu:<br/><b>Inspirationsphase</b>: Hier werden möglichst viele Gedanken gesammelt, welche in der Kompressionsphase helfen sollen, konkrete Ideen zu generieren.<br/><b>Kompressionsphase</b>: Hier werden die Inspirationen miteinander kombiniert und konkrete Ideen ausformuliert.<br/><b>Bewertung</b>: Die Ideen werden entsprechend gewissen Kriterien bewertet.<br/>Bitte nimm dir ca. 45 Minuten Zeit, um diesen Workshop durchzuspielen." ) );

		// intro 2
		String introDefID2 = exerciseDefinitionService
			.persist( new PosterDefinition(
				sbbRootUser,
				TimeUnit.SECONDS,
				30,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Inspirationsphase",
				"Du befindest dich jetzt in der <b>Inspirationsphase</b>. Hier erwarten dich 5 unterschiedliche Kreativitätstools. Wichtig bei all diesen Tools ist folgender Grundsatz: Ohne lange zu überlegen, schreib alles auf, was dir in den Sinn kommt. Ohne wenn und aber. Je mehr Antworten, desto besser." ) );

		String pinklabsIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"p.i.n.k.labs",
			"Beantworte die folgenden 4 unterschiedlichen Fragen." ) );

		String p2pOneIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Post2Paper 1",
			"Du siehst nun nacheinander 4 unterschiedliche Zielgruppen. Beantworte zu jeder dieser Zielgruppen die folgende Frage." ) );

		String p2pTwoIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Post2Paper 2",
			"Nimm jeweils 2 Antworten der letzten Aufgabe und beantworte die folgende Frage." ) );

		String xinixIntroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			15,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"XINIX-Tool",
			"Nun folgen 4 unterschiedliche Themen.<br/>Bitte würfeln und das angezeigte Bild mit dem Thema verknüpfen. Pro Bild sind mehrere Antworten möglich. Du darfst beliebig oft würfeln." ) );

		String simpleprotoIntroDefID = exerciseDefinitionService
			.persist( new PosterDefinition(
				sbbRootUser,
				TimeUnit.SECONDS,
				120,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Simply Prototyping",
				"Beantworte die folgende Frage indem du zeichnest, bastelst oder schreibst. Nimm dafür ein A3-Blatt zur Hand.<br/>iPad: Das Resultat bitte direkt mit dem Tablet fotografieren und hochladen.<br/>PC & Laptop: Das Resultat bitte mit dem PC oder Laptop via QR Code hochladen." ) );

		// intro 3
		String introDefID3 = exerciseDefinitionService
			.persist( new PosterDefinition(
				sbbRootUser,
				TimeUnit.SECONDS,
				30,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Kompressionsphase",
				"Du befindest dich jetzt in der <b>Kompressionsphase</b>. Nun kommen wir zurück auf unser Workshop-Thema: <b>Was wünsche ich mir am Bahnhof?</b>.<br/>In den nächsten 12 Minuten geht es darum, konkrete Ideen dazu zu entwicklen. Gib jeder Massnahme einen Titel und beschreibe die Massnahme<br/>Erarbeite so viele Massnahmen wie möglich.<br/>Wichtig: Lass dich von den Inspirationen, die auf dem Bildschirm erscheinen, anregen.<br/><br/>iPad: Die Inspirationen werden mit einem Zufallsgenerator aufgeführt. Drücke Random um den Zufallsgenerator auszulösen.<br/>PC & Laptop:  Alle Inspirationen sind den Aufgaben nach aufgelistet." ) );

		// intro 4
		String introDefID4 = exerciseDefinitionService
			.persist( new PosterDefinition(
				sbbRootUser,
				TimeUnit.SECONDS,
				30,
				(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
				"Bewertungsphase",
				"Gratuliere. Du bist bald am Ende dieses XINIX-Workshops. Nun kommt die <b>Bewertungsphase</b>. Die Ideen aller Teilnehmenden von diesem Workshop erscheinen nun auf deinem Screen. Lies diese durch und bewerte sie unter Einbezug des Kriteriums \"Umsetzbarkeit realistisch\".<br/>Bestimme deine 5 favorisierten Ideen und gewichte diese noch gemäss einer Skala von 1-10." ) );

		// outro
		String outroDefID = exerciseDefinitionService.persist( new PosterDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			120,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"",
			"Gratulation, der XINIX-Workshop ist beendet. Die besten Ideen werden am 01.07.2015 im Rahmen eines kreativ.workshops besprochen und weiterentwickelt" ) );

		// end
		String endDefID = exerciseDefinitionService.persist( new PosterDefinition(
			sbbRootUser,
			TimeUnit.SECONDS,
			180,
			(WorkshopDefinitionImpl)workshopDefinitionService.findByID( wsDefID ),
			"Abschluss",
			"Vielen Dank, dass du an diesem XINIX-Workshop teilgenommen hast!" ) );

		// exercise instances

		// start exercise / intro 1
		String startExID = exerciseService.persist( new ExerciseImpl(
			"Begruessung",
			"Workshop Start Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID1 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// intro 2
		exerciseService.persist( new ExerciseImpl( "Intro", "Intro Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID2 ), (WorkshopImpl)workshopService
			.findByID( wsID ) ) );

		// pinklabs intro
		exerciseService.persist( new ExerciseImpl(
			"p.i.n.k.labs Intro",
			"p.i.n.k.labs Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( pinklabsIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 1
		String pinklabsExID1 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (1/4)", "p.i.n.k.labs Tool 1", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID1 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 2
		String pinklabsExID2 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (2/4)", "p.i.n.k.labs Tool 2", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID2 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 3
		String pinklabsExID3 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (3/4)", "p.i.n.k.labs Tool 3", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID3 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// pinklabs exercise 4
		String pinklabsExID4 = exerciseService.persist( new ExerciseImpl( "p.i.n.k.labs (4/4)", "p.i.n.k.labs Tool 4", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( pinklabsDefID4 ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne intro
		exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 Intro",
			"Post2Paper 1 Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 1
		String p2pOneExID1 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (1/4)",
			"Post2Paper 1 Tool 1",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID1 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 2
		String p2pOneExID2 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (2/4)",
			"Post2Paper 1 Tool 2",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID2 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 3
		String p2pOneExID3 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (3/4)",
			"Post2Paper 1 Tool 3",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID3 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pOne exercise 4
		String p2pOneExID4 = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 1 (4/4)",
			"Post2Paper 1 Tool 4",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pOneDefID4 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pTwo intro
		exerciseService.persist( new ExerciseImpl(
			"Post2Paper 2 Intro",
			"Post2Paper 2 Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pTwoIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// p2pTwo exercise
		String p2pTwoExID = exerciseService.persist( new ExerciseImpl(
			"Post2Paper 2",
			"Post2Paper 2 Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( p2pTwoDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix intro
		exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool Intro",
			"XINIX-Tool Intro",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixIntroDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 1
		String xinixExID1 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (1/4)",
			"XINIX-Tool 1",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID1 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 2
		String xinixExID2 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (2/4)",
			"XINIX-Tool 2",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID2 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 3
		String xinixExID3 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (3/4)",
			"XINIX-Tool 3",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID3 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// xinix exercise 4
		String xinixExID4 = exerciseService.persist( new ExerciseImpl(
			"XINIX-Tool (4/4)",
			"XINIX-Tool 4",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( xinixDefID4 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// simplyproto intro
		exerciseService.persist( new ExerciseImpl( "Simply Prototyping Intro", "Simply Prototyping Intro Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( simpleprotoIntroDefID ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// simple prototyping exercise
		String simplePrototypingExID = exerciseService.persist( new ExerciseImpl( "Simply Prototyping", "Simply Prototyping Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService
			.findByID( simplePrototypingDefID ), (WorkshopImpl)workshopService.findByID( wsID ) ) );

		// intro 3 / compression
		exerciseService.persist( new ExerciseImpl(
			"Kompression Intro",
			"Kompression Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID3 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// kompression exercise
		String compressionExID = exerciseService.persist( new ExerciseImpl(
			"Kompression",
			"Kompression Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( compressionDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// intro 4 / bewertung
		exerciseService.persist( new ExerciseImpl(
			"Bewertung Intro",
			"Bewertung Intro Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( introDefID4 ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// evaluation exercise
		String evaluationExID = exerciseService.persist( new ExerciseImpl(
			"Bewertungsrunde",
			"Tool zur Evaluation der erarbeiteten Loesungen",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( evaluationDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// outro
		exerciseService.persist( new ExerciseImpl( "Outro", "Outro Tool", (WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( outroDefID ), (WorkshopImpl)workshopService
			.findByID( wsID ) ) );

		// end exercise
		String endExID = exerciseService.persist( new ExerciseImpl(
			"Abschluss",
			"Workshop Abschluss Tool",
			(WorkflowElementDefinitionImpl)exerciseDefinitionService.findByID( endDefID ),
			(WorkshopImpl)workshopService.findByID( wsID ) ) );

		// sessions and participants
		// TODO: change theses to participants
		for ( int i = 0; i < 10; i++ )
		{
			String sessionID = sessionService.persist( new SessionImpl(
				"Session für Teilnehmer " + i,
				"Session für Teilnehmer " + i + " für p.i.n.k.elefant Workshop mit SBB",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS, (WorkshopImpl)workshopService.findByID( wsID ), null, null, null, null, null ) );

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
		
		for ( int i = 0; i < 20; i++ )
		{
			String sessionID = sessionService.persist( new SessionImpl(
				"Asynchrone Session für Teilnehmer " + i,
				"Asynchrone Session für Teilnehmer " + i + " für p.i.n.k.elefant Workshop mit SBB",
				null,
				SessionSynchronizationImpl.ASYNCHRONOUS, (WorkshopImpl)workshopService.findByID( wsID ), null, null, null, null, null ) );

			String participantID = userService.persist( new UserImpl(
				new PasswordCredentialImpl( "abc123" ),
				(RoleImpl)roleService.findByID( PARTICIPANT_ROLE_ID ),
				null,
				"teilnehmer",
				"teilnehmer " + i,
				SBB_ROOT_CLIENT_NAME + "/p" + i + "@sbb" ) );

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
