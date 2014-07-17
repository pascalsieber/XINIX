package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public interface WorkshopService extends WorkflowElementService
{

	public void joinSession( int userID, int sessionID );

	public void leaveSession( int userID, int sessionID );

	public ExerciseImpl getCurrentExercise( int sessionID );
	
	public ExerciseImpl getNextExercise( int sessionID );
	
	public ExerciseImpl getPreviousExercise( int sessionID );

	public void setNextExercise( int sessionID );

}
