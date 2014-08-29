package ch.zhaw.iwi.cis.pews.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

@Entity
public class GroupImpl extends PrincipalImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToMany
	private Set< UserImpl > members;

	public GroupImpl()
	{
		super();
		this.members = new HashSet< UserImpl >();
	}

	public GroupImpl( PasswordCredentialImpl credential, RoleImpl role, SessionImpl session, String name )
	{
		super( credential, role, session );
		this.name = name;
		this.members = new HashSet< UserImpl >();
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Set< UserImpl > getMembers()
	{
		return members;
	}

	public void setMembers( Set< UserImpl > members )
	{
		this.members = members;
	}

}
