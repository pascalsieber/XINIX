package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.data;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 15.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService} with {@link P2PTwoData}
 * - PERSIST
 * - FIND_BY_ID
 * - EXPORT_BY_EXERCISE_ID
 * - EXPORT_BY_WORKSHOP_ID
 * - REMOVE_BY_ID
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class P2PTwoExerciseDataTest
{
	private static ExerciseDataService exerciseDataService;

	private static ExerciseDataImpl exerciseData = new P2PTwoData();
	private static WorkshopImpl     workshop     = new WorkshopImpl();
	private static ExerciseImpl     exercise     = new ExerciseImpl();

	private static P2POneKeyword p2POneKeyword = new P2POneKeyword();

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
		P2PTwoTemplate exerciseTemplate = (P2PTwoTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new P2PTwoTemplate( null,
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
		exercise.setID( exerciseService.persistExercise( new P2PTwoExercise( "", "", exerciseTemplate, workshop ) ) );

		// p2pOneData to generate p2pOneKeyword
		P2POneData p2POneData = (P2POneData)exerciseDataService.findExerciseDataByID( exerciseDataService.persist( new P2POneData( null,
				null,
				Collections.singletonList( "keyword" ) ) ) );
		p2POneKeyword.setID( p2POneData.getKeywords().get( 0 ).getID() );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exerciseData.setID( exerciseDataService.persist( new P2PTwoData( null,
				exercise,
				Arrays.asList( ANSWER_ONE, ANSWER_TWO ),
				new HashSet<P2POneKeyword>( Arrays.asList( p2POneKeyword, p2POneKeyword ) ) ) ) );
		assertTrue( exerciseData.getID() != null );
		assertTrue( !exerciseData.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		P2PTwoData found = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseData.getID() ) );
		assertTrue( found.getWorkflowElement().getID().equals( exercise.getID() ) );
		assertTrue( found.getAnswers().containsAll( Arrays.asList( ANSWER_ONE, ANSWER_TWO ) ) );
		assertTrue( found.getSelectedKeywords().size() == 2 );
		for ( P2POneKeyword keyword : found.getSelectedKeywords() )
		{
			assertTrue( keyword.getID().equals( p2POneKeyword.getID() ) );
		}
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
		P2PTwoData findable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 6 ) @Test public void testRemoveByID()
	{
		P2PTwoData removable = exerciseDataService.findByID( exerciseData.getID() );
		assertTrue( TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( removable.getID() ) );

		exerciseDataService.removeExerciseDataByID( exerciseData.getID() );
		assertTrue( exerciseDataService.findByID( exerciseData.getID() ) == null );
		assertTrue( !TestUtil.extractIds( exerciseDataService.findAllExerciseData() ).contains( removable.getID() ) );
	}
}

