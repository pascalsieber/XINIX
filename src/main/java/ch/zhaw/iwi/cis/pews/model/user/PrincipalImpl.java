package ch.zhaw.iwi.cis.pews.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class PrincipalImpl extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToOne( cascade = CascadeType.ALL )
	private PasswordCredentialImpl credential;

	@ManyToOne
	private RoleImpl role;

	@ManyToOne
	private SessionImpl session;

	@OneToMany( mappedBy = "invitee" )
	private Set< Invitation > sessionInvitations;

	@ManyToMany
	private Set< SessionImpl > sessionAcceptances;

	@ManyToMany
	private Set< SessionImpl > sessionExecutions;

	public PrincipalImpl()
	{
		super();
		this.sessionAcceptances = new HashSet< SessionImpl >();
		this.sessionInvitations = new HashSet< Invitation >();
		this.sessionExecutions = new HashSet< SessionImpl >();
	}

	public PrincipalImpl( PasswordCredentialImpl credential, RoleImpl role, SessionImpl session )
	{
		super();
		this.credential = credential;
		this.role = role;
		this.session = session;
		this.sessionAcceptances = new HashSet< SessionImpl >();
		this.sessionInvitations = new HashSet< Invitation >();
		this.sessionExecutions = new HashSet< SessionImpl >();
	}

	public PasswordCredentialImpl getCredential()
	{
		return credential;
	}

	public void setCredential( PasswordCredentialImpl credential )
	{
		this.credential = credential;
	}

	public RoleImpl getRole()
	{
		return role;
	}

	public void setRole( RoleImpl role )
	{
		this.role = role;
	}

	public SessionImpl getSession()
	{
		return session;
	}

	public void setSession( SessionImpl session )
	{
		this.session = session;
	}

	public Set< Invitation > getSessionInvitations()
	{
		return sessionInvitations;
	}

	public void setSessionInvitations( Set< Invitation > sessionInvitations )
	{
		this.sessionInvitations = sessionInvitations;
	}

	public Set< SessionImpl > getSessionAcceptances()
	{
		return sessionAcceptances;
	}

	public void setSessionAcceptances( Set< SessionImpl > sessionAcceptances )
	{
		this.sessionAcceptances = sessionAcceptances;
	}

	public Set< SessionImpl > getSessionExecutions()
	{
		return sessionExecutions;
	}

	public void setSessionExecutions( Set< SessionImpl > sessionExecutions )
	{
		this.sessionExecutions = sessionExecutions;
	}

}
