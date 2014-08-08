package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
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
	public ExerciseImpl getCurrentExercise( String sessionID )
	{
		return getServiceTarget().path( SessionRestService.GET_CURRENT_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public ExerciseImpl getNextExercise( String sessionID )
	{
		return getServiceTarget().path( SessionRestService.GET_NEXT_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public ExerciseImpl getPreviousExercise( String sessionID )
	{
		return getServiceTarget().path( SessionRestService.GET_PREVIOUS_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public void setNextExercise( String sessionID )
	{
		getServiceTarget().path( SessionRestService.SET_NEXT_EXERCISE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) );
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
	public void addExecuter( Invitation invitation )
	{
		getServiceTarget().path( SessionRestService.ADD_EXECUTER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( invitation ) );
	}

	@Override
	public void removeExecuter( Invitation invitation )
	{
		getServiceTarget().path( SessionRestService.REMOVE_EXECUTER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( invitation ) );
	}

}
