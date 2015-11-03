package ch.zhaw.iwi.cis.pews.model.data.export;

public class You2MeExerciseDataViewObject extends ExerciseDataViewObject
{
	private String dialogEntryRole;
	private String dialogEntryText;

	public You2MeExerciseDataViewObject()
	{
	}

	public You2MeExerciseDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			String dialogEntryRole,
			String dialogEntryText )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.dialogEntryRole = dialogEntryRole;
		this.dialogEntryText = dialogEntryText;
	}

	public String getDialogEntryRole()
	{
		return dialogEntryRole;
	}

	public void setDialogEntryRole( String dialogEntryRole )
	{
		this.dialogEntryRole = dialogEntryRole;
	}

	public String getDialogEntryText()
	{
		return dialogEntryText;
	}

	public void setDialogEntryText( String dialogEntryText )
	{
		this.dialogEntryText = dialogEntryText;
	}

}
