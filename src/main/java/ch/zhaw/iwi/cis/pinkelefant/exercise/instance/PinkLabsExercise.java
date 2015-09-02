package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkLabsExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public PinkLabsExercise()
	{
		super();
	}

	public PinkLabsExercise( String name, String description, WorkflowElementTemplate derivedFrom, Integer orderInWorkshop, WorkshopImpl workshop, Set< PrincipalImpl > participants, String question )
	{
		super( name, description, derivedFrom, orderInWorkshop, workshop, participants, question );
	}

}
