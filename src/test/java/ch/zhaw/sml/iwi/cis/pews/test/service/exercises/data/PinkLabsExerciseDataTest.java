package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.data;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 15.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService} with {@link PinkLabsExerciseData}
 * - PERSIST
 * - FIND_BY_ID
 * - EXPORT_BY_EXERCISE_ID
 * - EXPORT_BY_WORKSHOP_ID
 * - REMOVE_BY_ID
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class PinkLabsExerciseDataTest
{
	private static ExerciseDataService exerciseDataService;

	private static ExerciseDataImpl exerciseData = new PinkLabsExerciseData();
	private static WorkshopImpl     workshop     = new WorkshopImpl();
	private static ExerciseImpl     exercise     = new ExerciseImpl();

	private static String ANSWER_ONE = "one";
	private static String ANSWER_TWO = "two";

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
		ExerciseTemplate exerciseTemplate = exerciseTemplateService.findExerciseTemplateByID( exerciseTemplateService.persistExerciseTemplate(
				new PinkLabsTemplate( null,
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
						"" ) ) );
		exercise.setID( exerciseService.persistExercise( new ExerciseImpl( "", "", exerciseTemplate, workshop ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exerciseData.setID( exerciseDataService.persist( new PinkLabsExerciseData( null,
				exercise,
				Arrays.asList( ANSWER_ONE, ANSWER_TWO ) ) ) );
		assertTrue( exerciseData.getID() != null );
		assertTrue( !exerciseData.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		PinkLabsExerciseData found = (PinkLabsExerciseData)exerciseDataService.findExerciseDataByID( exerciseData.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseData.getID() ) );
		assertTrue( found.getWorkflowElement().getID().equals( exercise.getID() ) );
		assertTrue( found.getAnswers().size() == 2 );
		assertTrue( found.getAnswers().containsAll( Arrays.asList( ANSWER_ONE, ANSWER_TWO ) ) );
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
		PinkLabsExerciseData findable = (PinkLabsExerciseData)exerciseDataService.findExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findAllExerciseData().contains( findable ) );
	}

	@TestOrder( order = 6 ) @Test public void testRemoveByID()
	{
		PinkLabsExerciseData removable = (PinkLabsExerciseData)exerciseDataService.findExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findAllExerciseData().contains( removable ) );

		exerciseDataService.removeExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findExerciseDataByID( exerciseData.getID() ) == null );
		assertTrue( !exerciseDataService.findAllExerciseData().contains( removable ) );
	}
}

