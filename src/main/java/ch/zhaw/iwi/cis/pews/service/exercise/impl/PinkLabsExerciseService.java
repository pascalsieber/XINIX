package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.PinkLabsInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.PinkLabsOutput;
import ch.zhaw.iwi.cis.pews.model.wrappers.OutputRequest;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PinkLabsDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = PinkLabsDefinition.class )
public class PinkLabsExerciseService extends ExerciseServiceImpl
{

	public PinkLabsExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		PinkLabsDefinition definition = (PinkLabsDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new PinkLabsInput( definition.getQuestion() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		PinkLabsDefinition definition = (PinkLabsDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new PinkLabsInput( definition.getQuestion() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			PinkLabsOutput finalOutput = getObjectMapper().readValue( output, PinkLabsOutput.class );
			getExerciseDataDao().persist( new PinkLabsExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getAnswers() ) );

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + PinkLabsOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			OutputRequest request = getObjectMapper().readValue( outputRequestString, OutputRequest.class );
			PinkLabsOutput finalOutput = getObjectMapper().readValue( request.getOutput(), PinkLabsOutput.class );
			getExerciseDataDao().persist( new PinkLabsExerciseData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( request.getExerciseID() ), finalOutput.getAnswers() ) );

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + PinkLabsOutput.class.getSimpleName() );
		}
	}
}
