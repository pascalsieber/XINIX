package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class CompressionOutput extends Output
{
	private List< CompressionOutputElement > solutions;

	public CompressionOutput()
	{
		super();
		this.solutions = new ArrayList<>();
	}

	public CompressionOutput( List< CompressionOutputElement > solutions )
	{
		super();
		this.solutions = solutions;
	}

	public List< CompressionOutputElement > getSolutions()
	{
		return solutions;
	}

	public void setSolutions( List< CompressionOutputElement > solutions )
	{
		this.solutions = solutions;
	}

}
