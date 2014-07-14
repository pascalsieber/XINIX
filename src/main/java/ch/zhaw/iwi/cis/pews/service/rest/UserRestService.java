package ch.zhaw.iwi.cis.pews.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.IdentifiableObjectService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;

@Path(UserRestService.USER_BASE)
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class UserRestService extends IdentifiableObjectRestService
{

	public static final String USER_BASE = "/userService";
	
	private UserService userService;
		
	
	public UserRestService()
	{
		userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
	}



	@Override
	protected IdentifiableObjectService getPersistentObjectService()
	{
		return userService;
	}

}
