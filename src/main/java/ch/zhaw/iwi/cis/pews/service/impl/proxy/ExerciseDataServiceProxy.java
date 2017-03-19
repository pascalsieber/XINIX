package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressableExerciseData;

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

	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		// not intended for use in service proxy, only by service instances internally
		throw new UnsupportedOperationException( "unsupported operation in Service Proxy" );
	}

	@Override
	public void removeExerciseDataByID( String id )
	{
		getServiceTarget().path( ExerciseDataRestService.REMOVE_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) );
	}

	@Override
	public byte[] exportByExerciseID( String exerciseID )
	{
		//return getServiceTarget().path( ExerciseDataRestService.EXPORT_BY_EXERCISE_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( exerciseID ) ).readEntity( String.class );
		return new byte[] {};
	}

	@Override
	public byte[] exportByWorkshopID( String workshopID )
	{
		//getServiceTarget().path( ExerciseDataRestService.EXPORT_BY_WORKSHOP_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( workshopID ) ).readEntity( String.class ).
		return new byte[] {};
	}

	@Override
	public List< ExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		// not intended for use in service proxy, only by service instances internally
		throw new UnsupportedOperationException( "unsupported operation in Service Proxy" );
	}

	@Override
	public List< CompressableExerciseData > getCompressableExerciseDataByWorkshop( WorkshopImpl workshop )
	{
		// not intended for use in service proxy, only by service instances internally
		throw new UnsupportedOperationException( "unsupported operation in Service Proxy" );
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return getServiceTarget().path( ExerciseDataRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( obj ) ).readEntity( String.class );
	}
}
