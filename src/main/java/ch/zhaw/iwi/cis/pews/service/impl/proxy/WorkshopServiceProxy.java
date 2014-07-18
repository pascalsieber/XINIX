package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.rest.WorkshopRestService;

public class WorkshopServiceProxy extends IdentifiableObjectServiceProxy implements WorkshopService
{

	protected WorkshopServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, WorkshopRestService.WS_BASE );
	}

	@Override
	public void start( int id )
	{
		getServiceTarget().path( WorkshopRestService.WS_START ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void stop( int id )
	{
		getServiceTarget().path( WorkshopRestService.WS_STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void joinSession( int userID, int sessionID )
	{
		getServiceTarget().path( WorkshopRestService.SESSION_JOIN ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) );
	}

	@Override
	public void leaveSession( int userID, int sessionID )
	{
		getServiceTarget().path( WorkshopRestService.SESSION_LEAVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) );
	}

	@Override
	public ExerciseImpl getCurrentExercise( int sessionID )
	{
		return getServiceTarget().path( WorkshopRestService.SESSION_GET_CURRENT_EX ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public ExerciseImpl getNextExercise( int sessionID )
	{
		return getServiceTarget().path( WorkshopRestService.SESSION_GET_NEXT_EX ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public ExerciseImpl getPreviousExercise( int sessionID )
	{
		return getServiceTarget().path( WorkshopRestService.SESSION_GET_PREVIOUS_EX ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public void setNextExercise( int sessionID )
	{
		getServiceTarget().path( WorkshopRestService.SESSION_SET_NEXT_EX ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) );
	}

}
