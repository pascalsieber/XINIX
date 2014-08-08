package ch.zhaw.iwi.cis.pews.framework;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ClientService;
import ch.zhaw.iwi.cis.pews.service.RoleService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.ClientServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.RoleServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;
import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;
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
		ensureRootUser();
		ensureDefaultRoles();
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
		handler.addFilter( ThreadLocalFilter.class, "/*", EnumSet.of( DispatcherType.INCLUDE, DispatcherType.REQUEST ) );

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

		List< ConstraintMapping > mappings = new ArrayList<>();
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

	private static void ensureRootUser()
	{
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );
		UserService userService = getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
		ClientService clientService = getManagedObjectRegistry().getManagedObject( ClientServiceImpl.class.getSimpleName() );

		boolean rootRegistered = false;
		for ( IdentifiableObject user : userService.findAll(rootClient.getID()) )
		{
			if ( ( (UserImpl)user ).getLoginName().equalsIgnoreCase( "root@pews" ) )
			{
				rootRegistered = true;
				break;
			}
		}

		if ( !rootRegistered )
		{
			rootClient = clientService.findByID( clientService.persist( new Client( "pews root client" ) ) );
			String roleID = roleService.persist( new RoleImpl( rootClient, "root", "root" ) );
			userService.persist( new UserImpl( rootClient, new PasswordCredentialImpl( "root" ), (RoleImpl)roleService.findByID( roleID ), null, "root first name", "root last name", "root@pews" ) );
			System.out.println( "root user registered initially" );
		}

	}

	private static void ensureDefaultRoles()
	{
		RoleService roleService = getManagedObjectRegistry().getManagedObject( RoleServiceImpl.class.getSimpleName() );

		roleService.persist( new RoleImpl( rootClient, "organizer", "workshop organizer" ) );
		System.out.println( "organizer role created initially" );

		roleService.persist( new RoleImpl( rootClient, "executer", "session executer" ) );
		System.out.println( "executer role created initially" );

		roleService.persist( new RoleImpl( rootClient, "participant", "workshop participant" ) );
		System.out.println( "participant role created initially" );
	}

	private static void stopDatabase()
	{
		NetworkServerControlWrapper.shutdown( serverControl );
	}

	private static void stopWebServer()
	{
		ServerWrapper.stop( webServer );
	}
}
