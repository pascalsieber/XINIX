package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;

import org.eclipse.jetty.security.MappedLoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.security.Credential;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;

public class ZhawJDBCLoginService extends MappedLoginService
{
	private UserService userService;

	public ZhawJDBCLoginService()
	{
		super();
		setName( "ZHAWRealm" );
		userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );
	}

	private UserIdentity loadUserHelper( PrincipalImpl principal )
	{

		if ( principal == null )
		{
			return null;
		}

		Credential credential = Credential.getCredential( principal.getCredential().getPassword() );

		String[] roles = new String[ 2 ];
		roles[ 0 ] = principal.getRole().getName();
		roles[ 1 ] = "registered";

		return putUser( ( (UserImpl)principal ).getLoginName(), credential, roles );
	}

	@Override
	protected UserIdentity loadUser( String username )
	{
		return loadUserHelper( (PrincipalImpl)userService.findByLoginName( username ) );
	}

	@Override
	protected void loadUsers() throws IOException
	{
		for ( PrincipalImpl user : userService.findAllUsersForLoginService() )
		{
			loadUserHelper( user );
		}
	}

	@Override
	public UserIdentity login( String username, Object credentials )
	{
		// if login fails, try reloading user (in case of password changes)
		if ( super.login( username, credentials ) == null )
		{
			this.loadUser( username );
		}
		
		return super.login( username, credentials );
	}

}
