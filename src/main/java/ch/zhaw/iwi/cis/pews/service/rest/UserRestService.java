package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

	public UserRestService()
	{
		super();
	}

	@POST
	@Path( PERSIST )
	public String persistUser( PrincipalImpl principal )
	{
		return super.persist( principal );
	}

	@POST
	@Path( FIND_BY_ID )
	public PrincipalImpl findUserById( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeUser( PrincipalImpl principal )
	{
		super.remove( principal );
	}

	@POST
	@Path( FIND_ALL )
	public List< UserImpl > findAllUsers( @Context HttpServletRequest request )
	{
		return super.findAll( getUserService().getClientFromAuth( request ) );
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

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return getUserService();
	}

}
