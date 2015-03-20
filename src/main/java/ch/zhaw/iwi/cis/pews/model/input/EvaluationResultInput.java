package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResultInput extends Input
{
	private List< EvaluationResultObject > results;
	private List< String > notEvaluated;

	public EvaluationResultInput()
	{
		super();
		this.results = new ArrayList<>();
		this.notEvaluated = new ArrayList<>();
	}

	public EvaluationResultInput( List< EvaluationResultObject > results, List< String > notEvaluated )
	{
		super();
		this.results = results;
		this.notEvaluated = notEvaluated;
	}

	public List< EvaluationResultObject > getResults()
	{
		return results;
	}

	public void setResults( List< EvaluationResultObject > results )
	{
		this.results = results;
	}

	public List< String > getNotEvaluated()
	{
		return notEvaluated;
	}

	public void setNotEvaluated( List< String > notEvaluated )
	{
		this.notEvaluated = notEvaluated;
	}

}
