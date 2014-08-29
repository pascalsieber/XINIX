package ch.zhaw.iwi.cis.pews.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

@Entity
public class Invitation extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private PrincipalImpl inviter;

	@ManyToOne
	private PrincipalImpl invitee;

	private Date date;

	@ManyToOne
	private SessionImpl session;

	public Invitation()
	{
		super();
	}

	public Invitation( PrincipalImpl inviter, PrincipalImpl invitee, SessionImpl session )
	{
		this.inviter = inviter;
		this.invitee = invitee;
		this.date = new Date();
		this.session = session;
	}

	public PrincipalImpl getInviter()
	{
		return inviter;
	}

	public void setInviter( PrincipalImpl inviter )
	{
		this.inviter = inviter;
	}

	public PrincipalImpl getInvitee()
	{
		return invitee;
	}

	public void setInvitee( PrincipalImpl invitee )
	{
		this.invitee = invitee;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
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
