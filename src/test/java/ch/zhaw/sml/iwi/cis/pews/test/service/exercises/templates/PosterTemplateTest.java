package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.templates;

import static org.junit.Assert.assertTrue;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseTemplateRestService} with {@link PosterTemplate}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
public class PosterTemplateTest
{
	private static ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
			ExerciseTemplateServiceProxy.class );
	private static WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
			WorkshopTemplateServiceProxy.class );

	private static String   QUESTIONTEMPLATE = "questiontemplate";
	private static String   NAME             = "name";
	private static String   DESCRIPTION      = "description";
	private static int      NUMBER           = 4;
	private static TimeUnit TIMEUNIT         = TimeUnit.MINUTES;

	private static String POSTER_TITLE       = "postername";
	private static String POSTER_DESCRIPTION = "posterdescription";
	private static String IMAGE              = "image";
	private static String VIDEO              = "video";

	private ExerciseTemplate exerciseTemplate = new PosterTemplate();

	private WorkshopTemplate workshopTemplate = new WorkshopTemplate();

	@BeforeClass public void setup()
	{
		workshopTemplate.setID( workshopTemplateService.persist( new WorkshopTemplate( null,
				"",
				"" ) ) );
	}

	@Test public void testPersist()
	{
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new PosterTemplate( null,
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
				POSTER_TITLE,
				POSTER_DESCRIPTION,
				new HashSet<String>( Arrays.asList( IMAGE ) ),
				new HashSet<String>( Arrays.asList( VIDEO ) ) ) ) );

		assertTrue( exerciseTemplate.getID() != null );
		assertTrue( !exerciseTemplate.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		PosterTemplate found = (PosterTemplate)exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() );
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

		assertTrue( found.getPosterTitle().equals( POSTER_TITLE ) );
		assertTrue( found.getPosterDescription().equals( POSTER_DESCRIPTION ) );
		assertTrue( found.getPosterImages().contains( IMAGE ) );
		assertTrue( found.getPosterVideos().contains( VIDEO ) );
	}

	@Test public void testFindAll()
	{
		PosterTemplate findable = (PosterTemplate)exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() );
		assertTrue( exerciseTemplateService.findAllExerciseTemplates().contains( findable ) );
	}

	@Test public void testRemove()
	{
		PosterTemplate removable = (PosterTemplate)exerciseTemplateService.findExerciseTemplateByID( exerciseTemplate.getID() );
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
