package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationInput;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
import ch.zhaw.iwi.cis.pews.model.wrappers.OutputRequest;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = EvaluationDefinition.class )
public class EvaluationExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao compressionDataDao;

	public EvaluationExerciseService()
	{
		super();
		this.compressionDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{

		List< String > solutions = new ArrayList<>();
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );

		for ( ExerciseDataImpl data : compressionData )
		{
			solutions.addAll( ( (CompressionExerciseData)data ).getSolutions() );
		}

		EvaluationDefinition definition = (EvaluationDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new EvaluationInput( solutions, definition.getQuestion() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		List< String > solutions = new ArrayList<>();
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );

		for ( ExerciseDataImpl data : compressionData )
		{
			solutions.addAll( ( (CompressionExerciseData)data ).getSolutions() );
		}

		EvaluationDefinition definition = (EvaluationDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new EvaluationInput( solutions, definition.getQuestion() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			EvaluationOutput finalOutput = getObjectMapper().readValue( output, EvaluationOutput.class );
			getExerciseDataDao().persist( new EvaluationExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getEvaluations() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + EvaluationOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			OutputRequest request = getObjectMapper().readValue( outputRequestString, OutputRequest.class );
			EvaluationOutput finalOutput = getObjectMapper().readValue( request.getOutput(), EvaluationOutput.class );
			getExerciseDataDao().persist( new EvaluationExerciseData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( request.getExerciseID() ), finalOutput.getEvaluations() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + EvaluationOutput.class.getSimpleName() );
		}
	}

}
