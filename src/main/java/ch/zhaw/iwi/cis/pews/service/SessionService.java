package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedExecutionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedSetCurrentExerciseRequest;

public interface SessionService extends WorkflowElementService
{

	public ExerciseImpl getCurrentExercise( String sessionID );

	public ExerciseImpl getNextExercise( String sessionID );

	public ExerciseImpl getPreviousExercise( String sessionID );

	public String setNextExercise( String sessionID );

	public void join( Invitation invitation );

	public void leave( Invitation invitation );

	public void addExecuter( Invitation invitation );

	public void removeExecuter( Invitation invitation );

	public void setCurrentExercise( SessionImpl request );

	public String setNextExerciseWithDelay( DelayedExecutionRequest offsetRequest );

	public void setCurrentExerciseWithDelay( DelayedSetCurrentExerciseRequest request );

	public SessionImpl findSessionByID( String id );

	public List< SessionImpl > findAllSessions();

}
