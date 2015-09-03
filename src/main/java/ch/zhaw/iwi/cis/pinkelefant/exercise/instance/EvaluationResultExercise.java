package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.EvaluationResultTemplate;

@Entity
public class EvaluationResultExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public EvaluationResultExercise()
	{
		super();
	}

	public EvaluationResultExercise( String name, String description, EvaluationResultTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
	}

}
