package ch.zhaw.iwi.cis.pews.model.input;


public class EvaluationResultObject
{
	private CompressionInputElement solution;
	private double averageScore;
	private int numberOfVotes;

	public EvaluationResultObject()
	{
		super();
	}

	public EvaluationResultObject( CompressionInputElement solution, double averageScore, int numberOfVotes )
	{
		super();
		this.solution = solution;
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
	}

	public CompressionInputElement getSolution()
	{
		return solution;
	}

	public void setSolution( CompressionInputElement solution )
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
