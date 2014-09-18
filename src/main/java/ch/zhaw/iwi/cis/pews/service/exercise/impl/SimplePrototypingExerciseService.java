package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.SimplePrototypingInput;
import ch.zhaw.iwi.cis.pews.model.output.SimplePrototypingOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.SimplePrototypingDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = SimplePrototypingDefinition.class )
public class SimplePrototypingExerciseService extends ExerciseServiceImpl
{

	public SimplePrototypingExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		SimplePrototypingDefinition definition = (SimplePrototypingDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new SimplePrototypingInput( definition.getQuestion(), definition.getMimeType() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			SimplePrototypingOutput finalOutput = getObjectMapper().readValue( output, SimplePrototypingOutput.class );
			getExerciseDataDao().persist( new SimplePrototypingData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getBlob() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + SimplePrototypingOutput.class.getSimpleName() );
		}
	}

}
