package ch.zhaw.iwi.cis.pews.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
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
	public static final String EX_ADDDATA = "/exercise/addData";
	public static final String EX_GETDATA = "/exercise/getData";

	private ExerciseService exerciseService;

	public ExerciseRestService()
	{
		exerciseService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseServiceImpl.class.getSimpleName() );
	}
	
	

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return exerciseService;
	}

}
