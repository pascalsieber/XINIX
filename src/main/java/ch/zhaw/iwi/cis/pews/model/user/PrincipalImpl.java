package ch.zhaw.iwi.cis.pews.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class PrincipalImpl extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToOne( cascade = CascadeType.ALL )
	private PasswordCredentialImpl credential;

	@ManyToOne
	private RoleImpl role;

	@OneToOne( mappedBy = "principal" )
	private Participant participation;

	@OneToMany( mappedBy = "invitee" )
	private Set< Invitation > sessionInvitations;

	@ManyToMany
	@JoinTable( name = "principalimpl_sessionimpl_accept" )
	private Set< SessionImpl > sessionAcceptances;

	@ManyToMany
	@JoinTable( name = "principalimpl_sessionimpl_execute" )
	private Set< SessionImpl > sessionExecutions;

	public PrincipalImpl()
	{
		super();
	}

	public PrincipalImpl( PasswordCredentialImpl credential, RoleImpl role, Participant participation )
	{
		this.credential = credential;
		this.role = role;
		this.participation = participation;
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

	public Participant getParticipation()
	{
		return participation;
	}

	public void setParticipation( Participant participation )
	{
		this.participation = participation;
	}

	// helper method to not have to refactor every instance where principal.getSession() is called
	@JsonIgnore
	public SessionImpl getSession()
	{
		if ( participation == null )
		{
			return null;
		}
		else
		{
			return participation.getSession();
		}
	}

}
