package ch.zhaw.iwi.cis.pews.model.instance;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class Participant extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToOne
	private PrincipalImpl principal;

	@ManyToOne
	private SessionImpl session;

	public Participant()
	{
		super();
	}

	public Participant( PrincipalImpl principal, SessionImpl session )
	{
		super();
		this.principal = principal;
		this.session = session;
	}

	public PrincipalImpl getPrincipal()
	{
		return principal;
	}

	public void setPrincipal( PrincipalImpl principal )
	{
		this.principal = principal;
	}

	public SessionImpl getSession()
	{
		return session;
	}

	public void setSession( SessionImpl session )
	{
		this.session = session;
	}
}
