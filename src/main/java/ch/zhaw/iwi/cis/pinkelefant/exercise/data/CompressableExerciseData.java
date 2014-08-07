package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@MappedSuperclass
public class CompressableExerciseData extends ExerciseDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public CompressableExerciseData()
	{
		super();
	}

	public CompressableExerciseData( Client client, PrincipalImpl owner, WorkflowElementImpl workflowElement )
	{
		super( client, owner, workflowElement );
	}

}
