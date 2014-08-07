package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseRestService;

public class ExerciseServiceProxy extends IdentifiableObjectServiceProxy implements ExerciseService
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
		getServiceTarget().path( ExerciseRestService.STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( suspensionRequest ) );
	}

	@Override
	public double resume( String exerciseID )
	{
		return getServiceTarget().path( ExerciseRestService.STOP ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( double.class );
	}

}
