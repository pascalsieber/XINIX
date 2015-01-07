package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.StartWorkshopInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.StartWorkshopDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = StartWorkshopDefinition.class )
public class StartWorkshopExerciseService extends ExerciseServiceImpl
{

	public StartWorkshopExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		StartWorkshopDefinition definition = (StartWorkshopDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new StartWorkshopInput( definition.getTitle(), definition.getDescription() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		StartWorkshopDefinition definition = (StartWorkshopDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new StartWorkshopInput( definition.getTitle(), definition.getDescription() );
	}

	@Override
	public void setOutput( String output )
	{
		throw new UnsupportedOperationException( "Start Workshop Exercises does not support the operation setOutput." );
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		throw new UnsupportedOperationException( "Start Workshop Exercises does not support the operation setOutputByExerciseID." );
	}

}
