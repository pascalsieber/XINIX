package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class EvaluationExerciseData extends ExerciseDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="EVALUATIONEXERCISEDATA_EVALUATION")
	private Evaluation evaluation;

	public EvaluationExerciseData()
	{
		super();
	}

	public EvaluationExerciseData( PrincipalImpl owner, WorkflowElementImpl workflowElement, Evaluation evaluation )
	{
		super( owner, workflowElement );
		this.evaluation = evaluation;
	}

	public Evaluation getEvaluation()
	{
		return evaluation;
	}

	public void setEvaluation( Evaluation evaluation )
	{
		this.evaluation = evaluation;
	}

}
