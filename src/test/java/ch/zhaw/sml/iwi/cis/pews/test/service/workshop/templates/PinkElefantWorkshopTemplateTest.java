package ch.zhaw.sml.iwi.cis.pews.test.service.workshop.templates;

import static org.junit.Assert.assertTrue;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.service.RestServiceTest;
import org.apache.derby.client.am.UpdateSensitiveBlobLocatorInputStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

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
public class PinkElefantWorkshopTemplateTest
{
	private static WorkshopTemplateService workshopTemplateService = RestServiceTest.workshopTemplateService;

	private static String NAME        = "name";
	private static String DESCRIPTION = "description";
	private static String PROBLEM     = "problem";
	private static String EMAIL       = "email";

	private WorkshopTemplate workshopTemplate    = new PinkElefantTemplate();
	private ExerciseTemplate exerciseTemplateOne = new PinkLabsTemplate();
	private ExerciseTemplate exerciseTemplateTwo = new PinkLabsTemplate();

	@Test public void testPersist()
	{
		workshopTemplate.setID( workshopTemplateService.persist( new PinkElefantTemplate( null,
				NAME,
				DESCRIPTION,
				PROBLEM,
				EMAIL ) ) );

		exerciseTemplateOne.setID( RestServiceTest.exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
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

		exerciseTemplateTwo.setID( RestServiceTest.exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
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

	@Test public void testFind()
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

	@Test public void testUpdateExerciseTemplateOrder()
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

	@Test public void testFindAll()
	{
		PinkElefantTemplate findable = (PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID(
				workshopTemplate.getID() );
		assertTrue( workshopTemplateService.findAllWorkshopTemplates().contains( findable ) );
	}

	@Test public void testRemove()
	{
		PinkElefantTemplate removable = (PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID(
				workshopTemplate.getID() );
		assertTrue( workshopTemplateService.findAllWorkshopTemplates().contains( removable ) );

		workshopTemplateService.remove( workshopTemplate );
		assertTrue( workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() ) == null );
		assertTrue( !workshopTemplateService.findAllWorkshopTemplates().contains( removable ) );
	}
}
