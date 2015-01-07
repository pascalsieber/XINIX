package ch.zhaw.iwi.cis.pews.model.output;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.Evaluation;

public class EvaluationOutput extends Output
{
	private Evaluation evaluation;

	public EvaluationOutput()
	{
		super();
	}

	public EvaluationOutput( Evaluation evaluation )
	{
		super();
		this.evaluation = evaluation;
	}

	public Evaluation getEvaluation()
	{
		return evaluation;
	}

	public void setEvaluation( Evaluation evaluation )
	{
		this.evaluation = evaluation;
	}

}
