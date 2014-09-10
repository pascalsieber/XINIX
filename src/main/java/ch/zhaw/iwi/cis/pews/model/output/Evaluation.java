package ch.zhaw.iwi.cis.pews.model.output;

public class Evaluation
{
	private String solution;
	private Score score;

	public Evaluation()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Evaluation( String solution, Score score )
	{
		super();
		this.solution = solution;
		this.score = score;
	}

	public String getSolution()
	{
		return solution;
	}

	public void setSolution( String solution )
	{
		this.solution = solution;
	}

	public Score getScore()
	{
		return score;
	}

	public void setScore( Score score )
	{
		this.score = score;
	}

}
