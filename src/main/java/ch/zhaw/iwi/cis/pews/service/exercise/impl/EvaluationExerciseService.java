package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.CompressionDataDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.CompressionDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInputElement;
import ch.zhaw.iwi.cis.pews.model.input.EvaluationInput;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutput;
import ch.zhaw.iwi.cis.pews.model.output.EvaluationOutputElement;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Score;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = EvaluationTemplate.class )
public class EvaluationExerciseService extends ExerciseServiceImpl
{
	private CompressionDataDao compressionDataDao;
	private ExerciseDataDao evaluationExerciseDataDao;

	public EvaluationExerciseService()
	{
		super();
		this.compressionDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDataDaoImpl.class.getSimpleName() );
		this.evaluationExerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		return this.getInputByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		List< CompressionInputElement > solutions = new ArrayList< CompressionInputElement >();
		List< ExerciseDataImpl > compressionData = compressionDataDao.findByWorkshopAndExerciseDataClass( UserContext.getCurrentUser().getSession().getWorkshop(), CompressionExerciseData.class );

		for ( ExerciseDataImpl data : compressionData )
		{
			for ( CompressionExerciseDataElement element : ( (CompressionExerciseData)data ).getSolutions() )
			{
				solutions.add( new CompressionInputElement( element.getID(), element.getSolution(), element.getDescription() ) );
			}
		}

		EvaluationExercise ex = findByID( exerciseID );
		return new EvaluationInput( ex, solutions, ex.getQuestion(), ex.getNumberOfVotes() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			EvaluationOutput finalOutput = getObjectMapper().readValue( output, EvaluationOutput.class );

			for ( EvaluationOutputElement evaluation : finalOutput.getEvaluations() )
			{
				CompressionExerciseDataElement solution = compressionDataDao.findBySolutionAndDescription( evaluation.getSolution(), evaluation.getDescription() );

				evaluationExerciseDataDao.persist( new EvaluationExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), new Evaluation(
					UserContext.getCurrentUser(),
					solution,
					new Score( UserContext.getCurrentUser(), evaluation.getScore() ) ) ) );

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

			for ( EvaluationOutputElement evaluation : finalOutput.getEvaluations() )
			{
				CompressionExerciseDataElement solution = compressionDataDao.findBySolutionAndDescription( evaluation.getSolution(), evaluation.getDescription() );

				evaluationExerciseDataDao.persist( new EvaluationExerciseData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( finalOutput.getExerciseID() ), new Evaluation( UserContext
					.getCurrentUser(), solution, new Score( UserContext.getCurrentUser(), evaluation.getScore() ) ) ) );

			}

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + EvaluationOutput.class.getSimpleName() );
		}
	}
}
