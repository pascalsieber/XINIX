package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.data;

import static org.junit.Assert.assertTrue;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
public class You2MeExerciseDataTest
{
	private ExerciseDataService exerciseDataService;

	private ExerciseDataImpl exerciseData = new You2MeExerciseData();
	private WorkshopImpl     workshop     = new WorkshopImpl();
	private ExerciseImpl     exercise     = new ExerciseImpl();

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
		exerciseData.setID( exerciseDataService.persist( new You2MeExerciseData( null,
				exercise,
				Arrays.asList( new DialogEntry( DialogRole.RoleA, "a" ),
						new DialogEntry( DialogRole.RoleB, "b" ) ) ) ) );
		assertTrue( exerciseData.getID() != null );
		assertTrue( !exerciseData.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		You2MeExerciseData found = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseData.getID() ) );
		assertTrue( found.getWorkflowElement().getID().equals( exercise.getID() ) );
		assertTrue( found.getDialog().size() == 2 );
		assertTrue( found.getDialog().get( 0 ).getRole().equals( DialogRole.RoleA ) );
		assertTrue( found.getDialog().get( 0 ).getText().equals( "a" ) );
		assertTrue( found.getDialog().get( 0 ).getOrderInDialog() == 0 );
		assertTrue( found.getDialog().get( 1 ).getRole().equals( DialogRole.RoleB ) );
		assertTrue( found.getDialog().get( 1 ).getText().equals( "b" ) );
		assertTrue( found.getDialog().get( 1 ).getOrderInDialog() == 1 );
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
		You2MeExerciseData findable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findAllExerciseData().contains( findable ) );
	}

	@Test public void testRemoveByID()
	{
		You2MeExerciseData removable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findAllExerciseData().contains( removable ) );

		exerciseDataService.removeExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findByID( exerciseData.getID() ) == null );
		assertTrue( !exerciseDataService.findAllExerciseData().contains( removable ) );
	}
}
