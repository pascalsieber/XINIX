package ch.zhaw.iwi.cis.pews.model.data.export;

public class XinixDataViewObject extends ExerciseDataViewObject
{
	private String association;
	private String xinixImageID;
	private String xinixImageURL;

	public XinixDataViewObject()
	{
	}

	public XinixDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String association,
			String xinixImageID,
			String xinixImageURL )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.association = association;
		this.xinixImageID = xinixImageID;
		this.xinixImageURL = xinixImageURL;
	}

	public String getAssociation()
	{
		return association;
	}

	public void setAssociation( String association )
	{
		this.association = association;
	}

	public String getXinixImageID()
	{
		return xinixImageID;
	}

	public void setXinixImageID( String xinixImageID )
	{
		this.xinixImageID = xinixImageID;
	}

	public String getXinixImageURL()
	{
		return xinixImageURL;
	}

	public void setXinixImageURL( String xinixImageURL )
	{
		this.xinixImageURL = xinixImageURL;
	}

}
