package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.impl.CompressableExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInput;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutput;
import ch.zhaw.iwi.cis.pews.service.CompressableExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.impl.CompressableExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressableExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = CompressionDefinition.class )
public class CompressionExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao compressableExerciseDataDao;

	public CompressionExerciseService()
	{
		super();
		this.compressableExerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressableExerciseDataDaoImpl.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		CompressionDefinition definition = (CompressionDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		List< CompressableExerciseData > compressableData = new ArrayList<>();
		
		// TODO finish this!!!
		
		return new CompressionInput( definition.getQuestion(), definition.getSolutionCriteria(), compressableData );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			CompressionOutput finalOutput = getObjectMapper().readValue( output, CompressionOutput.class );
			getExerciseDataDao().persist(
				new CompressionExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), (List< String >)finalOutput.getSolutions() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + CompressionOutput.class.getSimpleName() );
		}
	}

}
