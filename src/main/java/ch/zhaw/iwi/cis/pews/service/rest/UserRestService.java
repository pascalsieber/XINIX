package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.RoleImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;

@Path( UserRestService.USER_BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class UserRestService extends IdentifiableObjectRestService
{

	public static final String USER_BASE = "/userService";

	public static final String USER_PERSIST = "/user" + PERSIST;
	public static final String USER_FIND_BY_ID = "/user" + FIND_BY_ID;
	public static final String USER_REMOVE = "/user" + REMOVE;
	public static final String USER_FIND_ALL = "/user" + FIND_ALL;

	public static final String USER_ACCEPT_INVITATION = "/acceptInvitation";

	public static final String ROLE_PERSIST = "/role" + PERSIST;
	public static final String ROLE_FIND_BY_ID = "/role" + FIND_BY_ID;
	public static final String ROLE_REMOVE = "/role" + REMOVE;
	public static final String ROLE_FIND_ALL = "/role" + FIND_ALL;

	private UserService userService;

	public UserRestService()
	{
		userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( USER_PERSIST )
	public int persistUser( PrincipalImpl principal )
	{
		return super.persist( principal );
	}

	@POST
	@Path( USER_FIND_BY_ID )
	public PrincipalImpl findUserById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( USER_REMOVE )
	public void removeUser( PrincipalImpl principal )
	{
		super.remove( principal );
	}

	@POST
	@Path( USER_FIND_ALL )
	public List< UserImpl > findAllUsers()
	{
		return super.findAll( UserImpl.class.getSimpleName() );
	}

	@POST
	@Path( USER_ACCEPT_INVITATION )
	public void acceptInvitation( int userID, int sessionID )
	{
		userService.acceptInvitation(userID, sessionID);
	}

	@POST
	@Path( ROLE_PERSIST )
	public int persistRole( RoleImpl role )
	{
		return super.persist( role );
	}

	@POST
	@Path( ROLE_FIND_BY_ID )
	public RoleImpl findRoleById( int id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( ROLE_REMOVE )
	public void removeRole( RoleImpl role )
	{
		super.remove( role );
	}

	@POST
	@Path( ROLE_FIND_ALL )
	public List< RoleImpl > findAllRoles()
	{
		return super.findAll( RoleImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return userService;
	}

}
