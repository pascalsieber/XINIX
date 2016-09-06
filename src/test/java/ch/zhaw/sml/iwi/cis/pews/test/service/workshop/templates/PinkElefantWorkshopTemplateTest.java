package ch.zhaw.sml.iwi.cis.pews.test.service.workshop.templates;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.WorkshopTemplateRestService} with {@link ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate}
 * - PERSIST
 * - FIND_BY_ID
 * - UPDATE_EXERCISE_TEMPLATES_ORDER
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class PinkElefantWorkshopTemplateTest
{
	private static WorkshopTemplateService workshopTemplateService;
	private static ExerciseTemplateService exerciseTemplateService;

	private static String NAME        = "name";
	private static String DESCRIPTION = "description";
	private static String PROBLEM     = "problem";
	private static String EMAIL       = "email";

	private static WorkshopTemplate workshopTemplate    = new PinkElefantTemplate();
	private static ExerciseTemplate exerciseTemplateOne = new PinkLabsTemplate();
	private static ExerciseTemplate exerciseTemplateTwo = new PinkLabsTemplate();

	@BeforeClass public static void setup()
	{
		workshopTemplateService = ServiceProxyManager.createServiceProxy( WorkshopTemplateServiceProxy.class );
		exerciseTemplateService = ServiceProxyManager.createServiceProxy( ExerciseTemplateServiceProxy.class );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		workshopTemplate.setID( workshopTemplateService.persist( new PinkElefantTemplate( null,
				NAME,
				DESCRIPTION,
				PROBLEM,
				EMAIL ) ) );

		exerciseTemplateOne.setID( exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				workshopTemplate,
				"",
				"",
				"" ) ) );

		exerciseTemplateTwo.setID( exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
				false,
				null,
				0,
				false,
				false,
				false,
				0,
				workshopTemplate,
				"",
				"",
				"" ) ) );

		assertTrue( workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() ) != null );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		PinkElefantTemplate found = (PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID(
				workshopTemplate.getID() );

		assertTrue( found.getID().equals( workshopTemplate.getID() ) );
		assertTrue( found.getName().equals( NAME ) );
		assertTrue( found.getDescription().equals( DESCRIPTION ) );
		assertTrue( found.getProblem().equals( PROBLEM ) );
		assertTrue( found.getDefaultEmailText().equals( EMAIL ) );
		assertTrue( found.getExerciseTemplates().size() == 2 );
		assertTrue( found.getExerciseTemplates().get( 0 ).getID().equals( exerciseTemplateOne.getID() ) );
		assertTrue( found.getExerciseTemplates().get( 1 ).getID().equals( exerciseTemplateTwo.getID() ) );
	}

	@TestOrder( order = 3 ) @Test public void testUpdateExerciseTemplateOrder()
	{
		WorkshopTemplate updateable = new WorkshopTemplate();
		updateable.setID( workshopTemplate.getID() );
		updateable.setExerciseTemplates( Arrays.asList( exerciseTemplateTwo, exerciseTemplateOne ) );
		workshopTemplateService.updateOrderOfExerciseTemplates( updateable );

		PinkElefantTemplate updated = (PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID(
				workshopTemplate.getID() );
		assertTrue( updateable.getID().equals( workshopTemplate.getID() ) );
		assertTrue( updated.getExerciseTemplates().size() == 2 );
		assertTrue( updated.getExerciseTemplates().get( 0 ).getID().equals( exerciseTemplateTwo.getID() ) );
		assertTrue( updated.getExerciseTemplates().get( 1 ).getID().equals( exerciseTemplateOne.getID() ) );
	}

	@TestOrder( order = 4 ) @Test public void testFindAll()
	{
		PinkElefantTemplate findable = (PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID(
				workshopTemplate.getID() );
		assertTrue( TestUtil.extractIds( workshopTemplateService.findAllWorkshopTemplates() )
				.contains( findable.getID() ) );
	}

	@TestOrder( order = 5 ) @Test public void testRemove()
	{
		PinkElefantTemplate removable = (PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID(
				workshopTemplate.getID() );
		assertTrue( TestUtil.extractIds( workshopTemplateService.findAllWorkshopTemplates() )
				.contains( removable.getID() ) );

		workshopTemplateService.remove( workshopTemplate );
		assertTrue( workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() ) == null );
		assertTrue( !TestUtil.extractIds( workshopTemplateService.findAllWorkshopTemplates() )
				.contains( removable.getID() ) );
	}
}
