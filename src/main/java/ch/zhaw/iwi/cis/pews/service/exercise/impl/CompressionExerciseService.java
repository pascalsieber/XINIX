package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.List;

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

	private CompressableExerciseDataService compressableExerciseDataService;

	public CompressionExerciseService()
	{
		super();
		this.compressableExerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressableExerciseDataServiceImpl.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		CompressionDefinition definition = (CompressionDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		List< CompressableExerciseData > compressableData = compressableExerciseDataService.findAll();
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
