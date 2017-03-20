package ch.zhaw.iwi.cis.pews.framework;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;

public class JPAInitializer {
    @Inject
    JPAInitializer(PersistService service) {
        service.start();
    }
}
