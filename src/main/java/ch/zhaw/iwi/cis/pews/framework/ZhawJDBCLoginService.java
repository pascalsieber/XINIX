package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;

import org.eclipse.jetty.security.MappedLoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.security.Credential;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
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

	private UserIdentity loadUserHelper( UserImpl principal )
	{
		Credential credential = Credential.getCredential( principal.getCredential().getPassword() );

		String[] roles = new String[ 1 ];
		roles[ 0 ] = principal.getRole().getName();

		return putUser( principal.getLoginName(), credential, roles );
	}

	@Override
	protected UserIdentity loadUser( String username )
	{
		return loadUserHelper( (UserImpl)userService.findByLoginName( username ) );
	}

	@Override
	protected void loadUsers() throws IOException
	{
		for ( IdentifiableObject user : userService.findAll( UserImpl.class ) )
		{
			loadUserHelper( (UserImpl)user );
		}
	}

}
