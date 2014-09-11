package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class PinkLabsOutput extends Output
{
	private List< String > answers;

	public PinkLabsOutput()
	{
		super();
		this.answers = new ArrayList<>();
	}

	public PinkLabsOutput( List< String > answers )
	{
		super();
		this.answers = answers;
	}

	public List< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( List< String > answers )
	{
		this.answers = answers;
	}

}
