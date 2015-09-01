package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

@Entity
public class You2MeExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection( fetch = FetchType.EAGER )
	private List< String > questions;

	public You2MeExercise()
	{
		super();
	}

	public You2MeExercise( String name, String description, WorkflowElementDefinitionImpl derivedFrom, WorkshopImpl workshop, List< String > questions )
	{
		super( name, description, derivedFrom, workshop );
		this.questions = questions;
	}

	public List< String > getQuestions()
	{
		return questions;
	}

	public void setQuestions( List< String > questions )
	{
		this.questions = questions;
	}

}
