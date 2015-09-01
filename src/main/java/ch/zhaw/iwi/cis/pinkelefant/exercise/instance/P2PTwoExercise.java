package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

@Entity
public class P2PTwoExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	public P2PTwoExercise()
	{
		super();
	}

	public P2PTwoExercise( String name, String description, WorkflowElementDefinitionImpl derivedFrom, WorkshopImpl workshop, String question )
	{
		super( name, description, derivedFrom, workshop );
		this.question = question;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

}
