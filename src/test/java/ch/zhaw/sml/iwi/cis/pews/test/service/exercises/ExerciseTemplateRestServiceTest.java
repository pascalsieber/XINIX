package ch.zhaw.sml.iwi.cis.pews.test.service.exercises;

import static org.junit.Assert.assertTrue;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.*;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
import ch.zhaw.sml.iwi.cis.pews.test.service.RestServiceTest;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by fueg on 09.08.2016.
 * <p>
 * tests
 * {@link ch.zhaw.iwi.cis.pews.service.rest.ExerciseTemplateRestService}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 */
public class ExerciseTemplateRestServiceTest
{

	private WorkshopTemplate workshopTemplate;

	@BeforeClass @Test public void setupWorkshopTemplate()
	{
		workshopTemplate = RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( RestServiceTest.workshopTemplateService
				.persist( new PinkElefantTemplate( null, "name", "description", "problem", "emailtext" ) ) );
	}

	@Test public void testPinklabs()
	{
		PinkLabsTemplate template = (PinkLabsTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new PinkLabsTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"pinklabsquestiontemplate",
						"pinklabstemplatedefaultname",
						"pinklabstemplatedefaultdescription" ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "pinklabsquestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "pinklabstemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "pinklabstemplatedefaultdescription" ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testP2pOne()
	{
		P2POneTemplate template = (P2POneTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new P2POneTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"p2ponetemplatequestiontemplate",
						"p2ponetemplatedefaultname",
						"p2ponetemplatedefaultdescription",
						"p2ponetemplatepicture" ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "p2ponetemplatequestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "p2ponetemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "p2ponetemplatedefaultdescription" ) );
		assertTrue( template.getPicture().equals( "p2ponetemplatepciture" ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testP2pTwo()
	{
		P2PTwoTemplate template = (P2PTwoTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new P2PTwoTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"p2ptwotemplatequestiontemplate",
						"p2ptwotemplatedefaultname",
						"p2ptwotemplatedefaultdescription" ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "p2ptwotemplatequestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "p2ptwotemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "p2ptwotemplatedefaultdescription" ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testPoster()
	{
		PosterTemplate template = (PosterTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new PosterTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"postertemplatequestiontemplate",
						"postertemplatedefaultname",
						"postertemplatedefaultdescription",
						"postertemplatepostertitle",
						"postertemplateposterdescription",
						new HashSet<String>( Arrays.asList( "postertemplateimageone", "postertemplateimagetwo" ) ),
						new HashSet<String>( Arrays.asList( "postertemplatevideoone",
								"postertemplatevideotwo" ) ) ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "postertemplatequestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "postertemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "postertemplatedefaultdescription" ) );
		assertTrue( template.getPosterTitle().equals( "postertemplatepostertitle" ) );
		assertTrue( template.getPosterDescription().equals( "postertemplateposterdescription" ) );
		assertTrue( template.getPosterImages()
				.containsAll( Arrays.asList( "postertemplateimageone", "postertempalteimagetwo" ) ) );
		assertTrue( template.getPosterVideos()
				.containsAll( Arrays.asList( "postertemplatevideoone", "postertemplatevideotwo" ) ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testXinix()
	{
		XinixImageMatrix xinixImageMatrix = RestServiceTest.xinixImageMatrixService.findXinixImageMatrixByID(
				RestServiceTest.xinixImageMatrixService.persist( new XinixImageMatrix( new ArrayList<MediaObject>() ) ) );

		XinixTemplate template = (XinixTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new XinixTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"xinixtemplatequestiontemplate",
						"xinixtemplatedefaultname",
						"xinixtemplatedefaultdescription",
						xinixImageMatrix ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "xinixtemplatequestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "xinixtemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "xinixtemplatedefaultdescription" ) );
		assertTrue( template.getImages().getID().equals( xinixImageMatrix.getID() ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testSimpleproto()
	{
		SimplyPrototypingTemplate template = (SimplyPrototypingTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new SimplyPrototypingTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"simpleprototypingquestiontemplate",
						"simpleprototypingtemplatedefaultname",
						"simpleprototypingtemplatedefaultdescription",
						"mimetype" ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "simpleprototypingquestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "simpleprototypingtemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "simpleprototypingtemplatedefaultdescription" ) );
		assertTrue( template.getMimeType().equals( "mimetype" ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testCompression()
	{
		CompressionTemplate template = (CompressionTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new CompressionTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"compressionquestiontemplate",
						"compressiontemplatedefaultname",
						"compressiontemplatedefaultdescription",
						Arrays.asList( "solutioncriterionone", "solutioncriteriontwo" ) ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "compressionquestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "compressiontemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "compressiontemplatedefaultdescription" ) );
		assertTrue( template.getSolutionCriteria()
				.containsAll( Arrays.asList( "solutioncriterionone", "solutioncriteriontwo" ) ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testYou2me()
	{
		You2MeTemplate template = (You2MeTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new You2MeTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"you2mequestiontemplate",
						"you2metemplatedefaultname",
						"you2metemplatedefaultdescription",
						new HashSet<String>( Arrays.asList( "questionone", "questiontwo" ) ) ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "you2mequestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "you2metemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "you2metemplatedefaultdescription" ) );
		assertTrue( template.getQuestions().containsAll( Arrays.asList( "questionone", "questiontwo" ) ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testEvaluation()
	{
		EvaluationTemplate template = (EvaluationTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new EvaluationTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"evaluationquestiontemplate",
						"evaluationtemplatedefaultname",
						"evaluationtemplatedefaultdescription",
						3 ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "evaluationquestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "evaluationtemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "evaluationtemplatedefaultdescription" ) );
		assertTrue( template.getNumberOfVotes() == 3 );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}

	@Test public void testEvaluationResult()
	{
		EvaluationResultTemplate template = (EvaluationResultTemplate)RestServiceTest.exTemplateService.findExerciseTemplateByID(
				RestServiceTest.exTemplateService.persist( new EvaluationResultTemplate( null,
						true,
						TimeUnit.MINUTES,
						2,
						true,
						true,
						true,
						2,
						workshopTemplate,
						"evaluationresultquestiontemplate",
						"evaluationresulttemplatedefaultname",
						"evaluationresulttemplatedefaultdescription" ) ) );

		assertTrue( template != null );
		assertTrue( template.isTimed() );
		assertTrue( template.getTimeUnit().equals( TimeUnit.MINUTES ) );
		assertTrue( template.getDuration() == 2 );
		assertTrue( template.isSharing() );
		assertTrue( template.isSkippable() );
		assertTrue( template.isCountable() );
		assertTrue( template.getCardinality() == 2 );
		assertTrue( template.getWorkshopTemplate().getID().equals( workshopTemplate.getID() ) );
		assertTrue( template.getQuestionTemplate().equals( "evaluationresultquestiontemplate" ) );
		assertTrue( template.getDefaultName().equals( "evaluationresulttemplatedefaultname" ) );
		assertTrue( template.getDefaultDescription().equals( "evaluationresulttemplatedefaultdescription" ) );
		assertTrue( template.getOrderInWorkshopTemplate().equals( 0 ) );

		assertTrue( RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );

		RestServiceTest.exerciseTemplateService.remove( template );
		assertTrue( RestServiceTest.exTemplateService.findExerciseTemplateByID( template.getID() ) == null );
		assertTrue( !RestServiceTest.exTemplateService.findAllExerciseTemplates().contains( template ) );
		assertTrue( RestServiceTest.workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() )
				.getExerciseTemplates()
				.isEmpty() );
	}
}
