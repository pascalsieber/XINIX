package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.You2MeTemplate;

@Entity
public class You2MeExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection( fetch = FetchType.EAGER )
	private Set< String > questions;

	public You2MeExercise()
	{
		super();
		this.questions = new HashSet< String >();
	}

	public You2MeExercise( String name, String description, You2MeTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.questions = derivedFrom.getQuestions();
	}

	public Set< String > getQuestions()
	{
		return questions;
	}

	public void setQuestions( Set< String > questions )
	{
		this.questions = questions;
	}

}
