package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.data;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.You2MeExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 15.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService} with {@link You2MeExerciseData}
 * - PERSIST
 * - FIND_BY_ID
 * - EXPORT_BY_EXERCISE_ID
 * - EXPORT_BY_WORKSHOP_ID
 * - REMOVE_BY_ID
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class You2MeExerciseDataTest
{
	private static ExerciseDataService exerciseDataService;

	private static ExerciseDataImpl exerciseData = new You2MeExerciseData();
	private static WorkshopImpl     workshop     = new WorkshopImpl();
	private static ExerciseImpl     exercise     = new ExerciseImpl();

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

		// workshop
		WorkshopTemplate workshopTemplate = workshopTemplateService.findByID( workshopTemplateService.persist( new WorkshopTemplate( null,
				"",
				"" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// exercise
		You2MeTemplate exerciseTemplate = (You2MeTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new You2MeTemplate( null,
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
						new HashSet<String>() ) ) );
		exercise.setID( exerciseService.persistExercise( new You2MeExercise( "", "", exerciseTemplate, workshop ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exerciseData.setID( exerciseDataService.persist( new You2MeExerciseData( null,
				exercise,
				Arrays.asList( new DialogEntry( DialogRole.RoleA, "a" ),
						new DialogEntry( DialogRole.RoleB, "b" ) ) ) ) );
		assertTrue( exerciseData.getID() != null );
		assertTrue( !exerciseData.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		You2MeExerciseData found = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseData.getID() ) );
		assertTrue( found.getWorkflowElement().getID().equals( exercise.getID() ) );
		assertTrue( found.getDialog().size() == 2 );
		assertTrue( found.getDialog().get( 0 ).getRole().equals( DialogRole.RoleA ) );
		assertTrue( found.getDialog().get( 0 ).getText().equals( "a" ) );
		assertTrue( found.getDialog().get( 1 ).getRole().equals( DialogRole.RoleB ) );
		assertTrue( found.getDialog().get( 1 ).getText().equals( "b" ) );

		// note that orderInDialog is not tested, as it is not persisted consistently by the API.
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
		You2MeExerciseData findable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 6 ) @Test public void testRemoveByID()
	{
		You2MeExerciseData removable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( removable.getID() ) );

		exerciseDataService.removeExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findByID( exerciseData.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( removable.getID() ) );
	}
}
