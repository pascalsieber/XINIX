package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
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
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutputElement;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.EvaluationDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = EvaluationDefinition.class )
public class EvaluationExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao compressionDataDao;
	private ExerciseDataDao evaluationExerciseDataDao;

	public EvaluationExerciseService()
	{
		super();
		this.compressionDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDao.class.getSimpleName() );
		this.evaluationExerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{

		List< CompressionOutputElement > solutions = new ArrayList< CompressionOutputElement >();
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );

		for ( ExerciseDataImpl data : compressionData )
		{
			for ( CompressionExerciseDataElement element : ( (CompressionExerciseData)data ).getSolutions() )
			{
				solutions.add( new CompressionOutputElement( element.getSolution(), element.getDescription() ) );
			}
		}

		EvaluationDefinition definition = (EvaluationDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new EvaluationInput( solutions, definition.getQuestion(), definition.getNumberOfVotes() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		List< CompressionOutputElement > solutions = new ArrayList<>();
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( CompressionExerciseData.class );

		for ( ExerciseDataImpl data : compressionData )
		{
			for ( CompressionExerciseDataElement element : ( (CompressionExerciseData)data ).getSolutions() )
			{
				solutions.add( new CompressionOutputElement( element.getSolution(), element.getDescription() ) );
			}
		}

		EvaluationDefinition definition = (EvaluationDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new EvaluationInput( solutions, definition.getQuestion(), definition.getNumberOfVotes() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			EvaluationOutput finalOutput = getObjectMapper().readValue( output, EvaluationOutput.class );

			for ( Evaluation evaluation : finalOutput.getEvaluations() )
			{
				evaluationExerciseDataDao.persist( new EvaluationExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), evaluation ) );
			}

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
			EvaluationOutput finalOutput = getObjectMapper().readValue( outputRequestString, EvaluationOutput.class );

			WorkflowElementImpl exercise = (WorkflowElementImpl)findByID( finalOutput.getExerciseID() );

			for ( Evaluation evaluation : finalOutput.getEvaluations() )
			{
				evaluationExerciseDataDao.persist( new EvaluationExerciseData( UserContext.getCurrentUser(), exercise, evaluation ) );
			}

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + EvaluationOutput.class.getSimpleName() );
		}
	}
}
