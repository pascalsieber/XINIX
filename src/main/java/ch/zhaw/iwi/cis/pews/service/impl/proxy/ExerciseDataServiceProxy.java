package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService;

public class ExerciseDataServiceProxy extends WorkshopObjectServiceProxy implements ExerciseDataService
{

	protected ExerciseDataServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseDataRestService.BASE );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return getServiceTarget().path( ExerciseDataRestService.FIND_BY_EXERCISE_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( List.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findAllExerciseData()
	{
		return getServiceTarget().path( ExerciseDataRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return getServiceTarget().path( ExerciseDataRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( ExerciseDataImpl.class );

	}

}
