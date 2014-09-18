package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class CompressionOutput extends Output
{
	private List< String > solutions;

	public CompressionOutput()
	{
		super();
		this.solutions = new ArrayList<>();
	}

	public CompressionOutput( List< String > solutions )
	{
		super();
		this.solutions = solutions;
	}

	public List< String > getSolutions()
	{
		return solutions;
	}

	public void setSolutions( List< String > solutions )
	{
		this.solutions = solutions;
	}

}
