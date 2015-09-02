package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class EvaluationExercise extends ExerciseImpl
{

	@Transient
	private static final long serialVersionUID = 1L;
	private int numberOfVotes;

	public EvaluationExercise()
	{
		super();
	}

	public EvaluationExercise(
			String name,
			String description,
			WorkflowElementTemplate derivedFrom,
			Integer orderInWorkshop,
			WorkshopImpl workshop,
			Set< PrincipalImpl > participants,
			String question,
			int numberOfVotes )
	{
		super( name, description, derivedFrom, orderInWorkshop, workshop, participants, question );
		this.numberOfVotes = numberOfVotes;
	}

	public int getNumberOfVotes()
	{
		return numberOfVotes;
	}

	public void setNumberOfVotes( int numberOfVotes )
	{
		this.numberOfVotes = numberOfVotes;
	}

}
