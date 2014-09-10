package ch.zhaw.iwi.cis.pews.model.output;

import java.util.HashSet;
import java.util.Set;

public class CompressionOutput extends Output
{
	private Set< String > solutions;

	public CompressionOutput()
	{
		super();
		this.solutions = new HashSet<>();
	}

	public CompressionOutput( Set< String > solutions )
	{
		super();
		this.solutions = solutions;
	}

	public Set< String > getSolutions()
	{
		return solutions;
	}

	public void setSolutions( Set< String > solutions )
	{
		this.solutions = solutions;
	}

}
