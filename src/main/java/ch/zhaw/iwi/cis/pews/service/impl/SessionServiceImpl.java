package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.SessionDao;
import ch.zhaw.iwi.cis.pews.dao.SessionDaoImpl;
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

	public SessionServiceImpl()
	{
		sessionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionDaoImpl.class.getSimpleName() );
	}

	@Override
	public void join( int userID, int sessionID )
	{
		SessionImpl session = findByID( sessionID );
		PrincipalImpl principal = findByID( userID );

		session.getParticipants().add( principal );
		sessionDao.persist( session );

		principal.setSession( (SessionImpl)findByID( sessionID ) );
		persist( principal );
	}

	@Override
	public void leave( int userID, int sessionID )
	{
		SessionImpl session = findByID( sessionID );
		PrincipalImpl principal = findByID( userID );

		session.getParticipants().remove( principal );
		persist( session );

		principal.setSession( null );
		persist( principal );
	}

	@Override
	public ExerciseImpl getCurrentExercise( int sessionID )
	{
		return ( (SessionImpl)findByID( sessionID ) ).getCurrentExercise();
	}

	@Override
	public ExerciseImpl getNextExercise( int sessionID )
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
	public ExerciseImpl getPreviousExercise( int sessionID )
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
	public void setNextExercise( int sessionID )
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
		SessionImpl session = findByID( invitation.getSession().getID() );
		session.getExecuters().add( (PrincipalImpl)findByID( invitation.getInvitee().getID() ) );
		persist( session );
	}

	@Override
	public void removeExecuter( Invitation invitation )
	{
		SessionImpl session = findByID( invitation.getSession().getID() );
		session.getExecuters().remove( (PrincipalImpl)findByID( invitation.getInvitee().getID() ) );
		persist( session );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return sessionDao;
	}

}
