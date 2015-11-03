package ch.zhaw.iwi.cis.pews.model.data.export;

public class P2PTwoDataViewObject extends ExerciseDataViewObject
{
	private String answer;
	private String cascade1KeywordID;
	private String cascade1Keyword;

	public P2PTwoDataViewObject()
	{
	}

	public P2PTwoDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String answer,
			String cascade1KeywordID,
			String cascade1Keyword )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.answer = answer;
		this.cascade1KeywordID = cascade1KeywordID;
		this.cascade1Keyword = cascade1Keyword;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer( String answer )
	{
		this.answer = answer;
	}

	public String getCascade1KeywordID()
	{
		return cascade1KeywordID;
	}

	public void setCascade1KeywordID( String cascade1KeywordID )
	{
		this.cascade1KeywordID = cascade1KeywordID;
	}

	public String getCascade1Keyword()
	{
		return cascade1Keyword;
	}

	public void setCascade1Keyword( String cascade1Keyword )
	{
		this.cascade1Keyword = cascade1Keyword;
	}

}
