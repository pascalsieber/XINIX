package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;

@Entity
public class PinkLabsExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	public PinkLabsExercise()
	{
		super();
	}

	public PinkLabsExercise( String name, String description, WorkflowElementTemplate derivedFrom, WorkshopImpl workshop, String question )
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
