package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

public class EvaluationInput extends Input
{
	private List< CompressionInputElement > solutions;
	private String question;
	private int numberOfVotes;

	public EvaluationInput()
	{
		super();
		this.solutions = new ArrayList<>();
	}

	public EvaluationInput( List< CompressionInputElement > solutions, String question, int numberOfVotes )
	{
		super();
		this.solutions = solutions;
		this.question = question;
		this.numberOfVotes = numberOfVotes;
	}

	public List< CompressionInputElement > getSolutions()
	{
		return solutions;
	}

	public void setSolutions( List< CompressionInputElement > solutions )
	{
		this.solutions = solutions;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
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
