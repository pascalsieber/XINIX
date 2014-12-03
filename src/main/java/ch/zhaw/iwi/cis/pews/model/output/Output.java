package ch.zhaw.iwi.cis.pews.model.output;

public class Output
{
	private String exerciseID;

	public Output()
	{
		super();
	}

	public Output( String exerciseID )
	{
		super();
		this.exerciseID = exerciseID;
	}

	public String getExerciseID()
	{
		return exerciseID;
	}

	public void setExerciseID( String exerciseID )
	{
		this.exerciseID = exerciseID;
	}

}
