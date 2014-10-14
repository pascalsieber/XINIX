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
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.CompressionExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.EvaluationExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.P2POneExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.P2PTwoExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.PinkLabsExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.SimplePrototypingExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.XinixExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.You2MeExerciseDataService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
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
		return cleanseData( service.findByExerciseID( exerciseID ) );
	}

	public List< ExerciseDataImpl > genericFindByExerciseID( String exerciseID )
	{
		return cleanseData( exerciseDataDao.findByExerciseID( exerciseID ) );
	}

	@SuppressWarnings( "unchecked" )
	private List< ExerciseDataImpl > cleanseData( List< ExerciseDataImpl > data )
	{
		List< ExerciseDataImpl > cleansed = (List< ExerciseDataImpl >)exerciseDataDao.cloneResult( data );
		for ( ExerciseDataImpl d : cleansed )
		{
			UserImpl oldUser = (UserImpl)d.getOwner();
			UserImpl newUser = new UserImpl( null, null, null, oldUser.getFirstName(), oldUser.getLastName(), oldUser.getLoginName() );
			newUser.setID( oldUser.getID() );
			newUser.setClient( oldUser.getClient() );
			d.setOwner( newUser );
			
			WorkflowElementImpl oldWF = d.getWorkflowElement();
			WorkflowElementImpl newWF = new WorkflowElementImpl( oldWF.getName(), oldWF.getDescription(), null );
			newWF.setClient( oldWF.getClient() );
			newWF.setID( oldWF.getID() );
			newWF.setStatusHistory( oldWF.getStatusHistory() );
			d.setWorkflowElement( newWF );
			
			if ( d instanceof P2POneData )
			{
				for ( P2POneKeyword keyword : ( (P2POneData)d ).getKeywords() )
				{
					UserImpl keywordOldUser = (UserImpl)keyword.getOwner();
					UserImpl keywordNewUser = new UserImpl( null, null, null, keywordOldUser.getFirstName(), keywordOldUser.getLastName(), keywordOldUser.getLoginName() );
					keywordNewUser.setID( keywordOldUser.getID() );
					keywordNewUser.setClient( keywordOldUser.getClient() );
					keyword.setOwner( keywordNewUser );
				}
			}
			
			if ( d instanceof P2PTwoData )
			{
				for ( P2POneKeyword keyword : ( (P2PTwoData)d ).getSelectedKeywords() )
				{
					UserImpl keywordOldUser = (UserImpl)keyword.getOwner();
					UserImpl keywordNewUser = new UserImpl( null, null, null, keywordOldUser.getFirstName(), keywordOldUser.getLastName(), keywordOldUser.getLoginName() );
					keywordNewUser.setID( keywordOldUser.getID() );
					keywordNewUser.setClient( keywordOldUser.getClient() );
					keyword.setOwner( keywordNewUser );
				}
			}
			
			if ( d instanceof XinixData )
			{
				UserImpl imageOldUser = (UserImpl)( (XinixData)d ).getXinixImage().getOwner();
				UserImpl imageNewUser = new UserImpl( null, null, null, imageOldUser.getFirstName(), imageOldUser.getLastName(), imageOldUser.getLoginName() );
				imageNewUser.setID( imageOldUser.getID() );
				imageNewUser.setClient( imageOldUser.getClient() );
				( (XinixData)d ).getXinixImage().setOwner( imageNewUser );
			}
			
			if ( d instanceof EvaluationExerciseData )
			{
				for ( Evaluation evaluation : ( (EvaluationExerciseData)d ).getEvaluations() )
				{
					UserImpl evalOldUser = (UserImpl)evaluation.getOwner();
					UserImpl evalNewUser = new UserImpl( null, null, null, evalOldUser.getFirstName(), evalOldUser.getLastName(), evalOldUser.getLoginName() );
					evalNewUser.setID( evalOldUser.getID() );
					evalNewUser.setClient( evalOldUser.getClient() );
					evaluation.setOwner( evalNewUser );
				}
			}
		}

		return cleansed;
	}
}
