package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;

public interface SessionService extends WorkflowElementService
{

	public ExerciseImpl getCurrentExercise( String sessionID );

	public ExerciseImpl getNextExercise( String sessionID );

	public ExerciseImpl getPreviousExercise( String sessionID );

	public void setNextExercise( String sessionID );

	public void join( Invitation invitation );

	public void leave( Invitation invitation );

	public void addExecuter( Invitation invitation );

	public void removeExecuter( Invitation invitation );

}
