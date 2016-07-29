package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class You2MeInput extends Input
{
	private List< String > questions;

	public You2MeInput()
	{
		super();
		this.questions = new ArrayList< String >();
	}

	public You2MeInput( ExerciseImpl exercise, List< String > questions )
	{
		super( exercise );
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
