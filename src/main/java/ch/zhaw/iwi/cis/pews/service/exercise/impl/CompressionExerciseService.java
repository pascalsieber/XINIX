package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDefinitionDao;
import ch.zhaw.iwi.cis.pews.dao.definition.impl.CompressionDefinitionDao;
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
import ch.zhaw.iwi.cis.pews.model.wrappers.OutputRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressableExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.CompressionDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = CompressionDefinition.class )
public class CompressionExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataService exerciseDataService;
	private ExerciseDefinitionDao compressionDefinitionDao;

	public CompressionExerciseService()
	{
		super();
		this.exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		this.compressionDefinitionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( CompressionDefinitionDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		CompressionDefinition definition = compressionDefinitionDao.findById( UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition().getID() );
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

		CompressionInput input = new CompressionInput( definition.getQuestion(), new ArrayList<>( definition.getSolutionCriteria() ), compressableData );
		return input;
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		CompressionDefinition definition = compressionDefinitionDao.findById( ( (WorkflowElementImpl)super.findByID( exerciseID ) ).getDefinition().getID() );
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

		CompressionInput input = new CompressionInput( definition.getQuestion(), new ArrayList<>( definition.getSolutionCriteria() ), compressableData );
		return input;
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			CompressionOutput finalOutput = getObjectMapper().readValue( output, CompressionOutput.class );
			getExerciseDataDao().persist(
				new CompressionExerciseData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), (List< String >)finalOutput.getSolutions() ) );
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
			OutputRequest request = getObjectMapper().readValue( outputRequestString, OutputRequest.class );
			CompressionOutput finalOutput = getObjectMapper().readValue( request.getOutput(), CompressionOutput.class );
			getExerciseDataDao().persist(
				new CompressionExerciseData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( request.getExerciseID() ), (List< String >)finalOutput.getSolutions() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + CompressionOutput.class.getSimpleName() );
		}
	}
}
