package ch.zhaw.iwi.cis.pews.model.authentication;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class AuthenticationToken extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String token;
	private String target;
	private String sessionID;

	public AuthenticationToken()
	{
		super();
	}

	public AuthenticationToken( PrincipalImpl owner, String token, String target, String sessionID )
	{
		super( owner );
		this.target = target;
		this.token = token;
		this.sessionID=sessionID;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget( String target )
	{
		this.target = target;
	}

	public String getSessionID()
	{
		return sessionID;
	}

	public void setSessionID( String sessionID )
	{
		this.sessionID = sessionID;
	}
}
