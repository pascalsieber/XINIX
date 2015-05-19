package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResultInput extends Input
{
	private List< EvaluationResultObject > results;
	private List< CompressionInputElement > notEvaluated;

	public EvaluationResultInput()
	{
		super();
		this.results = new ArrayList<>();
		this.notEvaluated = new ArrayList<>();
	}

	public EvaluationResultInput( List< EvaluationResultObject > results, List< CompressionInputElement > notEvaluated )
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

	public List< CompressionInputElement > getNotEvaluated()
	{
		return notEvaluated;
	}

	public void setNotEvaluated( List< CompressionInputElement > notEvaluated )
	{
		this.notEvaluated = notEvaluated;
	}

}
