package ch.zhaw.iwi.cis.pews.model.wrappers;

public class TokenAuthenticationResponse
{
	private String login;
	private String credential;
	private String target;
	private String sessionID;

	public TokenAuthenticationResponse()
	{
		super();
	}

	public TokenAuthenticationResponse( String login, String credential, String target, String sessionID )
	{
		super();
		this.login = login;
		this.credential = credential;
		this.target = target;
		this.sessionID = sessionID;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin( String login )
	{
		this.login = login;
	}

	public String getCredential()
	{
		return credential;
	}

	public void setCredential( String credential )
	{
		this.credential = credential;
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
