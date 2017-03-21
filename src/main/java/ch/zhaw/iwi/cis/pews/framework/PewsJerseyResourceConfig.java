package ch.zhaw.iwi.cis.pews.framework;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

public class PewsJerseyResourceConfig extends ResourceConfig {

    // Won't be initialized until onStartup()
    ServiceLocator serviceLocator;

    @Inject
    public PewsJerseyResourceConfig(Injector guiceInjector) {
        packages(true, "ch.zhaw.iwi.cis");

        register(new ContainerLifecycleListener()
        {
            public void onStartup(Container container)
            {
                serviceLocator = container.getApplicationHandler().getServiceLocator();

                // Guice Config
                GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
                GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
                guiceBridge.bridgeGuiceInjector(guiceInjector);
            }

            public void onReload(Container container) {/*...*/}
            public void onShutdown(Container container) {/*...*/}
        });


    }
}
