package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService;

public class ExerciseServiceProxy extends WorkshopObjectServiceProxy implements ExerciseService
{

	protected ExerciseServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseRestService.BASE );
	}

	@Override
	public void start( String id )
	{
		getServiceTarget().path( ExerciseRestService.START ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void stop( String id )
	{
		getServiceTarget().path( ExerciseRestService.STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public void suspend( SuspensionRequest suspensionRequest )
	{
		getServiceTarget().path( ExerciseRestService.SUSPEND ).request( MediaType.APPLICATION_JSON ).post( Entity.json( suspensionRequest ) );
	}

	@Override
	public double resume( String exerciseID )
	{
		return getServiceTarget().path( ExerciseRestService.RESUME ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( double.class );
	}

	@Override
	public Input getInput()
	{
		return getServiceTarget().path( ExerciseRestService.GETINPUT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( Input.class );
	}

	@Override
	public void setOutput( String output )
	{
		getServiceTarget().path( ExerciseRestService.GETINPUT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( output ) );
	}

	@Override
	public void startUser()
	{
		getServiceTarget().path( ExerciseRestService.START_USER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) );
	}

	@Override
	public void stopUser()
	{
		getServiceTarget().path( ExerciseRestService.STOP_USER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) );
	}

	@Override
	public void resetUser()
	{
		getServiceTarget().path( ExerciseRestService.RESET_USER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) );
	}

	@Override
	public void suspendUser( TimerRequest request )
	{
		getServiceTarget().path( ExerciseRestService.SUSPEND_USER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( request ) );
	}

	@Override
	public TimerRequest resumeUser()
	{
		return getServiceTarget().path( ExerciseRestService.RESUME_USER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( TimerRequest.class );
	}

	@Override
	public void cancelUser()
	{
		getServiceTarget().path( ExerciseRestService.CANCEL_USER ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) );
	}

	@Override
	public Participant findUserParticipant()
	{
		return getServiceTarget().path( ExerciseRestService.GET_USER_PARTICIPANT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( Participant.class );
	}

}
