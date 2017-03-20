package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.PinkLabsInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.PinkLabsOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = PinkLabsTemplate.class )
public class PinkLabsExerciseService extends ExerciseServiceImpl
{

	@Override
	public ExerciseImpl findExerciseByID( String id )
	{
		return super.findByID( id );
	}

	@Override
	public Input getInput()
	{
		return this.getInputByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		PinkLabsExercise ex = findByID( exerciseID );
		return new PinkLabsInput( ex, ex.getQuestion() );
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
			PinkLabsOutput finalOutput = getObjectMapper().readValue( outputRequestString, PinkLabsOutput.class );
			getExerciseDataDao().persist( new PinkLabsExerciseData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( finalOutput.getExerciseID() ), finalOutput.getAnswers() ) );

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + PinkLabsOutput.class.getSimpleName() );
		}
	}
}
