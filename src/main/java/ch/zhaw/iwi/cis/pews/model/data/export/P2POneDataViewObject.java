package ch.zhaw.iwi.cis.pews.model.data.export;

public class P2POneDataViewObject extends ExerciseDataViewObject
{
	private String keywordID;
	private String keyword;

	public P2POneDataViewObject()
	{
	}

	public P2POneDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String keywordID,
			String keyword )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.keywordID = keywordID;
		this.keyword = keyword;
	}

	public String getKeywordID()
	{
		return keywordID;
	}

	public void setKeywordID( String keywordID )
	{
		this.keywordID = keywordID;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}

}
