package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.data;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
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
public class PinkLabsExerciseDataTest
{
	private ExerciseDataService exerciseDataService;

	private ExerciseDataImpl exerciseData = new PinkLabsExerciseData();
	private WorkshopImpl     workshop     = new WorkshopImpl();
	private ExerciseImpl     exercise     = new ExerciseImpl();

	private static String ANSWER_ONE = "one";
	private static String ANSWER_TWO = "two";

	@BeforeClass public void setup()
	{
		// services
		exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		WorkshopService workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );
		ExerciseService exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
		MediaService mediaService = ServiceProxyManager.createServiceProxy( MediaServiceProxy.class );

		// workshop
		WorkshopTemplate workshopTemplate = workshopTemplateService.findByID( workshopTemplateService.persist( new WorkshopTemplate( null,
				"",
				"" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// exercise
		ExerciseTemplate exerciseTemplate = exerciseTemplateService.findExerciseTemplateByID( exerciseTemplateService.persistExerciseTemplate(
				new ExerciseTemplate( null,
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

	@Test public void testPersist()
	{
		exerciseData.setID( exerciseDataService.persist( new PinkLabsExerciseData( null,
				exercise,
				Arrays.asList( ANSWER_ONE, ANSWER_TWO ) ) ) );
		assertTrue( exerciseData.getID() != null );
		assertTrue( !exerciseData.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		PinkLabsExerciseData found = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseData.getID() ) );
		assertTrue( found.getWorkflowElement().getID().equals( exercise.getID() ) );
		assertTrue( found.getAnswers().size() == 2 );
		assertTrue( found.getAnswers().containsAll( Arrays.asList( ANSWER_ONE, ANSWER_TWO ) ) );
	}

	@Test public void testExportByExerciseID()
	{
		// not checking excel, just if API call runs through
		exerciseDataService.exportByExerciseID( exercise.getID() );
	}

	@Test public void testExportByWorkshopID()
	{
		// not checking excel, just if API call runs through
		exerciseDataService.exportByWorkshopID( workshop.getID() );
	}

	@Test public void testFindAll()
	{
		PinkLabsExerciseData findable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findAllExerciseData().contains( findable ) );
	}

	@Test public void testRemoveByID()
	{
		PinkLabsExerciseData removable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findAllExerciseData().contains( removable ) );

		exerciseDataService.removeExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findByID( exerciseData.getID() ) == null );
		assertTrue( !exerciseDataService.findAllExerciseData().contains( removable ) );
	}
}

