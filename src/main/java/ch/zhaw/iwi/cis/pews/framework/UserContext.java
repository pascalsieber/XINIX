package ch.zhaw.iwi.cis.pews.framework;

import ch.zhaw.iwi.cis.pews.model.user.UserImpl;

// TODO Might want to make this into a service
public class UserContext
{
	private static ThreadLocal< UserImpl > currentUser = new ThreadLocal< UserImpl >();
	
	public synchronized static UserImpl getCurrentUser()
	{
		return currentUser.get();
	}
	
	public synchronized static void setCurrentUser( UserImpl user )
	{
		currentUser.set( user );
	}
}
