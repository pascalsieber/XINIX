package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.P2POneInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.P2POneOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2POneDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = P2POneDefinition.class )
public class P2POneExerciseService extends ExerciseServiceImpl
{

	public P2POneExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		P2POneDefinition definition = (P2POneDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new P2POneInput( definition.getPicture(), definition.getQuestion() );
	}

	
	
	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		P2POneDefinition definition = (P2POneDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new P2POneInput( definition.getPicture(), definition.getQuestion() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			P2POneOutput finalOutput = getObjectMapper().readValue( output, P2POneOutput.class );
			getExerciseDataDao().persist( new P2POneData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getAnswers() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + P2POneOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			P2POneOutput finalOutput = getObjectMapper().readValue( outputRequestString, P2POneOutput.class );
			getExerciseDataDao().persist( new P2POneData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( finalOutput.getExerciseID() ), finalOutput.getAnswers() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + P2POneOutput.class.getSimpleName() );
		}
	}
	
}
