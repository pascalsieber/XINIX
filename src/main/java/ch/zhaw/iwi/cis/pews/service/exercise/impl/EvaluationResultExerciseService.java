package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultInput;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultObject;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pews.util.comparator.EvaluationResultComparator;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationResultDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = EvaluationResultDefinition.class )
public class EvaluationResultExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao evaluationDataDao;
	private ExerciseDataDao compressionDataDao;

	public EvaluationResultExerciseService()
	{
		super();
		this.evaluationDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
		this.compressionDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		Map< String, List< Integer > > evaluationsMap = new HashMap< String, List< Integer > >();
		List< ExerciseDataImpl > evaluations = evaluationDataDao.findByWorkshopAndExerciseDataClass( Evaluation.class );

		for ( ExerciseDataImpl evaluation : evaluations )
		{
			if ( !evaluationsMap.containsKey( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution() ) )
			{
				evaluationsMap
					.put( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution(),  new ArrayList< Integer >());
			}
			
			evaluationsMap.get( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution() ).add( ( (EvaluationExerciseData)evaluation ).getEvaluation().getScore().getScore() );	
		}

		EvaluationResultInput input = new EvaluationResultInput();

		for ( Entry< String, List< Integer >> entry : evaluationsMap.entrySet() )
		{
			int sumOfScores = 0;
			for ( Integer score : entry.getValue() )
			{
				sumOfScores += score;
			}

			input.getResults().add( new EvaluationResultObject( entry.getKey(), sumOfScores / entry.getValue().size(), entry.getValue().size() ) );
		}

		
		// add solutions which have not been evaluated
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );
		for ( ExerciseDataImpl compressionDataPoint : compressionData )
		{
			for ( CompressionExerciseDataElement solution : ( (CompressionExerciseData)compressionDataPoint ).getSolutions() )
			{
				if ( !evaluationsMap.containsKey( solution ) )
				{
					input.getNotEvaluated().add( solution );
				}
			}
		}

		Collections.sort( input.getResults(), new EvaluationResultComparator() );
		
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
