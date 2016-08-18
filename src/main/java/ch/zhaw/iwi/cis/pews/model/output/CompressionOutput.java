package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class CompressionOutput extends Output
{
	private List<CompressionOutputElement> solutions;

	public CompressionOutput()
	{
		super();
		this.solutions = new ArrayList<>();
	}

	public CompressionOutput( String exerciseID, List<CompressionOutputElement> solutions )
	{
		super( exerciseID );
		this.solutions = solutions;
	}

	public List<CompressionOutputElement> getSolutions()
	{
		return solutions;
	}

	public void setSolutions( List<CompressionOutputElement> solutions )
	{
		this.solutions = solutions;
	}

}
