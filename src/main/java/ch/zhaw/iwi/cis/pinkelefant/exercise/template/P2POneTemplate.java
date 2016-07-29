package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String picture;

	public P2POneTemplate()
	{
		super();
	}

	public P2POneTemplate(
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
			String picture )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate, defaultName, defaultDescription );
		this.picture = picture;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture( String picture )
	{
		this.picture = picture;
	}

}
