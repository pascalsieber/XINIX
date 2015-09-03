package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationTemplate;

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

	public EvaluationExercise( String name, String description, EvaluationTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.numberOfVotes = derivedFrom.getNumberOfVotes();
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
