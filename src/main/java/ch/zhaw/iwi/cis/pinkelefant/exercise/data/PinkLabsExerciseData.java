package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkLabsExerciseData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection
	private List< String > answers;

	public PinkLabsExerciseData()
	{
		super();
		this.answers = new ArrayList<>();
	}

	public PinkLabsExerciseData( PrincipalImpl owner, WorkflowElementImpl workflowElement, List< String > answers )
	{
		super( owner, workflowElement );
		this.answers = answers;
	}

	public List< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( List< String > answers )
	{
		this.answers = answers;
	}

}
