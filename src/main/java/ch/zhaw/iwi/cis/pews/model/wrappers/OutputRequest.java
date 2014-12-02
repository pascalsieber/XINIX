package ch.zhaw.iwi.cis.pews.model.wrappers;

public class OutputRequest
{
	private String exerciseID;
	private String output;

	public OutputRequest()
	{
		super();
	}

	public OutputRequest( String exerciseID, String output )
	{
		super();
		this.exerciseID = exerciseID;
		this.output = output;
	}

	public String getExerciseID()
	{
		return exerciseID;
	}

	public void setExerciseID( String exerciseID )
	{
		this.exerciseID = exerciseID;
	}

	public String getOutput()
	{
		return output;
	}

	public void setOutput( String output )
	{
		this.output = output;
	}

}
