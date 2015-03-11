package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.PosterInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.PosterDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = PosterDefinition.class )
public class PosterExerciseService extends ExerciseServiceImpl
{

	public PosterExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		PosterDefinition definition = (PosterDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new PosterInput( definition.getTitle(), definition.getDescription() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		PosterDefinition definition = (PosterDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new PosterInput( definition.getTitle(), definition.getDescription() );
	}

	@Override
	public void setOutput( String output )
	{
		throw new UnsupportedOperationException( "Poster Exercises does not support the operation setOutput." );
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		throw new UnsupportedOperationException( "Poster Exercises does not support the operation setOutputByExerciseID." );
	}
	
}
