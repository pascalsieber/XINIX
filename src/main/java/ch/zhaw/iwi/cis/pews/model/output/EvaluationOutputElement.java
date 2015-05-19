package ch.zhaw.iwi.cis.pews.model.output;

public class EvaluationOutputElement
{
	private String solution;
	private String description;
	private int score;

	public EvaluationOutputElement()
	{
		super();
	}

	public EvaluationOutputElement( String solution, String description, int score )
	{
		super();
		this.solution = solution;
		this.description = description;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore( int score )
	{
		this.score = score;
	}

}
