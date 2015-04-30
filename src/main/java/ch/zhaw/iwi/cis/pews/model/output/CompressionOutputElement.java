package ch.zhaw.iwi.cis.pews.model.output;

public class CompressionOutputElement
{
	private String solution;
	private String description;

	public CompressionOutputElement()
	{
		super();
	}

	public CompressionOutputElement( String solution, String description )
	{
		super();
		this.solution = solution;
		this.description = description;
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
