package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.P2PTwoInput;
import ch.zhaw.iwi.cis.pews.model.output.Output;
import ch.zhaw.iwi.cis.pews.model.output.P2PTwoOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = P2PTwoDefinition.class )
public class P2PTwoExerciseService extends ExerciseServiceImpl
{

	public P2PTwoExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		P2PTwoDefinition definition = (P2PTwoDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		List< String > keywords = new ArrayList<>();

		List< ExerciseDataImpl > cascadeOneData = getExerciseDataDao().findByWorkshopAndExerciseDataClass( P2POneData.class );

		for ( ExerciseDataImpl data : cascadeOneData )
		{
			for ( P2POneKeyword keywordObject : ( (P2POneData)data ).getKeywords() )
			{
				keywords.add( keywordObject.getKeyword() );
			}
		}

		return new P2PTwoInput( definition.getQuestion(), keywords );
	}

	@Override
	public void setOutput( Output output )
	{
		getExerciseDataDao().persist(
			new P2PTwoData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), ( (P2PTwoOutput)output ).getAnswers(), ( (P2PTwoOutput)output )
				.getChosenKeywords() ) );
	}

}
