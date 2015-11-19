package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.CompressionInput;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutput;
import ch.zhaw.iwi.cis.pews.model.output.CompressionOutputElement;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressableExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = CompressionTemplate.class )
public class CompressionExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataService exerciseDataService;

	public CompressionExerciseService()
	{
		super();
		this.exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
	}

	@Override
	public ExerciseImpl findExerciseByID( String id )
	{
		return super.findByID( id );
	}

	@Override
	public Input getInput()
	{
		return this.getInputByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		CompressionExercise exercise = findByID( exerciseID );
		List< CompressableExerciseData > compressableData = new ArrayList<>();
		List< ExerciseDataImpl > dataOfAllExercises = new ArrayList<>();

		for ( ExerciseImpl ex : UserContext.getCurrentUser().getSession().getWorkshop().getExercises() )
		{
			dataOfAllExercises.addAll( exerciseDataService.findByExerciseID( ex.getID() ) );
		}

		for ( ExerciseDataImpl data : dataOfAllExercises )
		{
			if ( CompressableExerciseData.class.isAssignableFrom( data.getClass() ) )
			{
				compressableData.add( (CompressableExerciseData)data );
			}
		}

		return new CompressionInput( exercise, exercise.getQuestion(), exercise.getSolutionCriteria(), compressableData );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			CompressionOutput finalOutput = getObjectMapper().readValue( output, CompressionOutput.class );

			List< CompressionExerciseDataElement > elements = new ArrayList< CompressionExerciseDataElement >();
			for ( CompressionOutputElement sol : finalOutput.getSolutions() )
			{
				elements.add( new CompressionExerciseDataElement( sol.getSolution(), sol.getDescription() ) );
			}

			getExerciseDataDao().persist( new CompressionExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), elements ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + CompressionOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			CompressionOutput finalOutput = getObjectMapper().readValue( outputRequestString, CompressionOutput.class );

			List< CompressionExerciseDataElement > elements = new ArrayList< CompressionExerciseDataElement >();
			for ( CompressionOutputElement sol : finalOutput.getSolutions() )
			{
				elements.add( new CompressionExerciseDataElement( sol.getSolution(), sol.getDescription() ) );
			}

			getExerciseDataDao().persist( new CompressionExerciseData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( finalOutput.getExerciseID() ), elements ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + CompressionOutput.class.getSimpleName() );
		}
	}
}
