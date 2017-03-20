package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;
import ch.zhaw.iwi.cis.pews.model.wrappers.TimerRequest;
import org.jvnet.hk2.annotations.Contract;

@Contract
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

	public void setOuputByExerciseID( String outputRequestString );

	public List< ExerciseDataImpl > getOutput();

	public List< ExerciseDataImpl > getOutputByExerciseID( String exerciseID );

	public List< ExerciseImpl > findAllExercises();

	public ExerciseImpl findExerciseByID( String id );

	public String persistExercise( ExerciseImpl exercise );

	public String generateFromTemplate( ExerciseImpl obj );
}
