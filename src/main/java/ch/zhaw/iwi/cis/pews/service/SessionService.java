package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;

public interface SessionService extends WorkflowElementService
{

	public ExerciseImpl getCurrentExercise( int sessionID );

	public ExerciseImpl getNextExercise( int sessionID );

	public ExerciseImpl getPreviousExercise( int sessionID );

	public void setNextExercise( int sessionID );

	public void join( Invitation invitation );

	public void leave( Invitation invitation );

	public void addExecuter( Invitation invitation );

	public void removeExecuter( Invitation invitation );

}
