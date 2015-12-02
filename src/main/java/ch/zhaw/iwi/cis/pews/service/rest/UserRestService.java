package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;

@Path( UserRestService.USER_BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class UserRestService extends WorkshopObjectRestService
{

	public static final String USER_BASE = "/userService/user";
	public static final String FIND_BY_LOGIN_NAME = "/findByLogin";
	public static final String REQUEST_PASSWORD = "/requestPassword";
	public static final String SEND_PROFILE = "/sendProfile";

	public static final String PERSIST_FOR_CLIENT = "persistForClient";

	public UserRestService()
	{
		super();
	}

	@POST
	@Path( PERSIST )
	public String persist( PrincipalImpl principal )
	{
		return super.persist( principal );
	}

	@POST
	@Path( PERSIST_FOR_CLIENT )
	public String persistUserForClient( PrincipalImpl principal )
	{
		return getUserService().persistForClient( principal );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public PrincipalImpl findByID( String id )
	{
		return getUserService().findUserByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( PrincipalImpl principal )
	{
		super.remove( principal );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< UserImpl > findAll()
	{
		return super.findAll();
	}

	@POST
	@Path( FIND_ALL_BY_CLIENT_ID )
	public List< UserImpl > findAllUsersByClientID( String clientID )
	{
		return super.findAllByClientID( clientID );
	}

	@POST
	@Path( FIND_BY_LOGIN_NAME )
	public PrincipalImpl findByLoginName( String loginName )
	{
		return getUserService().findByLoginName( loginName );
	}

	@POST
	@Path( REQUEST_PASSWORD )
	public boolean requestNewPassword( String userID )
	{
		return getUserService().requestNewPassword( userID );
	}

	@POST
	@Path( SEND_PROFILE )
	public void sendUserProfile( String userID )
	{
		getUserService().sendProfile( userID );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return getUserService();
	}

}
