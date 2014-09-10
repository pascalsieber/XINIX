package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class P2POneOutput extends Output
{
	private List< String > answers;

	public P2POneOutput()
	{
		super();
		this.answers = new ArrayList<>();
	}

	public P2POneOutput( List< String > answers )
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
