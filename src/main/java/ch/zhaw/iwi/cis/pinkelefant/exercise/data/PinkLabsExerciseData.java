package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkLabsExerciseData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String answer;

	public PinkLabsExerciseData()
	{
		super();
	}

	public PinkLabsExerciseData( PrincipalImpl owner, WorkflowElementImpl workflowElement, String answer )
	{
		super( owner, workflowElement );
		this.answer = answer;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer( String answer )
	{
		this.answer = answer;
	}

}
