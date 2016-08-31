package ch.zhaw.sml.iwi.cis.pews.test.service.exercises.instances;

import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.*;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationResultExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.util.OrderedRunner;
import ch.zhaw.sml.iwi.cis.pews.test.util.TestOrder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by fueg on 12.08.2016.
 * <p>
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService} with {@link EvaluationResultExercise}
 * - PERSIST
 * - FIND_BY_ID
 * - GETINPUT_BY_EXERCISEID
 * - SETOUTPUT_BY_EXERCISEID
 * - GETOUTPUT
 * - REMOVE
 * - FIND_ALL
 */
@RunWith( OrderedRunner.class ) public class EvaluationResultExerciseTest
{
	private static ExerciseService exerciseService;
	private static WorkshopService workshopService;

	private static ExerciseImpl     exercise         = new EvaluationResultExercise();
	private static ExerciseTemplate exerciseTemplate = new EvaluationResultTemplate();
	private static WorkshopImpl     workshop         = new WorkshopImpl();

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

	private static String SOLUTION_VOTED        = "solutionvoted";
	private static String DESCRIPTION_NOT_VOTED = "descriptionnotvoted";
	private static String SOLUTION_NOT_VOTED    = "solutionnotvoted";
	private static String DESCRIPTION_VOTED     = "descriptionvoted";

	private static int SCORE_ONE = 4;
	private static int SCORE_TWO = 5;

	@BeforeClass public void setup()
	{
		// services
		exerciseService = ServiceProxyManager.createServiceProxy( ExerciseServiceProxy.class );
		WorkshopTemplateService workshopTemplateService = ServiceProxyManager.createServiceProxy(
				WorkshopTemplateServiceProxy.class );
		workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );
		ExerciseDataService exerciseDataService = ServiceProxyManager.createServiceProxy( ExerciseDataServiceProxy.class );

		// workshop
		WorkshopTemplate workshopTemplate = workshopTemplateService.findWorkshopTemplateByID( workshopTemplateService.persist(
				new WorkshopTemplate( null, "", "" ) ) );
		workshop.setID( workshopService.persist( new WorkshopImpl( "", "", workshopTemplate ) ) );

		// exercise template
		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new EvaluationResultTemplate( null,
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
				"" ) ) );

		// compression exercise
		CompressionTemplate compressionTemplate = (CompressionTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new CompressionTemplate( null,
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
						"",
						new ArrayList<String>() ) ) );
		CompressionExercise compressionExercise = (CompressionExercise)exerciseService.findExerciseByID( exerciseService
				.persistExercise( new CompressionExercise( "", "", compressionTemplate, workshop ) ) );

		// compression data
		CompressionExerciseData evaluated = (CompressionExerciseData)exerciseDataService.findExerciseDataByID(
				exerciseDataService.persistExerciseData( new CompressionExerciseData( null,
						compressionExercise,
						Collections.singletonList( new CompressionExerciseDataElement( SOLUTION_VOTED,
								DESCRIPTION_VOTED ) ) ) ) );

		exerciseDataService.persistExerciseData( new CompressionExerciseData( null,
				compressionExercise,
				Collections.singletonList( new CompressionExerciseDataElement( SOLUTION_NOT_VOTED,
						DESCRIPTION_NOT_VOTED ) ) ) );
		// evaluation exercise
		EvaluationTemplate evaluationTemplate = (EvaluationTemplate)exerciseTemplateService.findExerciseTemplateByID(
				exerciseTemplateService.persistExerciseTemplate( new EvaluationTemplate( null,
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
						"",
						0 ) ) );

		EvaluationExercise evaluationExercise = (EvaluationExercise)exerciseService.findExerciseByID( exerciseService.persistExercise(
				new EvaluationExercise( "", "", evaluationTemplate, workshop ) ) );

		// evaluation data
		exerciseDataService.persistExerciseData( new EvaluationExerciseData( null,
				evaluationExercise,
				new Evaluation( null, evaluated.getSolutions().get( 0 ), new Score( null, SCORE_ONE ) ) ) );
		exerciseDataService.persistExerciseData( new EvaluationExerciseData( null,
				evaluationExercise,
				new Evaluation( null, evaluated.getSolutions().get( 0 ), new Score( null, SCORE_TWO ) ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		exercise.setID( exerciseService.persistExercise( new EvaluationResultExercise( NAME,
				DESCRIPTION,
				(EvaluationResultTemplate)exerciseTemplate,
				workshop ) ) );
		assertTrue( exercise.getID() != null );
		assertTrue( !exercise.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		EvaluationResultExercise found = (EvaluationResultExercise)exerciseService.findExerciseByID( exercise.getID() );
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

	}

	// only testing getInputByExerciseID. getInput API method is 'syntactic sugar'
	// which ends up calling getInputByExerciseID
	@TestOrder( order = 3 ) @Test public void testGetInput()
	{
		EvaluationResultExercise base = (EvaluationResultExercise)exerciseService.findExerciseByID( exercise.getID() );
		EvaluationResultInput input = (EvaluationResultInput)exerciseService.getInputByExerciseID( exercise.getID() );

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

		assertTrue( input.getNotEvaluated().size() == 1 );
		assertTrue( input.getNotEvaluated().get( 0 ).getSolution().equals( SOLUTION_NOT_VOTED ) );
		assertTrue( input.getNotEvaluated().get( 0 ).getDescription().equals( DESCRIPTION_NOT_VOTED ) );
		assertTrue( input.getResults().size() == 1 );
		assertTrue( input.getResults().get( 0 ).getSolution().getSolution().equals( SOLUTION_VOTED ) );
		assertTrue( input.getResults().get( 0 ).getSolution().getDescription().equals( DESCRIPTION_VOTED ) );
		assertTrue( input.getResults().get( 0 ).getNumberOfVotes() == 2 );
		assertTrue( input.getResults().get( 0 ).getAverageScore() == ( SCORE_ONE + SCORE_TWO ) / 2 );
	}

	// only testing setOutputByExerciseID. setOutput API method is 'syntactic sugar'
	// which ends up calling setOutputByExerciseID
	@TestOrder( order = 4 ) @Test public void testSetOutput()
	{
		// not used. evaluation result exercise does not generate any output
	}

	@TestOrder( order = 5 ) @Test public void testGetOutput()
	{
		// not used. evaluation result exercise does not generate any output
	}

	@TestOrder( order = 6 ) @Test public void testFindAll()
	{
		ExerciseImpl findable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( findable ) );
	}

	@TestOrder( order = 7 ) @Test public void testRemove()
	{
		ExerciseImpl removable = exerciseService.findExerciseByID( exercise.getID() );
		assertTrue( exerciseService.findAllExercises().contains( removable ) );

		exerciseService.remove( removable );
		assertTrue( exerciseService.findExerciseByID( exercise.getID() ) == null );
		assertTrue( !exerciseService.findAllExercises().contains( removable ) );
		assertTrue( !workshopService.findWorkshopByID( workshop.getID() ).getExercises().contains( removable ) );
	}
}

