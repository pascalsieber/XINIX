package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class You2MeTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection( fetch = FetchType.EAGER )
	private List< String > questions;

	public You2MeTemplate()
	{
		super();
		this.questions = new ArrayList<>();
	}

	public You2MeTemplate(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopTemplate workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			List< String > questions )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.questions = questions;
	}

	public List< String > getQuestions()
	{
		return questions;
	}

	public void setQuestions( List< String > questions )
	{
		this.questions = questions;
	}

}
