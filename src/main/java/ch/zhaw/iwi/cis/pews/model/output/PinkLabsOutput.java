package ch.zhaw.iwi.cis.pews.model.output;

import java.util.HashSet;
import java.util.Set;

public class PinkLabsOutput extends Output
{
	private Set< String > answers;

	public PinkLabsOutput()
	{
		super();
		this.answers = new HashSet<>();
	}

	public PinkLabsOutput( Set< String > answers )
	{
		super();
		this.answers = answers;
	}

	public Set< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( Set< String > answers )
	{
		this.answers = answers;
	}

}
