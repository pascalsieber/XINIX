package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;

@Entity
public class XinixTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private XinixImageMatrix images;

	public XinixTemplate()
	{
		super();
	}

	public XinixTemplate(
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
			String defaultDescription,
			XinixImageMatrix images )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate, defaultName, defaultDescription );
		this.images = images;
	}

	public XinixImageMatrix getImages()
	{
		return images;
	}

	public void setImages( XinixImageMatrix images )
	{
		this.images = images;
	}

}
