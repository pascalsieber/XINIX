package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedExecutionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedSetCurrentExerciseRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.PollingWrapper;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.rest.SessionRestService;

public class SessionServiceProxy extends WorkshopObjectServiceProxy implements SessionService
{

	protected SessionServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, SessionRestService.BASE );
	}

	@Override
	public void start( String id )
	{
		getServiceTarget().path( SessionRestService.START ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void stop( String id )
	{
		getServiceTarget().path( SessionRestService.STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void renew( String id )
	{
		getServiceTarget().path( SessionRestService.RENEW ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public ExerciseImpl getPreviousExercise( String sessionID )
	{
		return getServiceTarget().path( SessionRestService.GET_PREVIOUS_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public String setNextExercise( String sessionID )
	{
		return getServiceTarget().path( SessionRestService.SET_NEXT_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( String.class );
	}

	@Override
	public void join( Invitation invitation )
	{
		getServiceTarget().path( SessionRestService.JOIN ).request( MediaType.APPLICATION_JSON ).post( Entity.json( invitation ) );
	}

	@Override
	public void leave( Invitation invitation )
	{
		getServiceTarget().path( SessionRestService.LEAVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( invitation ) );
	}

	@Override
	public void setCurrentExercise( SessionImpl request )
	{
		getServiceTarget().path( SessionRestService.SET_CURRENT_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( request ) );
	}

	@Override
	public SessionImpl findSessionByID( String id )
	{
		return getServiceTarget().path( SessionRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( SessionImpl.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< SessionImpl > findAllSessions()
	{
		return getServiceTarget().path( SessionRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@Override
	public PollingWrapper getCurrentExericseIDWithOutput()
	{
		return getServiceTarget().path( SessionRestService.GET_CURRENT_EXERCISE_ID_WITH_OUTPUT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( PollingWrapper.class );
	}

	@Override
	public String persistSession( SessionImpl obj )
	{
		return getServiceTarget().path( SessionRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) ).readEntity( String.class );
	}

}
