package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.iwi.cis.pews.dao.ExerciseTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopTemplateDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopTemplateDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.service.ExerciseTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.CompressionTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.EvaluationResultTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.EvaluationTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.P2POneTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.P2PTwoTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.PinkLabsTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.PosterTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.SimplyPrototypingTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.XinixTemplateService;
import ch.zhaw.iwi.cis.pews.service.exercise.template.impl.You2MeTemplateService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PosterTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseTemplateServiceImpl extends WorkshopObjectServiceImpl implements ExerciseTemplateService
{
	private ExerciseTemplateDao exerciseTemplateDao;
	private WorkshopTemplateDao workshopTemplateDao;

	private static final Map< Class< ? extends ExerciseTemplate >, Class< ? extends ExerciseTemplateServiceImpl > > EXERCISETEMPLATESPECIFICSERVICES = new HashMap< Class< ? extends ExerciseTemplate >, Class< ? extends ExerciseTemplateServiceImpl > >();

	static
	{
		EXERCISETEMPLATESPECIFICSERVICES.put( PinkLabsTemplate.class, PinkLabsTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( You2MeTemplate.class, You2MeTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( P2POneTemplate.class, P2POneTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( P2PTwoTemplate.class, P2PTwoTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( SimplyPrototypingTemplate.class, SimplyPrototypingTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( XinixTemplate.class, XinixTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( CompressionTemplate.class, CompressionTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( EvaluationTemplate.class, EvaluationTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( PosterTemplate.class, PosterTemplateService.class );
		EXERCISETEMPLATESPECIFICSERVICES.put( EvaluationResultTemplate.class, EvaluationResultTemplateService.class );
	}

	private Class< ? > getExerciseTemplateSpecificService( Class< ? extends ExerciseTemplate > exerciseTemplateClass )
	{
		return EXERCISETEMPLATESPECIFICSERVICES.get( exerciseTemplateClass );
	}

	public ExerciseTemplateServiceImpl()
	{
		exerciseTemplateDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseTemplateDaoImpl.class.getSimpleName() );
		workshopTemplateDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopTemplateDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseTemplateDao;
	}

	@Override
	public ExerciseTemplate findExerciseTemplateByID( String id )
	{
		ExerciseTemplate template = exerciseTemplateDao.findById( id );
		Class< ? > serviceClass = getExerciseTemplateSpecificService( template.getClass() );
		ExerciseTemplateService service = ZhawEngine.getManagedObjectRegistry().getManagedObject( serviceClass.getSimpleName() );
		return (ExerciseTemplate)simplifyOwnerInObjectGraph( service.findExerciseTemplateByID( id ) );
	}

	public ExerciseTemplate genericFindExerciseTemplateByID( String id )
	{
		return (ExerciseTemplate)simplifyOwnerInObjectGraph( findByID( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseTemplate > findAllExerciseTemplates()
	{
		return (List< ExerciseTemplate >)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override
	public void removeExerciseTemplate( ExerciseTemplate obj )
	{
		ExerciseTemplate exTemplate = exerciseTemplateDao.findById( obj.getID() );
		WorkshopTemplate wsDef = workshopTemplateDao.findById( exTemplate.getWorkshopTemplate().getID() );

		wsDef.getExerciseTemplates().remove( exTemplate );

		workshopTemplateDao.persist( wsDef );
		exerciseTemplateDao.remove( exTemplate );

	}

}
