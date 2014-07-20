package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;

@Path( ExerciseDataRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseDataRestService extends IdentifiableObjectRestService
{
	public final static String BASE = "/exerciseService/data";

	private ExerciseDataService exerciseDataService;

	public ExerciseDataRestService()
	{
		exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
	}

//	@POST
//	@Path( PERSIST )
//	public int persist( ExerciseDataImpl obj )
//	{
//		return super.persist( obj );
//	}
//
//	@POST
//	@Path( FIND_BY_ID )
//	public ExerciseDataImpl findById( int id )
//	{
//		return super.findByID( id );
//	}
//
//	@POST
//	@Path( REMOVE )
//	public void remove( ExerciseDataImpl obj )
//	{
//		super.remove( obj );
//	}
//
//	@POST
//	@Path( FIND_ALL )
//	public List< ExerciseDataImpl > findAll()
//	{
//		return super.findAll( ExerciseDataImpl.class.getSimpleName() );
//	}
//	
	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return exerciseDataService;
	}

}
