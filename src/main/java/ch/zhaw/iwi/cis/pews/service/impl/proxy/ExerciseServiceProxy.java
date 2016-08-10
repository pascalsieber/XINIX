package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService;

public class ExerciseServiceProxy extends WorkshopObjectServiceProxy implements ExerciseService
{

	protected ExerciseServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseRestService.BASE );
	}

	@Override
	public Input getInput()
	{
		return getServiceTarget().path( ExerciseRestService.GETINPUT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( Input.class );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		return getServiceTarget().path( ExerciseRestService.GETINPUT_BY_EXERCISEID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( Input.class );
	}

	@Override
	public String getInputAsString()
	{
		return getServiceTarget().path( ExerciseRestService.GETINPUT_AS_STRING ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( String.class );
	}

	@Override
	public String getInputByExerciseIDAsString( String exerciseID )
	{
		return getServiceTarget().path( ExerciseRestService.GETINPUT_BY_EXERCISEID_AS_STRING ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( String.class );
	}

	@Override
	public void setOutput( String output )
	{
		getServiceTarget().path( ExerciseRestService.SETOUTPUT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( output ) );
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		getServiceTarget().path( ExerciseRestService.SETOUTPUT_BY_EXERCISEID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( outputRequestString ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > getOutput()
	{
		return getServiceTarget().path( ExerciseRestService.GETOUTPUT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseImpl > findAllExercises()
	{
		return getServiceTarget().path( ExerciseRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@Override
	public ExerciseImpl findExerciseByID( String id )
	{
		return getServiceTarget().path( ExerciseRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( ExerciseImpl.class );
	}

	@Override
	public String persistExercise( ExerciseImpl exercise )
	{
		return getServiceTarget().path( ExerciseRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exercise ) ).readEntity( String.class );
	}

	@Override
	public String generateFromTemplate( ExerciseImpl obj )
	{
		throw new UnsupportedOperationException( "internal method. not available via REST" );
	}

	@Override
	public void start(String id)
	{
		throw new UnsupportedOperationException( "internal method. not available via REST" );
	}

	@Override
	public void stop(String id)
	{
		throw new UnsupportedOperationException( "internal method. not available via REST" );
	}

	@Override
	public void renew(String id)
	{
		throw new UnsupportedOperationException("internal method. not available via REST");
	}
}
