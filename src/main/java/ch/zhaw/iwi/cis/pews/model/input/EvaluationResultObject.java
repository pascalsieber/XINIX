package ch.zhaw.iwi.cis.pews.model.input;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;

public class EvaluationResultObject
{
	private CompressionExerciseDataElement solution;
	private double averageScore;
	private int numberOfVotes;

	public EvaluationResultObject()
	{
		super();
	}

	public EvaluationResultObject( CompressionExerciseDataElement solution, double averageScore, int numberOfVotes )
	{
		super();
		this.solution = solution;
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
	}

	public CompressionExerciseDataElement getSolution()
	{
		return solution;
	}

	public void setSolution( CompressionExerciseDataElement solution )
	{
		this.solution = solution;
	}

	public double getAverageScore()
	{
		return averageScore;
	}

	public void setAverageScore( double averageScore )
	{
		this.averageScore = averageScore;
	}

	public int getNumberOfVotes()
	{
		return numberOfVotes;
	}

	public void setNumberOfVotes( int numberOfVotes )
	{
		this.numberOfVotes = numberOfVotes;
	}

}
