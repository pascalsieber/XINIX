package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.XinixInput;
import ch.zhaw.iwi.cis.pews.model.output.Output;
import ch.zhaw.iwi.cis.pews.model.output.XinixOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = XinixDefinition.class )
public class XinixExerciseService extends ExerciseServiceImpl
{

	public XinixExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		XinixDefinition definition = (XinixDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new XinixInput( definition.getQuestion(), definition.getImages() );
	}

	@Override
	public void setOutput( Output output )
	{
		getExerciseDataDao()
			.persist(
				new XinixData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), ( (XinixOutput)output ).getAnswers(), ( (XinixOutput)output )
					.getChosenImage() ) );
	}

}
