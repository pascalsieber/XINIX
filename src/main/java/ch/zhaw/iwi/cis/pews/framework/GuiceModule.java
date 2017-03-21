package ch.zhaw.iwi.cis.pews.framework;

import ch.zhaw.iwi.cis.pews.dao.*;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressableExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.P2POneDataDao;
import ch.zhaw.iwi.cis.pews.dao.impl.*;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.*;
import ch.zhaw.iwi.cis.pews.service.util.MailService;
import ch.zhaw.iwi.cis.pews.service.util.impl.MailServiceImpl;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class GuiceModule extends ServletModule{
    @Override
    protected void configureServlets() {
        // start the JPA service
        bind(JPAInitializer.class).asEagerSingleton();

        // PersistFilter
        filter("/*").through(PersistFilter.class);

        // jpa + guice
        install(new JpaPersistModule("pews")); // same as persistence.xml

        // Guice bindings
        bind( AuthenticationTokenService.class ).to( AuthenticationTokenServiceImpl.class );
        bind( AuthenticationTokenDao.class ).to( AuthenticationTokenDaoImpl.class );

        bind( CachedInputService.class ).to( CachedInputServiceImpl.class );
        bind( CachedInputDao.class ).to( CachedInputDaoImpl.class );

        bind( ClientService.class ).to( ClientServiceImpl.class );
        bind( ClientDao.class ).to( ClientDaoImpl.class );

        bind( ExerciseDataService.class ).to( ExerciseDataServiceImpl.class );
        bind( ExerciseDataDao.class ).to( ExerciseDataDaoImpl.class );

        bind( ExerciseService.class ).to( ExerciseServiceImpl.class );
        bind( ExerciseDao.class ).to( ExerciseDaoImpl.class );

        bind( ExerciseTemplateService.class ).to( ExerciseTemplateServiceImpl.class );
        bind( ExerciseTemplateDao.class ).to( ExerciseTemplateDaoImpl.class );

        bind( InvitationService.class ).to( InvitationServiceImpl.class );
        bind( InvitationDao.class ).to( InvitationDaoImpl.class );

        bind( MediaService.class ).to( MediaServiceImpl.class );
        bind( MediaDao.class ).to( MediaDaoImpl.class );

        bind( RoleService.class ).to( RoleServiceImpl.class );
        bind( RoleDao.class ).to( RoleDaoImpl.class );

        bind( SessionService.class ).to( SessionServiceImpl.class );
        bind( SessionDao.class ).to( SessionDaoImpl.class );

        bind( UserService.class ).to( UserServiceImpl.class );
        bind( UserDao.class ).to( UserDaoImpl.class );

        bind( WorkflowElementService.class ).to( WorkflowElementServiceImpl.class );
        bind( WorkflowElementDao.class ).to( WorkflowElementDaoImpl.class );

        bind( WorkshopService.class ).to( WorkshopServiceImpl.class );
        bind( WorkshopDao.class ).to( WorkshopDaoImpl.class );

        bind( WorkshopTemplateService.class ).to( WorkshopTemplateServiceImpl.class );
        bind( WorkshopTemplateDao.class ).to( WorkshopTemplateDaoImpl.class );

        bind( CompressionDataDao.class ).to(CompressionDataDaoImpl.class );
        bind( ExerciseDao.class ).to( ExerciseDaoImpl.class );
        bind( ExerciseService.class ).to( ExerciseServiceImpl.class );
        bind( ParticipantDao.class ).to( ParticipantDaoImpl.class );
        bind( MailService.class ).to( MailServiceImpl.class );
        bind( MediaService.class ).to( MediaServiceImpl.class );
        bind( ParticipantDao.class ).to( ParticipantDaoImpl.class );


    }
}
