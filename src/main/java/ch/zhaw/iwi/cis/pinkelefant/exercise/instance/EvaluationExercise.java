package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

@Entity
public class EvaluationExercise extends ExerciseImpl
{

	@Transient
	private static final long serialVersionUID = 1L;
	private String question;
	private int numberOfVotes;

	public EvaluationExercise()
	{
		super();
	}

	public EvaluationExercise( String name, String description, WorkflowElementDefinitionImpl derivedFrom, WorkshopImpl workshop, String question, int numberOfVotes )
	{
		super( name, description, derivedFrom, workshop );
		this.question = question;
		this.numberOfVotes = numberOfVotes;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
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
