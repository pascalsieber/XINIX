package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2PTwoTemplate;

@Entity
public class P2PTwoExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public P2PTwoExercise()
	{
		super();
	}

	public P2PTwoExercise( String name, String description, P2PTwoTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
	}

}
