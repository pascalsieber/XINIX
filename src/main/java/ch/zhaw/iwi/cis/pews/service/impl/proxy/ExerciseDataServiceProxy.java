package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService;

public class ExerciseDataServiceProxy extends IdentifiableObjectServiceProxy implements ExerciseDataService
{

	protected ExerciseDataServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseDataRestService.BASE );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( int exerciseID )
	{
		return getServiceTarget().path( ExerciseDataRestService.FIND_BY_EXERCISE_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( List.class );
	}

}
