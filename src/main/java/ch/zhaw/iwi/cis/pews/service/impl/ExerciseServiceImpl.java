package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseServiceImpl extends WorkflowElementServiceImpl implements ExerciseService
{
	private ExerciseDao exerciseDao;

	public ExerciseServiceImpl()
	{
		exerciseDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDaoImpl.class.getSimpleName() );
	}

	@Override
	public void suspend( SuspensionRequest suspensionRequest )
	{
		ExerciseImpl exercise = findByID( suspensionRequest.getId() );
		exercise.setCurrentState( WorkflowElementStatusImpl.SUSPENDED );
		
		exercise.setEllapsedSeconds( suspensionRequest.getEllapsedSeconds() );
		persist( exercise );
	}

	@Override
	public double resume( int exerciseID )
	{
		ExerciseImpl exercise = findByID( exerciseID );
		exercise.setCurrentState( WorkflowElementStatusImpl.RUNNING );
		
		double ellapsedSeconds = exercise.getEllapsedSeconds();
		
		exercise.setEllapsedSeconds( 0 );
		persist( exercise );
		
		return ellapsedSeconds;
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return exerciseDao;
	}
}
