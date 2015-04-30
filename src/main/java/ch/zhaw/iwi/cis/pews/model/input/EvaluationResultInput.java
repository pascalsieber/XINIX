package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;

public class EvaluationResultInput extends Input
{
	private List< EvaluationResultObject > results;
	private List< CompressionExerciseDataElement > notEvaluated;

	public EvaluationResultInput()
	{
		super();
		this.results = new ArrayList<>();
		this.notEvaluated = new ArrayList<>();
	}

	public EvaluationResultInput( List< EvaluationResultObject > results, List< CompressionExerciseDataElement > notEvaluated )
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

	public List< CompressionExerciseDataElement > getNotEvaluated()
	{
		return notEvaluated;
	}

	public void setNotEvaluated( List< CompressionExerciseDataElement > notEvaluated )
	{
		this.notEvaluated = notEvaluated;
	}

}
