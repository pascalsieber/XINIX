package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.You2MeInput;
import ch.zhaw.iwi.cis.pews.model.output.You2MeOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.You2MeDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = You2MeDefinition.class )
public class You2MeExerciseService extends ExerciseServiceImpl
{

	public You2MeExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		You2MeDefinition definition = (You2MeDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new You2MeInput( definition.getQuestions() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			You2MeOutput finalOutput = getObjectMapper().readValue( output, You2MeOutput.class );
			getExerciseDataDao().persist( new You2MeExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getDialog() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + You2MeOutput.class.getSimpleName() );
		}
	}

}
