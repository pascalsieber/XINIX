package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;

public class EvaluationOutput extends Output
{
	private List< Evaluation > evaluations;

	public EvaluationOutput()
	{
		super();
	}

	public EvaluationOutput( List< Evaluation > evaluations )
	{
		super();
		this.evaluations = evaluations;
	}

	public List< Evaluation > getEvaluations()
	{
		return evaluations;
	}

	public void setEvaluations( List< Evaluation > evaluations )
	{
		this.evaluations = evaluations;
	}

}
