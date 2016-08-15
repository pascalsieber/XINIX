package ch.zhaw.sml.iwi.cis.pews.test.service.exercises;

import static org.junit.Assert.assertTrue;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseTemplateRestService;
import ch.zhaw.iwi.cis.pews.service.rest.RestService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.*;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.*;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.service.RestServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by fueg on 09.08.2016.
 */
public class ExerciseRestServiceTest
{

	private WorkshopTemplate workshopTemplate;
	private WorkshopImpl     workshop;

	private XinixImageMatrix xinixImageMatrix;

	private PosterTemplate            posterTemplate;
	private PinkLabsTemplate          pinkLabsTemplate;
	private P2POneTemplate            p2POneTemplate;
	private P2PTwoTemplate            p2PTwoTemplate;
	private XinixTemplate             xinixTemplate;
	private SimplyPrototypingTemplate simplyPrototypingTemplate;
	private You2MeTemplate            you2MeTemplate;
	private CompressionTemplate       compressionTemplate;
	private EvaluationTemplate        evaluationTemplate;
	private EvaluationResultTemplate  evaluationResultTemplate;

	private PosterExercise            posterExercise;
	private PinkLabsExercise          pinkLabsExercise;
	private P2POneExercise            p2POneExercise;
	private P2PTwoExercise            p2PTwoExercise;
	private XinixExercise             xinixExercise;
	private SimplyPrototypingExercise simplyPrototypingExercise;
	private You2MeExercise            you2MeExercise;
	private CompressionExercise       compressionExercise;
	private EvaluationExercise        evaluationExercise;
	private EvaluationResultExercise  evaluationResultExercise;

