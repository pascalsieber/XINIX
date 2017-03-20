package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.P2POneInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.P2POneOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2POneExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = P2POneTemplate.class )
public class P2POneExerciseService extends ExerciseServiceImpl
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
		P2POneExercise ex = findByID( exerciseID );
		return new P2POneInput( ex, ex.getPicture(), ex.getQuestion() );
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
