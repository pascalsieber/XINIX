package ch.zhaw.iwi.cis.pews.model.user;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@Entity
public class RoleImpl extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	public RoleImpl()
	{
		super();
	}

	public RoleImpl( Client client, String name, String description )
	{
		super( client );
		this.name = name;
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

}
