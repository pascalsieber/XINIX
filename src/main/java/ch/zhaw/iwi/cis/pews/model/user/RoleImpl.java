package ch.zhaw.iwi.cis.pews.model.user;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

@Entity
public class RoleImpl extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	public RoleImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleImpl( String name, String description )
	{
		super();
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
