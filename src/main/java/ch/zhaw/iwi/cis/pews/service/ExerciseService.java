package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.output.Output;
import ch.zhaw.iwi.cis.pews.model.wrappers.SuspensionRequest;

public interface ExerciseService extends WorkflowElementService
{
	public void suspend( SuspensionRequest suspensionRequest );

	public double resume( String exerciseID );

	public Input getInput();

	public void setOutput( Output output );
}
