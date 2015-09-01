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
	private String question;
	private int numberOfVotes;

	public EvaluationTemplate()
	{
		super();
	}

	public EvaluationTemplate(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopTemplate workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			String question,
			int numberOfVotes )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.question = question;
		this.numberOfVotes = numberOfVotes;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
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
