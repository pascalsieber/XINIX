package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.SimplePrototypingInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.SimplePrototypingOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = SimplyPrototypingTemplate.class )
public class SimplePrototypingExerciseService extends ExerciseServiceImpl
{

	public SimplePrototypingExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		SimplyPrototypingTemplate definition = (SimplyPrototypingTemplate)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new SimplePrototypingInput( definition.getQuestion(), definition.getMimeType() );
	}
	
	

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		SimplyPrototypingTemplate definition = (SimplyPrototypingTemplate)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
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

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			SimplePrototypingOutput finalOutput = getObjectMapper().readValue( outputRequestString, SimplePrototypingOutput.class );
			getExerciseDataDao().persist( new SimplePrototypingData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( finalOutput.getExerciseID() ), finalOutput.getBlob() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + SimplePrototypingOutput.class.getSimpleName() );
		}
	}
	
}
