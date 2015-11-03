package ch.zhaw.iwi.cis.pews.model.data.export;

public class PinkLabsExerciseDataViewObject extends ExerciseDataViewObject
{
	private String answer;

	public PinkLabsExerciseDataViewObject()
	{
	}

	public PinkLabsExerciseDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String answer )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.answer = answer;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer( String answer )
	{
		this.answer = answer;
	}

}
