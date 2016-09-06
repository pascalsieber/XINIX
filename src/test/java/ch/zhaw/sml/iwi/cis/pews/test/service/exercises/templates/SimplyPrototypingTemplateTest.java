package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.templates;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseTemplateRestService} with {@link SimplyPrototypingTemplate}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class SimplyPrototypingTemplateTest
{
	private static ExerciseTemplateService exerciseTemplateService;
	private static WorkshopTemplateService workshopTemplateService;

	private static String   QUESTIONTEMPLATE = "questiontemplate";
	private static String   NAME             = "name";
	private static String   DESCRIPTION      = "description";
	private static int      NUMBER           = 4;
	private static TimeUnit TIMEUNIT         = TimeUnit.MINUTES;

	private static String MIME = "mime";

	private static ExerciseTemplate exerciseTemplate = new SimplyPrototypingTemplate();

	private static WorkshopTemplate workshopTemplate = new WorkshopTemplate();

	@BeforeClass public static void setup()
	{
		// services
		exerciseTemplateService = ServiceProxyManager.createServiceProxy( ExerciseTemplateServiceProxy.class );
		workshopTemplateService = ServiceProxyManager.createServiceProxy( WorkshopTemplateServiceProxy.class );

		// workshop template
		workshopTemplate.setID( workshopTemplateService.persist( new WorkshopTemplate( null, "", "" ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new SimplyPrototypingTemplate(
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
				MIME ) ) );

		assertTrue( exerciseTemplate.getID() != null );
		assertTrue( !exerciseTemplate.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		SimplyPrototypingTemplate found = (SimplyPrototypingTemplate)exerciseTemplateService.findExerciseTemplateByID(
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

		assertTrue( found.getMimeType().equals( MIME ) );
	}

	@TestOrder( order = 3 ) @Test public void testFindAll()
	{
		SimplyPrototypingTemplate findable = (SimplyPrototypingTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		assertTrue( findable != null );
		assertTrue( TestUtil.extractIds( exerciseTemplateService.findAllExerciseTemplates() )
				.contains( findable.getID() ) );
	}

	@TestOrder( order = 4 ) @Test public void testRemove()
	{
		SimplyPrototypingTemplate removable = (SimplyPrototypingTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplate.getID() );
		assertTrue( TestUtil.extractIds( exerciseTemplateService.findAllExerciseTemplates() )
				.contains( removable.getID() ) );

		exerciseTemplateService.remove( exerciseTemplate );
		assertTrue( !TestUtil.extractIds( exerciseTemplateService.findAllExerciseTemplates() )
				.contains( removable.getID() ) );
	}
}

