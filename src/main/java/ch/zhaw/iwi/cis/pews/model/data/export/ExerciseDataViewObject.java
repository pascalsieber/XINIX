package ch.zhaw.iwi.cis.pews.model.data.export;

public class ExerciseDataViewObject
{
	private String id;
	private String workshopID;
	private String workshopName;
	private String exerciseID;
	private String exerciseName;
	private String exerciseQuestion;
	private String ownerID;
	private String ownerLoginName;

	public ExerciseDataViewObject()
	{
	}

	public ExerciseDataViewObject( String id, String workshopID, String workshopName, String exerciseID, String exerciseName, String exerciseQuestion, String ownerID, String ownerLoginName )
	{
		super();
		this.id = id;
		this.workshopID = workshopID;
		this.workshopName = workshopName;
		this.exerciseID = exerciseID;
		this.exerciseName = exerciseName;
		this.exerciseQuestion = exerciseQuestion;
		this.ownerID = ownerID;
		this.ownerLoginName = ownerLoginName;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getWorkshopID()
	{
		return workshopID;
	}

	public void setWorkshopID( String workshopID )
	{
		this.workshopID = workshopID;
	}

	public String getWorkshopName()
	{
		return workshopName;
	}

	public void setWorkshopName( String workshopName )
	{
		this.workshopName = workshopName;
	}

	public String getExerciseID()
	{
		return exerciseID;
	}

	public void setExerciseID( String exerciseID )
	{
		this.exerciseID = exerciseID;
	}

	public String getExerciseName()
	{
		return exerciseName;
	}

	public void setExerciseName( String exerciseName )
	{
		this.exerciseName = exerciseName;
	}

	public String getExerciseQuestion()
	{
		return exerciseQuestion;
	}

	public void setExerciseQuestion( String exerciseQuestion )
	{
		this.exerciseQuestion = exerciseQuestion;
	}

	public String getOwnerID()
	{
		return ownerID;
	}

	public void setOwnerID( String ownerID )
	{
		this.ownerID = ownerID;
	}

	public String getOwnerLoginName()
	{
		return ownerLoginName;
	}

	public void setOwnerLoginName( String ownerLoginName )
	{
		this.ownerLoginName = ownerLoginName;
	}

}
