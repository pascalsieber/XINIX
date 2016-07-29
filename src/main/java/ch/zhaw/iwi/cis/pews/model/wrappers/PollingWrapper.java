package ch.zhaw.iwi.cis.pews.model.wrappers;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;

public class PollingWrapper
{
	private String currentExerciseID;
	private List< ExerciseDataImpl > output;

	public PollingWrapper()
	{
	}

	public PollingWrapper( String currentExerciseID, List< ExerciseDataImpl > output )
	{
		super();
		this.currentExerciseID = currentExerciseID;
		this.output = output;
	}

	public String getCurrentExerciseID()
	{
		return currentExerciseID;
	}

	public void setCurrentExerciseID( String currentExerciseID )
	{
		this.currentExerciseID = currentExerciseID;
	}

	public List< ExerciseDataImpl > getOutput()
	{
		return output;
	}

	public void setOutput( List< ExerciseDataImpl > output )
	{
		this.output = output;
	}
}
