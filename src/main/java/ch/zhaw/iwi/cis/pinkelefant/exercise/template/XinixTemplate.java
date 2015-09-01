package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	@ManyToOne
	private XinixImageMatrixTemplate images;

	public XinixTemplate()
	{
		super();
	}

	public XinixTemplate(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopTemplate workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			String question,
			XinixImageMatrixTemplate images )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.question = question;
		this.images = images;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public XinixImageMatrixTemplate getImages()
	{
		return images;
	}

	public void setImages( XinixImageMatrixTemplate images )
	{
		this.images = images;
	}

}
