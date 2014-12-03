package ch.zhaw.iwi.cis.pews.model.output;

import java.util.ArrayList;
import java.util.List;

public class PinkLabsOutput extends Output
{
	private String exerciseID;
	private List< String > answers;

	public PinkLabsOutput()
	{
		super();
		this.answers = new ArrayList<>();
	}

	public PinkLabsOutput( String exerciseID, List< String > answers )
	{
		super();
		this.exerciseID = exerciseID;
		this.answers = answers;
	}

	public String getExerciseID()
	{
		return exerciseID;
	}

	public void setExerciseID( String exerciseID )
	{
		this.exerciseID = exerciseID;
	}

	public List< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( List< String > answers )
	{
		this.answers = answers;
	}
}
