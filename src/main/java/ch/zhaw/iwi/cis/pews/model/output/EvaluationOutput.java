package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class EvaluationOutput extends Output
{
	private List< EvaluationOutputElement > evaluations;

	public EvaluationOutput()
	{
		super();
		this.evaluations = new ArrayList<>();
	}

	public EvaluationOutput( List< EvaluationOutputElement > evaluations )
	{
		super();
		this.evaluations = evaluations;
	}

	public List< EvaluationOutputElement > getEvaluations()
	{
		return evaluations;
	}

	public void setEvaluations( List< EvaluationOutputElement > evaluations )
	{
		this.evaluations = evaluations;
	}

}
