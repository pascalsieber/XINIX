package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.input.PosterInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PosterExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link PosterExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
public class PosterExerciseTest
{
	private ExerciseService exerciseService;
	private WorkshopService workshopService;

	private ExerciseImpl     exercise         = new PosterExercise();
	private ExerciseTemplate exerciseTemplate = new PosterTemplate();
	private WorkshopImpl     workshop         = new WorkshopImpl();

	private static String NAME        = "name";
	private static String DESCRIPTION = "description";

	private static boolean  TIMED             = false;
	private static TimeUnit TIMEUNIT          = TimeUnit.MINUTES;
	private static int      DURATION          = 10;
	private static boolean  SHARING           = false;
	private static boolean  SKIPPABLE         = false;
	private static boolean  COUNTABLE         = false;
	private static int      CARDINALITY       = 10;
	private static String   QUESTION_TEMPLATE = "question";

	private static String POSTER_TITLE       = "postertitle";
	private static String POSTER_DESCRIPTION = "posterdescription";
	private static String POSTER_IMAGE       = "posterimage";
	private static String POSTER_VIDEO       = "postervideo";

	@BeforeClass public void setup()
	{
		// services
		exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );

		// workshop
		WorkshopTemplate workshopTemplate = workshopTemplateService.findWorkshopTemplateByID( workshopTemplateService.persist(
				new WorkshopTemplate( null, "", "" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new PosterTemplate( null,
				TIMED,
				TIMEUNIT,
				DURATION,
				SHARING,
				SKIPPABLE,
				COUNTABLE,
				CARDINALITY,
				workshopTemplate,
				QUESTION_TEMPLATE,
				"",
				"",
				POSTER_TITLE,
				POSTER_DESCRIPTION,
				new HashSet<String>( Collections.singletonList( POSTER_IMAGE ) ),
				new HashSet<String>( Collections.singletonList( POSTER_VIDEO ) ) ) ) );
	}

	@Test public void testPersist()
	{
		exercise.setID( exerciseService.persistExercise( new PosterExercise( NAME,
				DESCRIPTION,
				(PosterTemplate)exerciseTemplate,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@Test public void testFind()
	{
		PosterExercise found = (PosterExercise)exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( exercise.getID() ) );

		assertTrue( found.getCurrentState().equals( WorkflowElementStatusImpl.NEW.toString() ) );

		assertTrue( found.getData().isEmpty() );

		assertTrue( found.getWorkshop().getID().equals( workshop.getID() ) );
		assertTrue( found.getOrderInWorkshop() == 0 );
		assertTrue( found.getDerivedFrom().getID().equals( exerciseTemplate.getID() ) );

		assertTrue( found.getName().equals( NAME ) );
		assertTrue( found.getDescription().equals( DESCRIPTION ) );

		assertTrue( found.isTimed() == TIMED );
		assertTrue( found.getTimeUnit().toString().equals( TIMEUNIT.toString() ) );
		assertTrue( found.getDuration() == DURATION );
		assertTrue( found.isSharing() == SHARING );
		assertTrue( found.isSkippable() == SKIPPABLE );
		assertTrue( found.isCountable() == COUNTABLE );
		assertTrue( found.getCardinality() == CARDINALITY );
		assertTrue( found.getQuestion().equals( QUESTION_TEMPLATE ) );

		assertTrue( found.getPosterTitle().equals( POSTER_TITLE ) );
		assertTrue( found.getPosterDescription().equals( POSTER_DESCRIPTION ) );
		assertTrue( found.getPosterImages().contains( POSTER_IMAGE ) );
		assertTrue( found.getPosterVideos().contains( POSTER_VIDEO ) );
	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@Test public void testGetInput()
	{
		PosterExercise base = (PosterExercise)exerciseService.findExerciseByID( exercise.getID() );
		PosterInput input = (PosterInput)exerciseService.getInputByExerciseID( exercise.getID() );

		assertTrue( input.getExerciseID().equals( base.getID() ) );
		assertTrue( input.getExerciseType().equals( base.getClass().getSimpleName() ) );
		assertTrue( input.getExerciseOrderInWorkshop().equals( base.getOrderInWorkshop() ) );
		assertTrue( input.getExerciseCurrentState().equals( base.getCurrentState() ) );

		assertTrue( input.isTimed() == base.isTimed() );
		assertTrue( input.getTimeUnit().toString().equals( base.getTimeUnit().toString() ) );
		assertTrue( input.getDuration() == base.getDuration() );
		assertTrue( input.isSharing() == base.isSharing() );
		assertTrue( input.isSkippable() == base.isSkippable() );
		assertTrue( input.isCountable() == base.isCountable() );
		assertTrue( input.getCardinality() == base.getCardinality() );
		assertTrue( input.getHelp().equals( base.getDescription() ) );

		assertTrue( input.getTitle().equals( POSTER_TITLE ) );
		assertTrue( input.getDescription().equals( POSTER_DESCRIPTION ) );
		assertTrue( input.getPosterImages().contains( POSTER_IMAGE ) );
		assertTrue( input.getPosterVideos().contains( POSTER_VIDEO ) );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@Test public void testSetOutput()
	{
		// not used. poster exercise does not generate any output
	}

	@Test public void testGetOutput()
	{
		// not used. poster exercise does not generate any output
	}

	@Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( findable ) );
	}

	@Test public void testRemove()
	{
		ExerciseImpl removable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( removable ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !exerciseService.findAllExercises().contains( removable ) );
		assertTrue( !workshopService.findWorkshopByID( workshop.getID() ).getExercises().contains( removable ) );
	}
}

