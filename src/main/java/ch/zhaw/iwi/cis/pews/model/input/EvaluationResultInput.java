package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResultInput extends Input
{
	private List< EvaluationResultObject > results;

	public EvaluationResultInput()
	{
		super();
		this.results = new ArrayList<>();
	}

	public EvaluationResultInput( List< EvaluationResultObject > results )
	{
		super();
		this.results = results;
	}

	public List< EvaluationResultObject > getResults()
	{
		return results;
	}

	public void setResults( List< EvaluationResultObject > results )
	{
		this.results = results;
	}

}
