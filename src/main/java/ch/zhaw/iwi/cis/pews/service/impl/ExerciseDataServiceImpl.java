package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.CompressionExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.EvaluationExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.P2POneExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.P2PTwoExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.PinkLabsExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.SimplePrototypingExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.XinixExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.You2MeExerciseDataService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.SimplePrototypingDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.You2MeDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDataServiceImpl extends WorkshopObjectServiceImpl implements ExerciseDataService
{
	private ExerciseDataDao exerciseDataDao;
	private ExerciseDao exerciseDao;

	private static final Map< String, Class< ? extends ExerciseDataServiceImpl > > EXERCISESPECIFICDATASERVICES = new HashMap< String, Class< ? extends ExerciseDataServiceImpl > >();

	static
	{
		EXERCISESPECIFICDATASERVICES.put( PinkLabsDefinition.class.getSimpleName(), PinkLabsExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( You2MeDefinition.class.getSimpleName(), You2MeExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( P2POneDefinition.class.getSimpleName(), P2POneExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( P2PTwoDefinition.class.getSimpleName(), P2PTwoExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( SimplePrototypingDefinition.class.getSimpleName(), SimplePrototypingExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( XinixDefinition.class.getSimpleName(), XinixExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( CompressionDefinition.class.getSimpleName(), CompressionExerciseDataService.class );
		EXERCISESPECIFICDATASERVICES.put( EvaluationDefinition.class.getSimpleName(), EvaluationExerciseDataService.class );
	}

	private Class< ? > getExerciseDataSpecificService( String exerciseDefinitionClassName )
	{
		return EXERCISESPECIFICDATASERVICES.get( exerciseDefinitionClassName );
	}

	public ExerciseDataServiceImpl()
	{
		exerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataDaoImpl.class.getSimpleName() );
		exerciseDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseDataDao;
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		ExerciseImpl ex = exerciseDao.findById( exerciseID );
		String defName = ex.getDefinition().getClass().getSimpleName();
		Class< ? > serviceClass = getExerciseDataSpecificService( defName );
		ExerciseDataService service = ZhawEngine.getManagedObjectRegistry().getManagedObject( serviceClass.getSimpleName() );
		return service.findByExerciseID( exerciseID );
	}

	public List< ExerciseDataImpl > genericFindByExerciseID( String exerciseID )
	{
		return exerciseDataDao.findByExerciseID( exerciseID );
	}
}
