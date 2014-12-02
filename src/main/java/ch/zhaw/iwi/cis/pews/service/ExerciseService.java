package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.wrappers.OutputRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;

public interface ExerciseService extends WorkflowElementService
{
	public void suspend( SuspensionRequest suspensionRequest );

	public double resume( String exerciseID );

	public Input getInput();

	public void setOutput( String output );

	public void startUser();

	public void stopUser();

	public void resetUser();

	public void suspendUser( TimerRequest request );

	public TimerRequest resumeUser();

	public void cancelUser();

	public Participant findUserParticipant();

	// only used for testing
	public String getInputAsString();

	public Input getInputByExerciseID( String exerciseID );

	// only used for testing
	public String getInputByExerciseIDAsString( String exerciseID );

	public void setOuputByExerciseID( OutputRequest outputRequest );

	public void setOuputStringByExerciseID( String outputRequest );
}
