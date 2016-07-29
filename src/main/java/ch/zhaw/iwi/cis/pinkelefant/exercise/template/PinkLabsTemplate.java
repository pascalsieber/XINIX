package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkLabsTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	public PinkLabsTemplate()
	{
		super();
	}

	public PinkLabsTemplate(
			PrincipalImpl owner,
			boolean timed,
			TimeUnit timeUnit,
			int duration,
			boolean sharing,
			boolean skippable,
			boolean countable,
			int cardinality,
			WorkshopTemplate workshopTemplate,
			String questionTemplate,
			String defaultName,
			String defaultDescription )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate, defaultName, defaultDescription );
	}

}
