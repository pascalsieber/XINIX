package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.ParticipantDao;
import ch.zhaw.iwi.cis.pews.dao.SessionDao;
import ch.zhaw.iwi.cis.pews.dao.UserDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.ParticipantDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.SessionDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.UserDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Timer;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementStatusImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.wrappers.PollingWrapper;
import ch.zhaw.iwi.cis.pews.service.AuthenticationTokenService;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.util.comparator.ExerciseImplComparator;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class SessionServiceImpl extends WorkflowElementServiceImpl implements SessionService
{
	@ Inject private SessionDao sessionDao;
	@ Inject private UserDao userDao;
	@ Inject private ParticipantDao participantDao;
	@ Inject private ExerciseDao exerciseDao;
	@ Inject private AuthenticationTokenService authenticationTokenService;
	@ Inject private ExerciseService exerciseService;
	@ Inject private WorkshopDao workshopDao;

	@Override
	public String persistSession( SessionImpl session )
	{
		// if new session, use first exercise in workshop as currentExercise
		// if existing session, use currentExercise referenced in database if null
		SessionImpl probed = findByID( session.getID() );

		if ( probed == null && session.getCurrentExercise() == null )
		{
			WorkshopImpl workshop = workshopDao.findWorkshopByID( session.getWorkshop().getID() );
			session.setCurrentExercise( workshop.getExercises().get( 0 ) );
		}

		if ( probed != null && session.getCurrentExercise() == null )
		{
			session.setCurrentExercise( probed.getCurrentExercise() );
		}

		return super.persist( session );
	}

	@Override
	public void stop( String id )
	{
		super.stop( id );
		// remove authentication tokens
		authenticationTokenService.removeBySessionID( id );
	}

	@Override
	public void renew( String id )
	{
		super.renew( id );
		// remove authentication tokens
		authenticationTokenService.removeBySessionID( id );
	}

	@Override
	public SessionImpl findSessionByID( String id )
	{
		// simplify object for JSON mapper
		SessionImpl session = (SessionImpl)simplifyOwnerInObjectGraph( sessionDao.findSessionByID( id ) );
		session.getWorkshop().setExercises( new ArrayList< ExerciseImpl >() );

		for ( Participant part : session.getParticipants() )
		{
			part.getPrincipal().setCredential( null );
			part.getPrincipal().setParticipation( null );
			part.getPrincipal().setSessionAcceptances( null );
			part.getPrincipal().setSessionExecutions( null );
			part.getPrincipal().setSessionInvitations( null );
		}

		for ( PrincipalImpl principal : session.getAcceptees() )
		{
			principal.setCredential( null );
			principal.setParticipation( null );
			principal.setSessionAcceptances( null );
			principal.setSessionExecutions( null );
			principal.setSessionInvitations( null );
		}

		return session;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< SessionImpl > findAllSessions()
	{
		return (List< SessionImpl >)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override
	public void join( Invitation invitation )
	{
		if ( ( (PrincipalImpl)userDao.findById( invitation.getInvitee().getID() ) ).getParticipation() != null )
		{
			leave( invitation );
		}

		SessionImpl session = sessionDao.findById( invitation.getSession().getID() );
		PrincipalImpl principal = userDao.findById( invitation.getInvitee().getID() );

		String participantID = participantDao.persist( new Participant( principal, session, new Timer( null, 0, WorkflowElementStatusImpl.NEW ) ) );

		principal.setParticipation( (Participant)participantDao.findById( participantID ) );
		userDao.persist( principal );

		session.getParticipants().add( (Participant)participantDao.findById( participantID ) );
		sessionDao.persist( session );
	}

	@Override
	public void leave( Invitation invitation )
	{
		PrincipalImpl principal = userDao.findById( invitation.getInvitee().getID() );
		SessionImpl session = sessionDao.findById( principal.getParticipation().getSession().getID() );

		Participant removable = null;

		for ( Participant participant : session.getParticipants() )
		{
			if ( participant.getPrincipal().getID().equalsIgnoreCase( principal.getID() ) )
			{
				removable = participant;
				break;
			}
		}

		session.getParticipants().remove( participantDao.findById( removable.getID() ) );
		sessionDao.persist( session );

		// participantDao.remove( participantDao.findById( removable.getID() ) );
	}

	@Override
	public ExerciseImpl getCurrentExercise( String sessionID )
	{
		// simplify exercise object
		ExerciseImpl ex = (ExerciseImpl)simplifyOwnerInObjectGraph( ( (SessionImpl)findByID( sessionID ) ).getCurrentExercise() );
		ex.getWorkshop().setExercises( new ArrayList< ExerciseImpl >() );
		return ex;
	}

	@Override
	public void setCurrentExercise( SessionImpl request )
	{
		SessionImpl session = sessionDao.findById( request.getID() );
		ExerciseImpl exercise = exerciseDao.findById( request.getCurrentExercise().getID() );

		if ( session.getWorkshop().getID().equalsIgnoreCase( exercise.getWorkshop().getID() ) )
		{
			session.setCurrentExercise( exercise );
			sessionDao.persist( session );
		}
		else
		{
			throw new RuntimeException( "the requested session and exercise are not part of the same workshop!" );
		}
	}

	private List< ExerciseImpl > getExercisesOfSession( SessionImpl session )
	{
		Set< ExerciseImpl > exercisesRaw = new HashSet<>( session.getWorkshop().getExercises() );
		List< ExerciseImpl > orderedExercises = new ArrayList<>( exercisesRaw );
		Collections.sort( orderedExercises, new ExerciseImplComparator() );

		return orderedExercises;
	}

	@Override
	public ExerciseImpl getNextExercise( String sessionID )
	{
		SessionImpl session = sessionDao.findById( sessionID );
		List< ExerciseImpl > exercises = getExercisesOfSession( session );
		int current = exercises.indexOf( session.getCurrentExercise() );

		if ( current < exercises.size() )
		{
			ExerciseImpl ex = (ExerciseImpl)simplifyOwnerInObjectGraph( exercises.get( current + 1 ) );
			ex.getWorkshop().setExercises( new ArrayList< ExerciseImpl >() );
			return ex;
		}
		else
		{
			ExerciseImpl ex = (ExerciseImpl)simplifyOwnerInObjectGraph( exercises.get( current ) );
			ex.getWorkshop().setExercises( new ArrayList< ExerciseImpl >() );
			return ex;
		}
	}

	@Override
	public ExerciseImpl getPreviousExercise( String sessionID )
	{
		SessionImpl session = sessionDao.findById( sessionID );
		List< ExerciseImpl > exercises = getExercisesOfSession( session );
		int current = exercises.indexOf( session.getCurrentExercise() );

		if ( current > 0 )
		{
			ExerciseImpl ex = (ExerciseImpl)simplifyOwnerInObjectGraph( exercises.get( current - 1 ) );
			ex.getWorkshop().setExercises( new ArrayList< ExerciseImpl >() );
			return ex;
		}
		else
		{
			ExerciseImpl ex = (ExerciseImpl)simplifyOwnerInObjectGraph( exercises.get( current ) );
			ex.getWorkshop().setExercises( new ArrayList< ExerciseImpl >() );
			return ex;
		}
	}

	@Override
	public String setNextExercise( String sessionID )
	{
		SessionImpl session = sessionDao.findById( sessionID );
		List< ExerciseImpl > exercises = getExercisesOfSession( session );
		int current = exercises.indexOf( session.getCurrentExercise() );

		if ( current + 1 < exercises.size() )
		{
			session.setCurrentExercise( exercises.get( current + 1 ) );
			persist( session );
			return "CHANGED";
		}
		else
		{
			return "FINAL_EXERCISE";
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

	@Override
	public PollingWrapper getCurrentExericseIDWithOutput()
	{
		return new PollingWrapper( UserContext.getCurrentUser().getSession().getCurrentExercise().getID(), exerciseService.getOutput() );
	}
}
