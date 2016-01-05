package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.AuthenticationTokenService;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.impl.UserServiceImpl;
import ch.zhaw.iwi.cis.pews.service.rest.AuthenticationTokenRestService;

public class ServletContextFilter implements Filter
{
	private static ThreadLocal< HttpServletRequest > servletRequest = new ThreadLocal< HttpServletRequest >();
	private static ThreadLocal< HttpServletResponse > servletResponse = new ThreadLocal< HttpServletResponse >();

	private UserService userService;

	private UserService getUserService()
	{
		if ( userService == null )
			userService = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserServiceImpl.class.getSimpleName() );

		return userService;
	}

	public UserImpl getCurrentUser( HttpServletRequest request )
	{
		String loginName = request.getUserPrincipal().getName();
		UserImpl user = (UserImpl)getUserService().findByLoginName( loginName );

		return user;
	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{
	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException
	{
		servletRequest.set( (HttpServletRequest)request );
		servletResponse.set( (HttpServletResponse)response );

		if ( !( (HttpServletRequest)request ).getRequestURI().contains( AuthenticationTokenRestService.AUTHENTICATE_WITH_TOKEN ) )
		{
			UserContext.setCurrentUser( getCurrentUser( servletRequest.get() ) );
		}

		chain.doFilter( request, response );
	}

	@Override
	public void destroy()
	{
	}

	public static HttpServletRequest getServletRequest()
	{
		return servletRequest.get();
	}

	public static HttpServletResponse getServletResponse()
	{
		return servletResponse.get();
	}
}
