package ch.zhaw.iwi.cis.pews.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class OwnableObject extends IdentifiableObject
{

	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private PrincipalImpl owner;

	public OwnableObject()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public OwnableObject( PrincipalImpl owner )
	{
		super();
		this.owner = owner;
	}

	public PrincipalImpl getOwner()
	{
		return owner;
	}

	public void setOwner( PrincipalImpl owner )
	{
		this.owner = owner;
	}

}
