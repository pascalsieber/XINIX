package ch.zhaw.iwi.cis.pews.model.data.export;

public class SimplePrototypingDataViewObject extends ExerciseDataViewObject
{
	private String pictureURL;

	public SimplePrototypingDataViewObject()
	{
	}

	public SimplePrototypingDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String pictureURL )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.pictureURL = pictureURL;
	}

	public String getPictureURL()
	{
		return pictureURL;
	}

	public void setPictureURL( String pictureURL )
	{
		this.pictureURL = pictureURL;
	}

}
