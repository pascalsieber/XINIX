package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.P2POneInput;
import ch.zhaw.iwi.cis.pews.model.output.Output;
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
	public void setOutput( Output output )
	{
		getExerciseDataDao().persist( new P2POneData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), ( (P2POneOutput)output ).getAnswers() ) );
	}

}
