package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class EvaluationTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	private int numberOfVotes;

	public EvaluationTemplate()
	{
		super();
	}

	public EvaluationTemplate(
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
			int numberOfVotes )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate );
		this.numberOfVotes = numberOfVotes;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public int getNumberOfVotes()
	{
		return numberOfVotes;
	}

	public void setNumberOfVotes( int numberOfVotes )
	{
		this.numberOfVotes = numberOfVotes;
	}
}
