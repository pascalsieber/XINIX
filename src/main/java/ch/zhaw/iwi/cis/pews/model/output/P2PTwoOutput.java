package ch.zhaw.iwi.cis.pews.model.output;

import java.util.HashSet;
import java.util.Set;

public class P2PTwoOutput extends Output
{
	private Set< String > chosenKeywords;
	private Set< String > answers;

	public P2PTwoOutput()
	{
		super();
		this.chosenKeywords = new HashSet<>();
		this.answers = new HashSet<>();
	}

	public P2PTwoOutput( Set< String > chosenKeywords, Set< String > answers )
	{
		super();
		this.chosenKeywords = chosenKeywords;
		this.answers = answers;
	}

	public Set< String > getChosenKeywords()
	{
		return chosenKeywords;
	}

	public void setChosenKeywords( Set< String > chosenKeywords )
	{
		this.chosenKeywords = chosenKeywords;
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
