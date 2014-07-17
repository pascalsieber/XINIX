package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseService;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;

@Path( ExerciseRestService.EX_BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExerciseRestService extends IdentifiableObjectRestService
{

	public final static String EX_BASE = "/exerciseService";

	public static final String EXDEF_PERSIST = "/definition" + PERSIST;
	public static final String EXDEF_FIND_BY_ID = "/definition" + FIND_BY_ID;
	public static final String EXDEF_REMOVE = "/definition" + REMOVE;
	public static final String EXDEF_FIND_ALL = "/definition" + FIND_ALL;

	public static final String EX_PERSIST = "/exercise" + PERSIST;
	public static final String EX_FIND_BY_ID = "/exercise" + FIND_BY_ID;
	public static final String EX_REMOVE = "/exercise" + REMOVE;
	public static final String EX_FIND_ALL = "/exercise" + FIND_ALL;

	public static final String EX_START = "/exercise/start";
	public static final String EX_STOP = "/exercise/stop";

	public static final String EXDATA_PERSIST = "/exercise/data" + PERSIST;
	public static final String EXDATA_FIND_BY_ID = "/exercise/data" + FIND_BY_ID;
	public static final String EXDATA_REMOVE = "/exercise/data" + REMOVE;
	public static final String EXDATA_FIND_ALL = "/exercise/data" + FIND_ALL;

	private ExerciseService exerciseService;

	public ExerciseRestService()
	{
		exerciseService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( EXDEF_PERSIST )
	public int persistExerciseDefinition( ExerciseDefinitionImpl definition )
	{
		return super.persist( definition );
	}

	@POST
	@Path( EXDEF_FIND_BY_ID )
	public ExerciseDefinitionImpl findExerciseDefinitionById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( EXDEF_REMOVE )
	public void removeExerciseDefinition( ExerciseDefinitionImpl definition )
	{
		super.remove( definition );
	}

	@POST
	@Path( EXDEF_FIND_ALL )
	public List< ExerciseDefinitionImpl > findAllExerciseDefinitions()
	{
		return super.findAll( ExerciseDefinitionImpl.class.getSimpleName() );
	}

	@POST
	@Path( EX_PERSIST )
	public int persistExercise( ExerciseImpl exercise )
	{
		return super.persist( exercise );
	}

	@POST
	@Path( EX_FIND_BY_ID )
	public ExerciseImpl findExerciseById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( EX_REMOVE )
	public void removeExercise( ExerciseImpl exercise )
	{
		super.remove( exercise );
	}

	@POST
	@Path( EX_FIND_ALL )
	public List< ExerciseImpl > findAllExercises()
	{
		return super.findAll( ExerciseImpl.class.getSimpleName() );
	}

	@POST
	@Path( EX_START )
	public void statExercise( ExerciseImpl exercise )
	{
		exerciseService.start( exercise.getID() );
	}

	@POST
	@Path( EX_STOP )
	public void stopExercise( ExerciseImpl exercise )
	{
		exerciseService.stop( exercise.getID() );
	}

	@POST
	@Path( EXDATA_PERSIST )
	public int persistExerciseData( ExerciseDataImpl data )
	{
		return super.persist( data );
	}
	
	@POST
	@Path( EXDATA_FIND_BY_ID )
	public ExerciseDataImpl findExerciseDataByID( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( EXDATA_REMOVE )
	public void removeExerciseData( ExerciseDataImpl data )
	{
		super.remove( data );
	}
	
	@POST
	@Path( EXDATA_FIND_ALL )
	public List< ExerciseDataImpl > findAllExerciseData()
	{
		return super.findAll( ExerciseDataImpl.class.getSimpleName() );
	}
	
	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return exerciseService;
	}

}
