package ch.zhaw.iwi.cis.pews.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Client extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;

	public Client()
	{
		super();
	}

	public Client( String name )
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

}
