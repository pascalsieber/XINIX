package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.templates;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseTemplateRestService} with {@link CompressionTemplate}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
public class CompressionTemplateTest
{
	private static ExerciseTemplateService exerciseTemplateService;
	private static WorkshopTemplateService workshopTemplateService;

	private static String   QUESTIONTEMPLATE = "questiontemplate";
	private static String   NAME             = "name";
	private static String   DESCRIPTION      = "description";
	private static int      NUMBER           = 4;
	private static TimeUnit TIMEUNIT         = TimeUnit.MINUTES;

	private static String CRITERION_ONE = "criterionone";
	private static String CRITERION_TWO = "criteriontwo";

	private static ExerciseTemplate exerciseTemplate = new CompressionTemplate();

	private static WorkshopTemplate workshopTemplate = new WorkshopTemplate();

	@BeforeClass public static void setup()
	{
		// services
		exerciseTemplateService = ServiceProxyManager.createServiceProxy( ExerciseTemplateServiceProxy.class );
		workshopTemplateService = ServiceProxyManager.createServiceProxy( WorkshopTemplateServiceProxy.class );

		// workshop template
		workshopTemplate.setID( workshopTemplateService.persist( new WorkshopTemplate( null, "", "" ) ) );
	}

	@Test public void testPersist()
	{
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new CompressionTemplate(
				null,
				true,
				TIMEUNIT,
				NUMBER,
				true,
				true,
				true,
				NUMBER,
				workshopTemplate,
				QUESTIONTEMPLATE,
				NAME,
				DESCRIPTION,
				Arrays.asList( CRITERION_ONE, CRITERION_TWO ) ) ) );

		assertTrue( exerciseTemplate.getID() != null );
		assertTrue( !exerciseTemplate.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		CompressionTemplate found = (CompressionTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exerciseTemplate.getID() ) );
		assertTrue( found.getDefaultName().equals( NAME ) );
		assertTrue( found.getDefaultDescription().equals( DESCRIPTION ) );
		assertTrue( found.getQuestionTemplate().equals( QUESTIONTEMPLATE ) );
		assertTrue( found.getDuration() == NUMBER );
		assertTrue( found.getCardinality() == NUMBER );
		assertTrue( found.isCountable() );
		assertTrue( found.isSkippable() );
		assertTrue( found.isSharing() );
		assertTrue( found.isTimed() );
		assertTrue( found.getOrderInWorkshopTemplate() == 0 );
		assertTrue( found.getTimeUnit().equals( TIMEUNIT ) );
		assertTrue( found.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );

		assertTrue( found.getSolutionCriteria().containsAll( Arrays.asList( CRITERION_ONE, CRITERION_TWO ) ) );
	}

	@Test public void testFindAll()
	{
		CompressionTemplate findable = (CompressionTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		assertTrue( exerciseTemplateService.findAllExerciseTemplates().contains( findable ) );
	}

	@Test public void testRemove()
	{
		CompressionTemplate removable = (CompressionTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		assertTrue( exerciseTemplateService.findAllExerciseTemplates().contains( removable ) );
		assertTrue( workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.contains( removable ) );

		exerciseTemplateService.remove( exerciseTemplate );
		assertTrue( exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() ) == null );
		assertTrue( !exerciseTemplateService.findAllExerciseTemplates().contains( removable ) );
		assertTrue( !workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.contains( removable ) );
	}
}

