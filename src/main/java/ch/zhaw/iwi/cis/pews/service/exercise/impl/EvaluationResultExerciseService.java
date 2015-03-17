package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultInput;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultObject;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationResultDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = EvaluationResultDefinition.class )
public class EvaluationResultExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao evaluationDataDao;

	public EvaluationResultExerciseService()
	{
		super();
		this.evaluationDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		Map< String, Integer > evaluationsMap = new HashMap< String, Integer >();
		List< ExerciseDataImpl > evaluations = evaluationDataDao.findByWorkshopAndExerciseDataClass( Evaluation.class );

		for ( ExerciseDataImpl evaluation : evaluations )
		{
			if ( evaluationsMap.containsKey( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution() ) )
			{
				evaluationsMap.put( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution(), ( (EvaluationExerciseData)evaluation ).getEvaluation().getScore().getScore() );
			}
			else
			{
				evaluationsMap.put( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution(), evaluationsMap.get( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution() )
						+ ( (EvaluationExerciseData)evaluation ).getEvaluation().getScore().getScore() );
			}
		}

		// TODO: finish!
		List< EvaluationResultObject > resultObjects = new ArrayList<>();
		for ( String solution : evaluationsMap.keySet() )
		{
			EvaluationResultObject resultObject = new EvaluationResultObject( solution, averageScore, numberOfVotes )
		}
		
		EvaluationResultInput input = new EvaluationResultInput();
		return input;
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		return getInput();
	}

	@Override
	public void setOutput( String output )
	{
		throw new UnsupportedOperationException( "Evaluation Result Exercises does not support the operation setOutput." );
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		throw new UnsupportedOperationException( "Evaluation Result Exercises does not support the operation setOutputByExerciseID." );
	}

}
