package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class EvaluationResultDefinition extends ExerciseDefinitionImpl
{

	@Transient
	private static final long serialVersionUID = 1L;

	public EvaluationResultDefinition()
	{
		super();
	}

	public EvaluationResultDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, boolean timed, boolean sharing, boolean skippable )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
	}

}
