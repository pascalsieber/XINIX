package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationInput;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = EvaluationDefinition.class )
public class EvaluationExerciseService extends ExerciseServiceImpl
{

	public EvaluationExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{

		List< String > solutions = new ArrayList<>();
		List< ExerciseDataImpl > compressionData = getExerciseDataDao().findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );
		
		for ( ExerciseDataImpl data : compressionData )
		{
			solutions.addAll( ( (CompressionExerciseData)data ).getSolutions() );
		}

		EvaluationDefinition definition = (EvaluationDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new EvaluationInput( solutions, definition.getQuestion() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			EvaluationOutput finalOutput = getObjectMapper().readValue( output, EvaluationOutput.class );
			getExerciseDataDao().persist(
				new EvaluationExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getEvaluations() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + EvaluationOutput.class.getSimpleName() );
		}
		
		
		
	}

}
