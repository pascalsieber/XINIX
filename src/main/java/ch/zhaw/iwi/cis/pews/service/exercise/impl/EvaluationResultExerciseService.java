package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInputElement;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultInput;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultObject;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pews.util.comparator.EvaluationResultComparator;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationResultExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = EvaluationResultTemplate.class )
public class EvaluationResultExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao evaluationDataDao;
	private ExerciseDataDao compressionDataDao;

	public EvaluationResultExerciseService()
	{
		super();
		this.evaluationDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
		this.compressionDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDaoImpl.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		return this.getInputByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		EvaluationResultExercise ex = findByID( exerciseID );
		Map< CompressionExerciseDataElement, List< Integer > > evaluationsMap = new HashMap< CompressionExerciseDataElement, List< Integer > >();
		List< ExerciseDataImpl > evaluations = evaluationDataDao.findByWorkshopAndExerciseDataClass( Evaluation.class );

		for ( ExerciseDataImpl evaluation : evaluations )
		{
			if ( !evaluationsMap.containsKey( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution() ) )
			{
				evaluationsMap.put( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution(), new ArrayList< Integer >() );
			}

			evaluationsMap.get( ( (EvaluationExerciseData)evaluation ).getEvaluation().getSolution() ).add( ( (EvaluationExerciseData)evaluation ).getEvaluation().getScore().getScore() );
		}

		EvaluationResultInput input = new EvaluationResultInput( ex, new ArrayList< EvaluationResultObject >(), new ArrayList< CompressionInputElement >() );
		List< String > idsOfEvaluatedCompressionElements = new ArrayList< String >();

		for ( Entry< CompressionExerciseDataElement, List< Integer >> entry : evaluationsMap.entrySet() )
		{
			int sumOfScores = 0;
			for ( Integer score : entry.getValue() )
			{
				sumOfScores += score;
			}
			idsOfEvaluatedCompressionElements.add( entry.getKey().getID() );

			double avgScore = (double)sumOfScores / (double)entry.getValue().size();

			input.getResults().add(
				new EvaluationResultObject( new CompressionInputElement( entry.getKey().getID(), entry.getKey().getSolution(), entry.getKey().getDescription() ), avgScore, entry.getValue().size() ) );
		}

		// add solutions which have not been evaluated
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );
		for ( ExerciseDataImpl compressionDataPoint : compressionData )
		{
			for ( CompressionExerciseDataElement solution : ( (CompressionExerciseData)compressionDataPoint ).getSolutions() )
			{
				if ( !idsOfEvaluatedCompressionElements.contains( solution.getID() ) )
				{
					input.getNotEvaluated().add( new CompressionInputElement( solution.getID(), solution.getSolution(), solution.getDescription() ) );
				}
			}
		}

		Collections.sort( input.getResults(), new EvaluationResultComparator() );

		return input;
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
