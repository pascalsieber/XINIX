package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.SessionDao;
import ch.zhaw.iwi.cis.pews.dao.UserDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.SessionDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.UserDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.SessionService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class SessionServiceImpl extends WorkflowElementServiceImpl implements SessionService
{
	private SessionDao sessionDao;
	private UserDao userDao;

	public SessionServiceImpl()
	{
		sessionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionDaoImpl.class.getSimpleName() );
		userDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserDaoImpl.class.getSimpleName() );
	}

	@Override
	public void join( Invitation invitation )
	{
		PrincipalImpl principal = userDao.findById( invitation.getInvitee().getID() );
		principal.setSession( (SessionImpl)findByID( invitation.getSession().getID() ) );
		userDao.persist( principal );
	}

	@Override
	public void leave( Invitation invitation )
	{
		PrincipalImpl principal = userDao.findById( invitation.getInvitee().getID() );
		principal.setSession( null );
		userDao.persist( principal );
	}

	@Override
	public ExerciseImpl getCurrentExercise( String sessionID )
	{
		return ( (SessionImpl)findByID( sessionID ) ).getCurrentExercise();
	}

	@Override
	public ExerciseImpl getNextExercise( String sessionID )
	{
		SessionImpl session = findByID( sessionID );
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
	public ExerciseImpl getPreviousExercise( String sessionID )
	{
		SessionImpl session = findByID( sessionID );
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
	public void setNextExercise( String sessionID )
	{
		SessionImpl session = findByID( sessionID );
		List< ExerciseImpl > exercises = session.getWorkshop().getExercises();
		int current = exercises.indexOf( session.getCurrentExercise() );

		if ( current < exercises.size() )
		{
			session.setCurrentExercise( exercises.get( current + 1 ) );
			persist( session );
		}
	}

	@Override
	public void addExecuter( Invitation invitation )
	{
		PrincipalImpl principal = userDao.findById( invitation.getInvitee().getID() );
		principal.getSessionExecutions().add( (SessionImpl)findByID( invitation.getSession().getID() ) );
		userDao.persist( principal );
	}

	@Override
	public void removeExecuter( Invitation invitation )
	{
		PrincipalImpl principal = userDao.findById( invitation.getInvitee().getID() );
		principal.getSessionExecutions().remove( (SessionImpl)findByID( invitation.getSession().getID() ) );
		userDao.persist( principal );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return sessionDao;
	}

}
