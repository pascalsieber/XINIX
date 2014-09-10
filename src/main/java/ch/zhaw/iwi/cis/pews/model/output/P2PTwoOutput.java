package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;

public class P2PTwoOutput extends Output
{
	private List< String > chosenKeywords;
	private List< String > answers;

	public P2PTwoOutput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public P2PTwoOutput( List< String > chosenKeywords, List< String > answers )
	{
		super();
		this.chosenKeywords = chosenKeywords;
		this.answers = answers;
	}

	public List< String > getChosenKeywords()
	{
		return chosenKeywords;
	}

	public void setChosenKeywords( List< String > chosenKeywords )
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
