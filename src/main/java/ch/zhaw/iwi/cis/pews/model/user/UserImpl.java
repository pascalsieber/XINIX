package ch.zhaw.iwi.cis.pews.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

@Entity
public class UserImpl extends PrincipalImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String loginName;

	@ManyToMany
	private Set< GroupImpl > groups;

	public UserImpl()
	{
		super();
		this.groups = new HashSet< GroupImpl >();
	}

	public UserImpl( PasswordCredentialImpl credential, RoleImpl role, SessionImpl session, String firstName, String lastName, String loginName )
	{
		super( credential, role, session );
		this.groups = new HashSet< GroupImpl >();
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginName = loginName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName( String loginName )
	{
		this.loginName = loginName;
	}

	public Set< GroupImpl > getGroups()
	{
		return groups;
	}

	public void setGroups( Set< GroupImpl > groups )
	{
		this.groups = groups;
	}

}
