package ch.zhaw.iwi.cis.pews.model.input;

import java.util.List;

public class You2MeInput extends Input
{
	private List< String > questions;

	public You2MeInput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public You2MeInput( List< String > questions )
	{
		super();
		this.questions = questions;
	}

	public List< String > getQuestions()
	{
		return questions;
	}

	public void setQuestions( List< String > questions )
	{
		this.questions = questions;
	}

}
