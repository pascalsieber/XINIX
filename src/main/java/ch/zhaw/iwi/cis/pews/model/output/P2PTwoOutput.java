package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P2PTwoOutput extends Output
{
	private Set< String > chosenKeywords;
	private List< String > answers;

	public P2PTwoOutput()
	{
		super();
		this.chosenKeywords = new HashSet<>();
		this.answers = new ArrayList<>();
	}

	public P2PTwoOutput( Set< String > chosenKeywords, List< String > answers )
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

	public List< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( List< String > answers )
	{
		this.answers = answers;
	}

}
