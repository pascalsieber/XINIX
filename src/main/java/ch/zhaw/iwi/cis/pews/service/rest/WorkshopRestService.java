package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ThreadLocalFilter;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopServiceImpl;

@Path( WorkshopRestService.WS_BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class WorkshopRestService extends IdentifiableObjectRestService
{

	public static final String WS_BASE = "/workshopService";

	public static final String WSDEF_PERSIST = "/definition" + PERSIST;
	public static final String WSDEF_FIND_BY_ID = "/definition" + FIND_BY_ID;
	public static final String WSDEF_REMOVE = "/definition" + REMOVE;
	public static final String WSDEF_FIND_ALL = "/definition" + FIND_ALL;

	public static final String WS_PERSIST = "/workshop" + PERSIST;
	public static final String WS_FIND_BY_ID = "/workshop" + FIND_BY_ID;
	public static final String WS_REMOVE = "/workshop" + REMOVE;
	public static final String WS_FIND_ALL = "/workshop" + FIND_ALL;

	public static final String WS_START = "/workshop/start";
	public static final String WS_STOP = "/workshop/stop";

	public static final String SESSION_PERSIST = "/session" + PERSIST;
	public static final String SESSION_FIND_BY_ID = "/session" + FIND_BY_ID;
	public static final String SESSION_REMOVE = "/session" + REMOVE;
	public static final String SESSION_FIND_ALL = "/session" + FIND_ALL;

	public static final String SESSION_JOIN = "/session/join";
	public static final String SESSION_LEAVE = "/session/leave";
	public static final String SESSION_GET_CURRENT_EX = "/session/getCurrentExercise";
	public static final String SESSION_GET_NEXT_EX = "/session/getNextExercise";
	public static final String SESSION_GET_PREVIOUS_EX = "/session/getPreviousExercise";
	public static final String SESSION_SET_NEXT_EX = "/session/setNextExercise";

	private WorkshopService workshopService;
	private UserService userService;

	public WorkshopRestService()
	{
		workshopService = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopServiceImpl.class.getSimpleName() );
		userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( WSDEF_PERSIST )
	public int persistWorkshopDefinition( WorkshopDefinitionImpl definition )
	{
		return super.persist( definition );
	}

	@POST
	@Path( WSDEF_FIND_BY_ID )
	public WorkshopDefinitionImpl findWorkshopDefinitionById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( WSDEF_REMOVE )
	public void removeWorkshopDefinition( WorkshopDefinitionImpl workshop )
	{
		super.remove( workshop );
	}

	@POST
	@Path( WSDEF_FIND_ALL )
	public List< WorkshopDefinitionImpl > findAllWorkshopDefinitions()
	{
		return super.findAll( WorkshopDefinitionImpl.class.getSimpleName() );
	}

	@POST
	@Path( WS_PERSIST )
	public int persistWorkshop( WorkshopImpl workshop )
	{
		return super.persist( workshop );
	}

	@POST
	@Path( WS_FIND_BY_ID )
	public WorkshopImpl findWorkshopById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( WS_REMOVE )
	public void removeWorkshop( WorkshopImpl workshop )
	{
		super.remove( workshop );
	}

	@POST
	@Path( WS_FIND_ALL )
	public List< WorkshopImpl > findAllSessions()
	{
		return super.findAll( WorkshopImpl.class.getSimpleName() );
	}

	@POST
	@Path( WS_START )
	public void statWorkshop( WorkshopImpl workshop )
	{
		workshopService.start( workshop.getID() );
	}

	@POST
	@Path( WS_STOP )
	public void stopWorkshop( WorkshopImpl workshop )
	{
		workshopService.stop( workshop.getID() );
	}

	@POST
	@Path( SESSION_PERSIST )
	public int persistSession( WorkshopImpl workshop )
	{
		return super.persist( workshop );
	}

	@POST
	@Path( SESSION_FIND_BY_ID )
	public WorkshopImpl findSessionById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( SESSION_REMOVE )
	public void removeSession( WorkshopImpl workshop )
	{
		super.remove( workshop );
	}

	@POST
	@Path( SESSION_FIND_ALL )
	public List< WorkshopImpl > findAllWorkshops()
	{
		return super.findAll( WorkshopImpl.class.getSimpleName() );
	}

	@POST
	@Path( SESSION_JOIN )
	public void joinSession( int sessionID )
	{
		PrincipalImpl user = userService.findByLoginName( ThreadLocalFilter.getServletRequest().getUserPrincipal().getName() );
		workshopService.joinSession( user.getID(), sessionID );
	}

	@POST
	@Path( SESSION_LEAVE )
	public void leaveSession( int sessionID )
	{
		PrincipalImpl user = userService.findByLoginName( ThreadLocalFilter.getServletRequest().getUserPrincipal().getName() );
		workshopService.leaveSession( user.getID(), sessionID );
	}

	@POST
	@Path( SESSION_GET_CURRENT_EX )
	public ExerciseImpl getCurrentExercise( int sessionID )
	{
		return workshopService.getCurrentExercise( sessionID );
	}

	@POST
	@Path( SESSION_GET_NEXT_EX )
	public ExerciseImpl getNextExercise( int sessionID )
	{
		return workshopService.getNextExercise( sessionID );
	}

	@POST
	@Path( SESSION_GET_PREVIOUS_EX )
	public ExerciseImpl getPreviousExercise( int sessionID )
	{
		return workshopService.getPreviousExercise( sessionID );
	}

	@POST
	@Path( SESSION_SET_NEXT_EX )
	public void setNextExercise( int sessionID )
	{
		workshopService.setNextExercise( sessionID );
	}
	
	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return workshopService;
	}

}
