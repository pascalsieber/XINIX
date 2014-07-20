package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ThreadLocalFilter;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.SessionServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;

@Path( SessionRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class SessionRestService extends IdentifiableObjectRestService
{

	public static final String BASE = "/workshopService/session";

	public final static String GET_CURRENT_EXERCISE = "/getCurrentExercise";
	public final static String GET_NEXT_EXERCISE = "/getNextExercise";
	public final static String GET_PREVIOUS_EXERCISE = "/getPreviousExercise";
	public final static String SET_NEXT_EXERCISE = "/setNextExercise";

	public final static String START = "/start";
	public final static String STOP = "/stop";

	public static final String JOIN = "/join";
	public static final String LEAVE = "/leave";

	public static final String ACCEPT_INVITATION = "/acceptInvitation";
	public static final String DECLINE_INVITATION = "/declineInvitation";
	public static final String INVITE = "/invite";
	public static final String OUTVITE = "/outvite";

	public static final String ADD_EXECUTER = "/addExecuter";
	public static final String REMOVE_EXECUTER = "/removeExecuter";

	private SessionService sessionService;
	private UserService userService;

	public SessionRestService()
	{
		sessionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
		userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
	}

//	@POST
//	@Path( PERSIST )
//	public int persist( SessionImpl obj )
//	{
//		return super.persist( obj );
//	}
//
//	@POST
//	@Path( FIND_BY_ID )
//	public SessionImpl findById( int id )
//	{
//		return super.findByID( id );
//	}
//
//	@POST
//	@Path( REMOVE )
//	public void remove( SessionImpl obj )
//	{
//		super.remove( obj );
//	}
//
//	@POST
//	@Path( FIND_ALL )
//	public List< SessionImpl > findAll()
//	{
//		return super.findAll( SessionImpl.class.getSimpleName() );
//	}

	@POST
	@Path( GET_CURRENT_EXERCISE )
	public ExerciseImpl getCurrentExercise( int sessionID )
	{
		return sessionService.getCurrentExercise( sessionID );
	}

	@POST
	@Path( GET_NEXT_EXERCISE )
	public ExerciseImpl getNextExercise( int sessionID )
	{
		return sessionService.getNextExercise( sessionID );
	}

	@POST
	@Path( GET_PREVIOUS_EXERCISE )
	public ExerciseImpl getPreviousExercise( int sessionID )
	{
		return sessionService.getPreviousExercise( sessionID );
	}

	@POST
	@Path( SET_NEXT_EXERCISE )
	public void setNextExercise( int sessionID )
	{
		sessionService.setNextExercise( sessionID );
	}

	@POST
	@Path( START )
	public void start( SessionImpl session )
	{
		sessionService.start( session.getID() );
	}

	@POST
	@Path( STOP )
	public void stop( SessionImpl session )
	{
		sessionService.stop( session.getID() );
	}

	@POST
	@Path( JOIN )
	public void join( int sessionID )
	{
		PrincipalImpl user = userService.findByLoginName( ThreadLocalFilter.getServletRequest().getUserPrincipal().getName() );
		sessionService.join( sessionID, user.getID() );
	}

	@POST
	@Path( LEAVE )
	public void leave( int sessionID )
	{
		PrincipalImpl user = userService.findByLoginName( ThreadLocalFilter.getServletRequest().getUserPrincipal().getName() );
		sessionService.leave( sessionID, user.getID() );
	}

	@POST
	@Path( ACCEPT_INVITATION )
	public void acceptInvitation( int invitationID )
	{
		sessionService.acceptInvitation( invitationID );
	}

	@POST
	@Path( DECLINE_INVITATION )
	public void declineInvitation( int invitationID )
	{
		sessionService.declineInvitation( invitationID );
	}

	@POST
	@Path( INVITE )
	public void invite( Invitation invitation )
	{
		sessionService.invite( invitation );
	}

	@POST
	@Path( OUTVITE )
	public void outvite( int invitationID )
	{
		sessionService.outvite( invitationID );
	}

	@POST
	@Path( ADD_EXECUTER )
	public void addExecuter( Invitation invitation )
	{
		sessionService.addExecuter( invitation );
	}

	@POST
	@Path( REMOVE_EXECUTER )
	public void removeExecuter( Invitation invitation )
	{
		sessionService.removeExecuter( invitation );
	}

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return sessionService;
	}

}