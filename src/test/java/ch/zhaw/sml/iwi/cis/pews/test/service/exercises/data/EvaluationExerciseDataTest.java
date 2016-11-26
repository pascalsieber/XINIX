package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.data;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionSynchronizationImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 15.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService} with {@link EvaluationExerciseData}
 * - PERSIST
 * - FIND_BY_ID
 * - EXPORT_BY_EXERCISE_ID
 * - EXPORT_BY_WORKSHOP_ID
 * - REMOVE_BY_ID
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class EvaluationExerciseDataTest
{
	private static ExerciseDataService exerciseDataService;

	private static ExerciseDataImpl exerciseData = new EvaluationExerciseData();
	private static WorkshopImpl     workshop     = new WorkshopImpl();
	private static SessionImpl      session      = new SessionImpl();
	private static ExerciseImpl     exercise     = new ExerciseImpl();

	private static CompressionExerciseDataElement solution = new CompressionExerciseDataElement();
	private static int                            SCORE    = 5;
	private static UserImpl                       owner    = new UserImpl();

	@BeforeClass public static void setup()
	{
		// services
		exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );
		ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
		UserService userService = ServiceProxyManager.createServiceProxy( UserServiceProxy.class );
		RoleService roleService = ServiceProxyManager.createServiceProxy( RoleServiceProxy.class );
		SessionService sessionService = ServiceProxyManager.createServiceProxy( SessionServiceProxy.class );

		// owner
		RoleImpl role = roleService.findByID( roleService.persist( new RoleImpl( "role", "role" ) ) );
		owner.setID( userService.persist( new UserImpl( new PasswordCredentialImpl( "password" ),
				role,
				null,
				"",
				"",
				"evaluationexercisedatatestlogin" ) ) );

		// workshop
		WorkshopTemplate workshopTemplate = workshopTemplateService.findByID( workshopTemplateService.persist( new WorkshopTemplate( null,
				"",
				"" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// session
		session.setID( sessionService.persistSession( new SessionImpl( "",
				"",
				null,
				SessionSynchronizationImpl.SYNCHRONOUS,
				workshop,
				null,
				null,
				null ) ) );

		// owner joins session -> needed as evaluation exercise service checks session's exercises
		sessionService.join( new Invitation( null, owner, session ) );

		// exercise
		EvaluationTemplate exerciseTemplate = (EvaluationTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new EvaluationTemplate( null,
						true,
						TimeUnit.SECONDS,
						0,
						true,
						true,
						true,
						0,
						workshopTemplate,
						"",
						"",
						"",
						0 ) ) );
		exercise.setID( exerciseService.persistExercise( new EvaluationExercise( "",
				"",
				exerciseTemplate,
				workshop ) ) );

		// solution
		CompressionExerciseData compressionExerciseData = exerciseDataService.findByID( exerciseDataService.persist( new CompressionExerciseData( owner,
				null,
				Collections.singletonList( new CompressionExerciseDataElement( "compressiondata",
						"compressiondata" ) ) ) ) );
		solution = compressionExerciseData.getSolutions().get( 0 );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exerciseData.setID( exerciseDataService.persistExerciseData( new EvaluationExerciseData( owner,
				exercise,
				new Evaluation( owner, solution, new Score( owner, SCORE ) ) ) ) );
		assertTrue( exerciseData.getID() != null );
		assertTrue( !exerciseData.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		EvaluationExerciseData found = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseData.getID() ) );
		assertTrue( found.getWorkflowElement().getID().equals( exercise.getID() ) );
		assertTrue( found.getEvaluation().getSolution().getID().equals( solution.getID() ) );
		assertTrue( found.getEvaluation().getScore().getScore() == SCORE );
	}

	@TestOrder( order = 3 ) @Test public void testExportByExerciseID()
	{
		// not checking excel, just if API call runs through
		exerciseDataService.exportByExerciseID( exercise.getID() );
	}

	@TestOrder( order = 4 ) @Test public void testExportByWorkshopID()
	{
		// not checking excel, just if API call runs through
		exerciseDataService.exportByWorkshopID( workshop.getID() );
	}

	@TestOrder( order = 5 ) @Test public void testFindAll()
	{
		EvaluationExerciseData findable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 6 ) @Test public void testRemoveByID()
	{
		EvaluationExerciseData removable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( removable.getID() ) );

		exerciseDataService.removeExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findByID( exerciseData.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( removable.getID() ) );
	}
}

