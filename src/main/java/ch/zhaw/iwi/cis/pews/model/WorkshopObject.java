package ch.zhaw.iwi.cis.pews.model;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class WorkshopObject extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Client client;

	public WorkshopObject()
	{
		super();
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient( Client client )
	{
		this.client = client;
	}

}