	/**
	 * initializes exercise template.
	 * <p>
	 * tests
	 * {@link ExerciseTemplateRestService}
	 * - PERSIST
	 * - FIND_BY_ID
	 */
	@BeforeClass public void setupExerciseTemplates()
	{
		// setup xinix image matrix RestServiceTest.mediaService
		MediaObject xinixImageOne = RestServiceTest.mediaService.findByID( RestServiceTest.mediaService.persist( new MediaObject( "mime",
				"blobxiniximageone".getBytes(),
				MediaObjectType.XINIX ) ) );
		MediaObject xinixImageTwo = RestServiceTest.mediaService.findByID( RestServiceTest.mediaService.persist( new MediaObject( "mime",
				"blobxiniximagetwo".getBytes(),
				MediaObjectType.XINIX ) ) );

		xinixImageMatrix = (XinixImageMatrix)RestServiceTest.xinixImageMatrixService.findXinixImageMatrixByID(
				RestServiceTest.xinixImageMatrixService.persist( new XinixImageMatrix( Arrays.asList( xinixImageOne,
						xinixImageTwo ) ) ) );

		// setup workshop template
		workshopTemplate = RestServiceTest.workshopTemplateService.findByID( RestServiceTest.workshopTemplateService.persist(
				new PinkElefantTemplate( null,
						"workshoptemplatename",
						"workshoptemplatedescription",
						"workshoptemplateproblem",
						"workshoptemplateemail" ) ) );

		// setup exercise template
		posterTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new PosterTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				1,
				true,
				true,
				true,
				1,
				workshopTemplate,
				"postertemplatequestion",
				"postertemplatename",
				"postertemplatedescription",
				"postertemplatetitle",
				"postertempaltedescription",
				new HashSet<String>( Arrays.asList( "posterimageone", "posterimagetwo" ) ),
				new HashSet<String>( Arrays.asList( "postervideoone", "postervideotwo" ) ) ) ) );

		pinkLabsTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new PinkLabsTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				2,
				true,
				true,
				true,
				2,
				workshopTemplate,
				"pinklabstemplatequestion",
				"pinklabstemplatename",
				"pinklabstemplatedescription" ) ) );

		p2POneTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new P2POneTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				3,
				true,
				true,
				true,
				3,
				workshopTemplate,
				"p2ponetemplatequestion",
				"p2ponetemplatename",
				"p2ponetemplatedescription",
				"p2ponepicture" ) ) );

		p2PTwoTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new P2PTwoTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				4,
				true,
				true,
				true,
				4,
				workshopTemplate,
				"p2ptwotemplatequestion",
				"p2ptwotemplatename",
				"p2ptwotemplatedescription" ) ) );

		xinixTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new XinixTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				5,
				true,
				true,
				true,
				5,
				workshopTemplate,
				"xinixtemplatequestion",
				"xinixtemplatename",
				"xinixtemplatedescription",
				xinixImageMatrix ) ) );

		simplyPrototypingTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist(
				new SimplyPrototypingTemplate( null,
						true,
						TimeUnit.MINUTES,
						6,
						true,
						true,
						true,
						6,
						workshopTemplate,
						"simplyprototypingtemplatequestion",
						"simplyprototypingtemplatename",
						"simplyprototypingtemplatedescription",
						"simplyprototypingtemplatemimetype" ) ) );

		you2MeTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new You2MeTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				7,
				true,
				true,
				true,
				7,
				workshopTemplate,
				"you2metemplatequestion",
				"you2metemplatename",
				"you2metemplatedescription",
				new HashSet<String>( Arrays.asList( "you2metampltequestionone", "you2metemplatequestiontwo" ) ) ) ) );

		compressionTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new CompressionTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				8,
				true,
				true,
				true,
				8,
				workshopTemplate,
				"compressiontemplatequestion",
				"compressiontemplatename",
				"compressiontemplatedescription",
				Arrays.asList( "solutioncriterionone", "solutioncriteriontwo" ) ) ) );

		evaluationTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist( new EvaluationTemplate(
				null,
				true,
				TimeUnit.MINUTES,
				9,
				true,
				true,
				true,
				9,
				workshopTemplate,
				"evaluationtemplatequestion",
				"evaluationtemplatename",
				"evaluationtemplatedescription",
				4 ) ) );

		evaluationResultTemplate = RestServiceTest.exTemplateService.findByID( RestServiceTest.exTemplateService.persist(
				new EvaluationResultTemplate( null,
						true,
						TimeUnit.MINUTES,
						10,
						true,
						true,
						true,
						10,
						workshopTemplate,
						"evalutionresulttemplatequestion",
						"evalutionresulttemplatename",
						"evaluationresulttemplatedescription" ) ) );
	}

	/**
	 * clean-up exercise template
	 * <p>
	 * tests
	 * {@link ExerciseTemplateRestService}
	 * - REMOVE
	 * - FIND_ALL
	 */
	@AfterClass public void cleanupExerciseTemplates()
	{
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( posterTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( pinkLabsTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( p2POneTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( p2PTwoTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( xinixTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( simplyPrototypingTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( you2MeTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( compressionTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( evaluationTemplate ) );
		assertTrue( RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( evaluationResultTemplate ) );

		RestServiceTest.exerciseTemplateService.remove( posterTemplate );
		RestServiceTest.exerciseTemplateService.remove( pinkLabsTemplate );
		RestServiceTest.exerciseTemplateService.remove( p2POneTemplate );
		RestServiceTest.exerciseTemplateService.remove( p2PTwoTemplate );
		RestServiceTest.exerciseTemplateService.remove( xinixTemplate );
		RestServiceTest.exerciseTemplateService.remove( simplyPrototypingTemplate );
		RestServiceTest.exerciseTemplateService.remove( you2MeTemplate );
		RestServiceTest.exerciseTemplateService.remove( compressionTemplate );
		RestServiceTest.exerciseTemplateService.remove( evaluationTemplate );
		RestServiceTest.exerciseTemplateService.remove( evaluationResultTemplate );

		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( posterTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( pinkLabsTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( p2POneTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( p2PTwoTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( xinixTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( simplyPrototypingTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates().contains( you2MeTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( compressionTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( evaluationTemplate ) );
		assertTrue( !RestServiceTest.exerciseTemplateService.findAllExerciseTemplates()
				.contains( evaluationResultTemplate ) );
	}

	/**
	 * clean-up exercises
	 * <p>
	 * tests
	 * {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService}
	 * - REMOVE
	 * - FIND_ALL
	 */
	@AfterClass public void cleanupExercises()
	{
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( posterExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( pinkLabsExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( p2POneExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( p2PTwoExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( xinixExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises()
				.contains( simplyPrototypingExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( you2MeExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises()
				.contains( compressionExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises().contains( evaluationExercise ) );
		assertTrue( RestServiceTest.exerciseService.findAllExercises()
				.contains( evaluationResultExercise ) );

		RestServiceTest.exerciseService.remove( posterExercise );
		RestServiceTest.exerciseService.remove( pinkLabsExercise );
		RestServiceTest.exerciseService.remove( p2POneExercise );
		RestServiceTest.exerciseService.remove( p2PTwoExercise );
		RestServiceTest.exerciseService.remove( xinixExercise );
		RestServiceTest.exerciseService.remove( simplyPrototypingExercise );
		RestServiceTest.exerciseService.remove( you2MeExercise );
		RestServiceTest.exerciseService.remove( compressionExercise );
		RestServiceTest.exerciseService.remove( evaluationExercise );
		RestServiceTest.exerciseService.remove( evaluationResultExercise );

		assertTrue( !RestServiceTest.exerciseService.findAllExercises().contains( posterExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises().contains( pinkLabsExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises().contains( p2POneExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises().contains( p2PTwoExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises().contains( xinixExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises()
				.contains( simplyPrototypingExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises().contains( you2MeExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises()
				.contains( compressionExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises()
				.contains( evaluationExercise ) );
		assertTrue( !RestServiceTest.exerciseService.findAllExercises()
				.contains( evaluationResultExercise ) );
	}

	/**
	 * tests
	 * {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService}
	 * - PERSIST
	 * - FIND_BY_ID
	 */
	@Test public void persistAndFindExercises()
	{
		// poster
		posterExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new PosterExercise(
				"postername",
				"posterdescription",
				(PosterTemplate)RestServiceTest.exerciseTemplateService.findByID( posterTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// pinklabs
		pinkLabsExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new PinkLabsExercise(
				"pinklabsname",
				"pinklabsdescription",
				(PinkLabsTemplate)RestServiceTest.exerciseTemplateService.findByID( pinkLabsTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// p2pone
		p2POneExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new P2POneExercise(
				"p2ponename",
				"p2ponedescription",
				(P2POneTemplate)RestServiceTest.exerciseTemplateService.findByID( p2POneTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// p2ptwo
		p2PTwoExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new P2PTwoExercise(
				"p2ponename",
				"p2ponedescription",
				(P2PTwoTemplate)RestServiceTest.exerciseTemplateService.findByID( p2PTwoTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// xinix
		xinixExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new XinixExercise(
				"xinixname",
				"xinixdescription",
				(XinixTemplate)RestServiceTest.exerciseTemplateService.findByID( xinixTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// simply prototyping
		simplyPrototypingExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist(
				new SimplyPrototypingExercise( "simpleyprototypingname",
						"simplyprototypingdescription",
						(SimplyPrototypingTemplate)RestServiceTest.exerciseTemplateService.findByID(
								simplyPrototypingTemplate.getID() ),
						RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// you2me
		you2MeExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new You2MeExercise(
				"you2mename",
				"you2medescription",
				(You2MeTemplate)RestServiceTest.exerciseTemplateService.findByID( you2MeTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// compression
		compressionExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new CompressionExercise(
				"compressionname",
				"compressiondescription",
				(CompressionTemplate)RestServiceTest.exerciseTemplateService.findByID( compressionTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// evaluation
		evaluationExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist( new EvaluationExercise(
				"evaluationname",
				"evaluationdescription",
				(EvaluationTemplate)RestServiceTest.exerciseTemplateService.findByID( evaluationTemplate.getID() ),
				RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );

		// evaluation result
		evaluationResultExercise = RestServiceTest.exerciseService.findByID( RestServiceTest.exerciseService.persist(
				new EvaluationResultExercise( "evaluationresultname",
						"evaluationresultdescription",
						(EvaluationResultTemplate)RestServiceTest.exerciseTemplateService.findByID(
								evaluationResultTemplate.getID() ),
						RestServiceTest.workshopService.findWorkshopByID( workshop.getID() ) ) ) );
	}

	// getInputByExerciseID

	// setOutputByExerciseID

	// getOutput

	// generateFromTemplate
}
