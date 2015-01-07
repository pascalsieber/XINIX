package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.EndWorkshopInput;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EndWorkshopDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = EndWorkshopDefinition.class )
public class EndWorkshopExerciseService extends ExerciseServiceImpl
{

	public EndWorkshopExerciseService()
	{
		super();
	}
	
	@Override
	public Input getInput()
	{
		EndWorkshopDefinition definition = (EndWorkshopDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new EndWorkshopInput( definition.getTitle(), definition.getDescription() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		EndWorkshopDefinition definition = (EndWorkshopDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new EndWorkshopInput( definition.getTitle(), definition.getDescription() );
	}

	@Override
	public void setOutput( String output )
	{
		throw new UnsupportedOperationException( "End Workshop Exercises does not support the operation setOutput." );
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		throw new UnsupportedOperationException( "End Workshop Exercises does not support the operation setOutputByExerciseID." );
	}
}
