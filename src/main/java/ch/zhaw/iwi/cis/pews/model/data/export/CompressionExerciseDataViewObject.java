package ch.zhaw.iwi.cis.pews.model.data.export;

public class CompressionExerciseDataViewObject extends ExerciseDataViewObject
{
	private String solution;
	private String description;

	public CompressionExerciseDataViewObject()
	{
	}

	public CompressionExerciseDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String solution,
			String description )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.solution = solution;
		this.description = description;
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
}
