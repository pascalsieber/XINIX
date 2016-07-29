package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.XinixImageMatrixDao;
import ch.zhaw.iwi.cis.pews.dao.exercise.impl.XinixExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.XinixInput;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.XinixOutput;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = XinixTemplate.class )
public class XinixExerciseService extends ExerciseServiceImpl
{
	private ExerciseDataDao xinixImageMatrixDao;
	private ExerciseDao specificDao;

	public XinixExerciseService()
	{
		super();
		this.xinixImageMatrixDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageMatrixDao.class.getSimpleName() );
		this.specificDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixExerciseDaoImpl.class.getSimpleName() );
	}

	@Override
	public ExerciseImpl findExerciseByID( String id )
	{
		return specificDao.findExerciseByID( id );
	}

	@Override
	public Input getInput()
	{
		return this.getInputByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		XinixExercise ex = findByID( exerciseID );
		return new XinixInput( ex, ex.getQuestion(), (XinixImageMatrix)xinixImageMatrixDao.findById( ex.getImages().getID() ) );
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
		try
		{
			XinixOutput finalOutput = getObjectMapper().readValue( outputRequestString, XinixOutput.class );
			getExerciseDataDao().persist(
				new XinixData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( finalOutput.getExerciseID() ), finalOutput.getAnswers(), finalOutput.getChosenImage() ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + XinixOutput.class.getSimpleName() );
		}
	}

}
