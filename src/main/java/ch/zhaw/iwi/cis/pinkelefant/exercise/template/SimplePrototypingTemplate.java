package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SimplePrototypingTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;
	private String mimeType;

	public SimplePrototypingTemplate()
	{
		super();
	}

	public SimplePrototypingTemplate(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopTemplate workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			String question,
			String mimeType )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.question = question;
		this.mimeType = mimeType;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
