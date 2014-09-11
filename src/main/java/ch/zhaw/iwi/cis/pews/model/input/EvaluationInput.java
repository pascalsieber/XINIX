package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

public class EvaluationInput extends Input
{
	private List< String > solutions;
	private String question;

	public EvaluationInput()
	{
		super();
		this.solutions = new ArrayList<>();
	}

	public EvaluationInput( List< String > solutions, String question )
	{
		super();
		this.solutions = solutions;
		this.question = question;
	}

	public List< String > getSolutions()
	{
		return solutions;
	}

	public void setSolutions( List< String > solutions )
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
}
