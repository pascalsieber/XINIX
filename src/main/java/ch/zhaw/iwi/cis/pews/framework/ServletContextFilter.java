package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;
import ch.zhaw.iwi.cis.pews.service.rest.AuthenticationTokenRestService;

public class ServletContextFilter implements Filter
{
	private UserService userService;

	private UserService getUserService()
	{
		if ( userService == null )
			userService = new UserServiceImpl();
			//userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );

		return userService;
	}

	public UserImpl getCurrentUser( HttpServletRequest request )
	{
		String loginName = request.getUserPrincipal().getName();
		UserImpl user = (UserImpl)getUserService().findByLoginNameForUserContext( loginName );

		return user;
	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{
	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException
	{
		if ( !( (HttpServletRequest)request ).getRequestURI().contains( AuthenticationTokenRestService.AUTHENTICATE_WITH_TOKEN ) )
		{
			UserContext.setCurrentUser( getCurrentUser( (HttpServletRequest)request ) );
		}

		chain.doFilter( request, response );
	}

	@Override
	public void destroy()
	{
	}
}
