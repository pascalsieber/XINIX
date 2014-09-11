package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.PinkLabsInput;
import ch.zhaw.iwi.cis.pews.model.output.Output;
import ch.zhaw.iwi.cis.pews.model.output.PinkLabsOutput;
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
	public void setOutput( Output output )
	{
		getExerciseDataDao()
			.persist( new PinkLabsExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), ( (PinkLabsOutput)output ).getAnswers() ) );
	}

}
