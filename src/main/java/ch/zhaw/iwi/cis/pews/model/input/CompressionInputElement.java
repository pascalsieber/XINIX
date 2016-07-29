package ch.zhaw.iwi.cis.pews.model.input;

public class CompressionInputElement
{
	private String id;
	private String solution;
	private String description;

	public CompressionInputElement()
	{
		super();
	}

	public CompressionInputElement( String id, String solution, String description )
	{
		super();
		this.id = id;
		this.solution = solution;
		this.description = description;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getSolution()
	{
		return solution;
	}

	public void setSolution( String solution )
	{
		this.solution = solution;
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
