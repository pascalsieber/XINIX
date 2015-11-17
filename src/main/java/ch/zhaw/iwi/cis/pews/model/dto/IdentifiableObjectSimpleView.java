package ch.zhaw.iwi.cis.pews.model.dto;

public class IdentifiableObjectSimpleView
{
	private String id;

	public IdentifiableObjectSimpleView()
	{
		super();
	}

	public IdentifiableObjectSimpleView( String id )
	{
		super();
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}
}
