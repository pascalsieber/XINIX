package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.EvaluationExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class EvaluationExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao specificDataDao;
	private ExerciseDataDao compressionDao;

	public EvaluationExerciseDataService()
	{
		specificDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
		compressionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDaoImpl.class.getSimpleName() );
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return specificDataDao.persistExerciseData( obj );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return specificDataDao.findByExerciseID( exerciseID );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return specificDataDao.findDataByID( id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< EvaluationExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		// apply same logic for generating input for EvluationResultExercise

		List< EvaluationExerciseDataViewObject > export = new ArrayList< EvaluationExerciseDataViewObject >();
		// map for organizing evaluations, with ID of evaluated solution as key and score as value
		Map< CompressionExerciseDataElement, List< Integer > > evaluationsMap = new HashMap< CompressionExerciseDataElement, List< Integer > >();

		// get evaluations produced by exercise and organized in evaluationsMap
		for ( ExerciseDataImpl obj : findByExerciseID( exercise.getID() ) )
		{
			if ( !evaluationsMap.containsKey( ( (EvaluationExerciseData)obj ).getEvaluation().getSolution() ) )
			{
				evaluationsMap.put( ( (EvaluationExerciseData)obj ).getEvaluation().getSolution(), new ArrayList< Integer >() );
			}

			evaluationsMap.get( ( (EvaluationExerciseData)obj ).getEvaluation().getSolution() ).add( ( (EvaluationExerciseData)obj ).getEvaluation().getScore().getScore() );
		}

		// for storing of IDs of evaluated solutions
		List< String > idsOfEvaluatedCompressionElements = new ArrayList< String >();

		// compute score for each solution and add to export
		for ( Entry< CompressionExerciseDataElement, List< Integer >> entry : evaluationsMap.entrySet() )
		{
			int sumOfScores = 0;
			for ( Integer score : entry.getValue() )
			{
				sumOfScores += score;
			}
			idsOfEvaluatedCompressionElements.add( entry.getKey().getID() );

			double avgScore = (double)sumOfScores / (double)entry.getValue().size();
			// same as sumOfScores
			double weightedScore = avgScore * entry.getValue().size();

			export.add( new EvaluationExerciseDataViewObject(
				"",
				exercise.getWorkshop().getID(),
				exercise.getWorkshop().getName(),
				exercise.getID(),
				exercise.getName(),
				exercise.getQuestion(),
				"",
				"",
				true,
				entry.getKey().getSolution(),
				entry.getKey().getDescription(),
				entry.getValue().size(),
				avgScore,
				weightedScore ) );
		}

		// add solutions with no evaluations
		List< ExerciseDataImpl > compressionData = compressionDao.findByWorkshopAndExerciseDataClass( exercise.getWorkshop(), CompressionExerciseData.class );
		for ( ExerciseDataImpl compressionDataPoint : compressionData )
		{
			for ( CompressionExerciseDataElement solution : ( (CompressionExerciseData)compressionDataPoint ).getSolutions() )
			{
				if ( !idsOfEvaluatedCompressionElements.contains( solution.getID() ) )
				{
					export.add( new EvaluationExerciseDataViewObject( "", exercise.getWorkshop().getID(), exercise.getWorkshop().getName(), exercise.getID(), exercise.getName(), exercise
						.getQuestion(), "", "", false, solution.getSolution(), solution.getDescription(), 0, 0, 0 ) );
				}
			}
		}

		return export;
	}
}
