package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedExecutionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.DelayedSetCurrentExerciseRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.PollingWrapper;

public interface SessionService extends WorkflowElementService
{

	public ExerciseImpl getPreviousExercise( String sessionID );

	public String setNextExercise( String sessionID );

	public void join( Invitation invitation );

	public void leave( Invitation invitation );

	public void setCurrentExercise( SessionImpl request );

	public SessionImpl findSessionByID( String id );

	public List< SessionImpl > findAllSessions();

	public PollingWrapper getCurrentExericseIDWithOutput();

	public String persistSession( SessionImpl obj );

}
