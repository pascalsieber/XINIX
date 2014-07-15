package ch.zhaw.iwi.cis.pews.model.user;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class PasswordCredentialImpl extends CredentialImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String password;

	public PasswordCredentialImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordCredentialImpl( String password )
	{
		super();
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

}
