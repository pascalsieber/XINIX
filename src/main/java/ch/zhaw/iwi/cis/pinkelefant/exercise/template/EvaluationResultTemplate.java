package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class EvaluationResultTemplate extends ExerciseTemplate
{

	@Transient
	private static final long serialVersionUID = 1L;

	public EvaluationResultTemplate()
	{
		super();
	}

	public EvaluationResultTemplate( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopTemplate workshopDefinition, boolean timed, boolean sharing, boolean skippable )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
	}

}
