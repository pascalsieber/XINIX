package ch.zhaw.sml.iwi.cis.pews.test.service.workshop.instances;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.WorkshopTemplateService;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ExerciseTemplateServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopServiceProxy;
import ch.zhaw.iwi.cis.pews.service.impl.proxy.WorkshopTemplateServiceProxy;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;
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
 * tests {@link ch.zhaw.iwi.cis.pews.service.rest.WorkshopRestService} with {@link ch.zhaw.iwi.cis.pinkelefant.workshop.instance.PinkElefantWorkshop}
 * - PERSIST
 * - FIND_BY_ID
 * - REMOVE
 * - FIND_ALL
 * - RESET
 * - UPDATE_EXERCISES_ORDER
 * - GENERATE_FROM_TEMPLATE
 * - UPDATE_BASIC_INFO
 */
@RunWith( OrderedRunner.class ) public class PinkElefantWorkshopTest
{
	private static WorkshopService         workshopService;
	private static WorkshopTemplateService workshopTemplateService;

	private static String NAME        = "name";
	private static String DESCRIPTION = "description";
	private static String PROBLEM     = "problem";
	private static String EMAIL       = "email";

	private static String  EXERCISE_TEMPLATE_NAME         = "templatename";
	private static String  EXERCISE_TEMPLATE_DESCRIPTION  = "templatedescription";
	private static String  EXERCISE_TEMPLATE_QUESTION     = "templatequestion";
	private static boolean EXERCISE_TEMPLATE_BOOL         = true;
	private static int     EXERCISE_TEMPLATE_NUMBER_PARAM = 4;

	private static WorkshopImpl workshop = new PinkElefantWorkshop();

	private static WorkshopTemplate workshopTemplate = new PinkElefantTemplate();
	private static ExerciseTemplate exerciseTemplate = new PinkLabsTemplate();
	private static ExerciseImpl     exerciseOne      = new PinkLabsExercise();
	private static ExerciseImpl     exerciseTwo      = new PinkLabsExercise();
	private static SessionImpl      session          = new SessionImpl();

	@BeforeClass public static void setup()
	{
		// services
		workshopService = ServiceProxyManager.createServiceProxy( WorkshopServiceProxy.class );
		workshopTemplateService = ServiceProxyManager.createServiceProxy( WorkshopTemplateServiceProxy.class );
		ExerciseTemplateService exerciseTemplateService = ServiceProxyManager.createServiceProxy(
				ExerciseTemplateServiceProxy.class );

		// workshop template
		workshopTemplate.setID( workshopTemplateService.persist( new PinkElefantTemplate( null,
				"templatename",
				"templatedescription",
				PROBLEM,
				EMAIL ) ) );

		exerciseTemplate.setID( exerciseTemplateService.persistExerciseTemplate( new PinkLabsTemplate( null,
				false,
				TimeUnit.MINUTES,
				EXERCISE_TEMPLATE_NUMBER_PARAM,
				EXERCISE_TEMPLATE_BOOL,
				EXERCISE_TEMPLATE_BOOL,
				EXERCISE_TEMPLATE_BOOL,
				EXERCISE_TEMPLATE_NUMBER_PARAM,
				workshopTemplate,
				EXERCISE_TEMPLATE_QUESTION,
				EXERCISE_TEMPLATE_NAME,
				EXERCISE_TEMPLATE_DESCRIPTION ) ) );
	}

	@TestOrder( order = 1 ) @Test public void testPersist()
	{
		workshop.setID( workshopService.persist( new PinkElefantWorkshop( NAME,
				DESCRIPTION,
				(PinkElefantTemplate)workshopTemplateService.findWorkshopTemplateByID( workshopTemplate.getID() ) ) ) );
		assertTrue( workshop.getID() != null );
		assertTrue( !workshop.getID().equals( "" ) );
	}

	@TestOrder( order = 2 ) @Test public void testFind()
	{
		PinkElefantWorkshop found = (PinkElefantWorkshop)workshopService.findWorkshopByID( workshop.getID() );
		assertTrue( found != null );
		assertTrue( found.getID().equals( workshop.getID() ) );
		assertTrue( found.getName().equals( NAME ) );
		assertTrue( found.getDescription().equals( DESCRIPTION ) );
		assertTrue( found.getProblem().equals( PROBLEM ) );
		assertTrue( found.getEmailText().equals( EMAIL ) );
		assertTrue( found.getDerivedFrom().getID().equals( workshopTemplate.getID() ) );
		assertTrue( found.getCurrentState().equals( WorkflowElementStatusImpl.NEW.toString() ) );
		assertTrue( found.getExercises().isEmpty() );
		assertTrue( found.getSessions().isEmpty() );
	}

	@TestOrder( order = 3 ) @Test public void testReset()
	{
	}

	@TestOrder( order = 4 ) @Test public void testUpdateExerciseOrder()
	{
	}

	@TestOrder( order = 5 ) @Test public void testGenerateFromTemplate()
	{
	}

	@TestOrder( order = 6 ) @Test public void testUpdateBasicInfo()
	{
	}

	@TestOrder( order = 7 ) @Test public void testFindAll()
	{
		PinkElefantWorkshop findable = (PinkElefantWorkshop)workshopService.findWorkshopByID( workshop.getID() );
		assertTrue( TestUtil.extractIds( workshopService.findAll() ).contains( findable.getID() ) );
	}

	@TestOrder( order = 8 ) @Test public void testRemove()
	{
		PinkElefantWorkshop removable = (PinkElefantWorkshop)workshopService.findWorkshopByID( workshop.getID() );
		assertTrue( TestUtil.extractIds( workshopService.findAll() ).contains( removable.getID() ) );

		workshopService.remove( workshop );
		assertTrue( workshopService.findWorkshopByID( workshop.getID() ) == null );
		assertTrue( !TestUtil.extractIds( workshopService.findAll() ).contains( removable.getID() ) );
	}
}
