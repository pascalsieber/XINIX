package ch.zhaw.iwi.cis.pews.framework;

import java.io.*;
import java.util.EnumSet;

import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;

import ch.zhaw.iwi.cis.pews.service.*;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService.*;

/*
  TODO: Secure all endpoints, whitelist:
		pews/global/ping
		pews/userService/user/requestPassword
		pews/authenticationService/authenticateWithToken

  TODO: Make sure the multipart limit is also fixed
		servlet.getRegistration().setMultipartConfig( new MultipartConfigElement( "data/tmp", 524288000, 524288000, 262144 ) );

  TODO: Make replacement for UserContext hack

 */

public class ZhawEngine
{
	private final static Logger LOG = LoggerFactory.getLogger(ZhawEngine.class);

	private static Server webServer;
	private static ManagedObjectRegistry managedObjectRegistry;
	private static ZhawEngine zhawEngine;

	static
	{
		// Fix Logging
		System.setProperty("org.jboss.logging.provider", "slf4j");
	}

	public static void main( String[] args ) throws IOException {
		LOG.info("Starting...");
		getEngine().start();
	}

	public static ZhawEngine getEngine()
	{
		if ( zhawEngine == null )
			zhawEngine = new ZhawEngine();
		return zhawEngine;
	}

	private void start() throws IOException {
		LOG.info("Setting up EntityManager...");
		setupEntityManager();
		LOG.info("Starting jetty...");
		startWebServer();
	}

	private static void setupEntityManager()
	{
		ManagedObjectRegistry registry = ZhawEngine.getManagedObjectRegistry();
		registry.registerManagedObjectType( new EntityManagerFactoryLifecycleManager( "pews" ), "pewsFactory", Scope.CLASSLOADER );
		EntityManagerLifecycleManager lifecycleManager = new EntityManagerLifecycleManager( (EntityManagerFactory)registry.getManagedObject( "pewsFactory" ) );
		registry.registerManagedObjectType( lifecycleManager, "pews", Scope.CLASSLOADER );
	}

	public static ManagedObjectRegistry getManagedObjectRegistry()
	{
		if ( managedObjectRegistry == null )
			managedObjectRegistry = new ManagedObjectRegistryImpl();

		return managedObjectRegistry;
	}

	private static void startWebServer() throws IOException {
		// Jetty Server
		webServer = new Server( PewsConfig.getApplicationPort() );

		ServletContextHandler pewsContext = new ServletContextHandler(webServer, SERVICES_BASE);
		pewsContext.addFilter(ServletContextFilter.class, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));

		ResourceConfig resourceConfig = new PewsJerseyResourceConfig();
		ServletHolder servlet = new ServletHolder( new ServletContainer( resourceConfig ) );
		pewsContext.addServlet(servlet, "/*");

		// Add security
		ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
		securityHandler.setHandler(pewsContext);
		securityHandler.setAuthenticator( new BasicAuthenticator() );
		securityHandler.setLoginService( new ZhawJDBCLoginService()  );
		// TODO: Constraints

		webServer.setHandler(securityHandler);

		// Start jetty and join the Threadpool
		try {
			webServer.start();
			LOG.info("Jetty started...");
			LOG.info("PEWS ready for requests.");
			webServer.join();
		} catch (Exception e) {
			LOG.error("Error starting jetty webserver");
			e.printStackTrace();
		} finally {
			webServer.destroy();
		}
	}

	public void stop() throws Exception {
		webServer.stop();
		getManagedObjectRegistry().stop();
	}

	private void bootstrap()
	{
	    BootstrapService bootstrap = new BootstrapService();
		bootstrap.configureRootUser();
		bootstrap.configureSampleWorkshop();
		bootstrap.configureDemoWorkshop();
		bootstrap.configurePostWorkshop();
	}

}
