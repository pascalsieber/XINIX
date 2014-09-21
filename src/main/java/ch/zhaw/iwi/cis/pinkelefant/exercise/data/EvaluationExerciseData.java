package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class EvaluationExerciseData extends ExerciseDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToMany
	private List< Evaluation > evaluations;

	public EvaluationExerciseData()
	{
		super();
		this.evaluations = new ArrayList<>();
	}

	public EvaluationExerciseData( PrincipalImpl owner, WorkflowElementImpl workflowElement, List< Evaluation > evaluations )
	{
		super( owner, workflowElement );
		this.evaluations = evaluations;
	}

	public List< Evaluation > getEvaluations()
	{
		return evaluations;
	}

	public void setEvaluations( List< Evaluation > evaluations )
	{
		this.evaluations = evaluations;
	}

}
