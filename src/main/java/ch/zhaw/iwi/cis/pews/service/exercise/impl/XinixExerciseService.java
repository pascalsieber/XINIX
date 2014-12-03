package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.XinixImageMatrixDao;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.XinixInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.XinixOutput;
import ch.zhaw.iwi.cis.pews.model.wrappers.OutputRequest;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixDefinition;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.XinixImageMatrix;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = XinixDefinition.class )
public class XinixExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao xinixImageMatrixDao;

	public XinixExerciseService()
	{
		super();
		this.xinixImageMatrixDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageMatrixDao.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		XinixDefinition definition = (XinixDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		return new XinixInput( definition.getQuestion(), (XinixImageMatrix)xinixImageMatrixDao.findById( definition.getImages().getID() ) );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		XinixDefinition definition = (XinixDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		return new XinixInput( definition.getQuestion(), (XinixImageMatrix)xinixImageMatrixDao.findById( definition.getImages().getID() ) );
	}

	@Override
	public void setOutput( String output )
	{
		// TODO chosen xinix image should probably be in form of ID, not whole object
		try
		{
			XinixOutput finalOutput = getObjectMapper().readValue( output, XinixOutput.class );
			getExerciseDataDao().persist(
				new XinixData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getAnswers(), finalOutput.getChosenImage() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + XinixOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		// TODO chosen xinix image should probably be in form of ID, not whole object
		try
		{
			OutputRequest request = getObjectMapper().readValue( outputRequestString, OutputRequest.class );
			XinixOutput finalOutput = getObjectMapper().readValue( request.getOutput(), XinixOutput.class );
			getExerciseDataDao().persist(
				new XinixData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( request.getExerciseID() ), finalOutput.getAnswers(), finalOutput.getChosenImage() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + XinixOutput.class.getSimpleName() );
		}
	}

}
