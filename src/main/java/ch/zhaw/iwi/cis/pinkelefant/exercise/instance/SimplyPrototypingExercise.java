package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;

@Entity
public class SimplyPrototypingExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;
	private String mimeType;

	public SimplyPrototypingExercise()
	{
		super();
	}

	public SimplyPrototypingExercise( String name, String description, WorkflowElementTemplate derivedFrom, WorkshopImpl workshop, String question, String mimeType )
	{
		super( name, description, derivedFrom, workshop );
		this.question = question;
		this.mimeType = mimeType;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
	}

}
