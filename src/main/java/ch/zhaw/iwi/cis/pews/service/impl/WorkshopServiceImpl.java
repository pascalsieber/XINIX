package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopServiceImpl extends WorkflowElementServiceImpl implements WorkshopService
{
	private WorkshopDao workshopDao;

	public WorkshopServiceImpl()
	{
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
	}

	@Override
	public void joinSession( int userID, int sessionID )
	{
		SessionImpl session = workshopDao.findById( sessionID );
		PrincipalImpl principal = workshopDao.findById( userID );

		session.getParticipants().add( principal );
		workshopDao.persist( session );

		principal.setSession( (SessionImpl)workshopDao.findById( sessionID ) );
		workshopDao.persist( principal );
	}

	@Override
	public void leaveSession( int userID, int sessionID )
	{
		SessionImpl session = workshopDao.findById( sessionID );
		PrincipalImpl principal = workshopDao.findById( userID );

		session.getParticipants().remove( principal );
		workshopDao.persist( session );

		principal.setSession( null );
		workshopDao.persist( principal );
	}

	@Override
	public ExerciseImpl getCurrentExercise( int sessionID )
	{
		SessionImpl session = workshopDao.findById( sessionID );
		return session.getCurrentExercise();
	}

	@Override
	public ExerciseImpl getNextExercise( int sessionID )
	{
		SessionImpl session = workshopDao.findById( sessionID );
		List< ExerciseImpl > exercises = session.getWorkshop().getExercises();
		int current = exercises.indexOf( session.getCurrentExercise() );
		
		if ( current < exercises.size() )
		{
			return exercises.get( current + 1 );
		}
		else
		{
			return exercises.get( current );
		}
	}

	@Override
	public ExerciseImpl getPreviousExercise( int sessionID )
	{
		SessionImpl session = workshopDao.findById( sessionID );
		List< ExerciseImpl > exercises = session.getWorkshop().getExercises();
		int current = exercises.indexOf( session.getCurrentExercise() );
		
		if ( current > 0 )
		{
			return exercises.get( current - 1 );
		}
		else
		{
			return exercises.get( current );
		}
	}

	@Override
	public void setNextExercise( int sessionID )
	{
		SessionImpl session = workshopDao.findById( sessionID );
		List< ExerciseImpl > exercises = session.getWorkshop().getExercises();
		int current = exercises.indexOf( session.getCurrentExercise() );
		
		if ( current < exercises.size() )
		{
			session.setCurrentExercise( exercises.get( current + 1 ) );
			workshopDao.persist( session );
		}
		
	}

}
