package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.PinkLabsTemplate;

@Entity
public class PinkLabsExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public PinkLabsExercise()
	{
		super();
	}

	public PinkLabsExercise( String name, String description, PinkLabsTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
	}

}
