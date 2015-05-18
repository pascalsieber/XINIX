package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.view.CompressionExerciseDataElementView;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class CompressionExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao specificDataDao;

	public CompressionExerciseDataService()
	{
		specificDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDao.class.getSimpleName() );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		List< ExerciseDataImpl > result = new ArrayList< ExerciseDataImpl >();

		for ( ExerciseDataImpl d : specificDataDao.findByExerciseID( exerciseID ) )
		{
			CompressionExerciseData mockParent = new CompressionExerciseData();
			mockParent.setID( d.getID() );

			for ( CompressionExerciseDataElement element : ( (CompressionExerciseData)d ).getSolutions() )
			{
				CompressionExerciseDataElementView view = new CompressionExerciseDataElementView( d.getOwner(), d.getWorkflowElement(), element.getSolution(), element.getDescription(), mockParent );
				view.setID( element.getID() );
				result.add( view );
			}
		}

		return result;
	}
}
