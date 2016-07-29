package ch.zhaw.iwi.cis.pews.util.comparator;

import java.util.Comparator;

import ch.zhaw.iwi.cis.pews.model.input.EvaluationResultObject;

public class EvaluationResultComparator implements Comparator< EvaluationResultObject >
{
	@Override
	public int compare( EvaluationResultObject r1, EvaluationResultObject r2 )
	{
		return r1.getAverageScore() < r2.getAverageScore() ? -1 : ( r1.getAverageScore() == r2.getAverageScore() ? 0 : 1 );
	}
}
