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
	private String question;

	public PinkLabsTemplate()
	{
		super();
	}

	public PinkLabsTemplate( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopTemplate workshopDefinition, boolean timed, boolean sharing, boolean skippable, String question )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.question = question;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

}
