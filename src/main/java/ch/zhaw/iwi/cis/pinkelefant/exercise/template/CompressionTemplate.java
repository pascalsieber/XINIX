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
public class CompressionTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection( fetch = FetchType.EAGER )
	private List< String > solutionCriteria;

	public CompressionTemplate()
	{
		super();
		this.solutionCriteria = new ArrayList<>();
	}

	public CompressionTemplate(
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
			List< String > solutionCriteria )
	{
		super( owner, timed, timeUnit, duration, sharing, skippable, countable, cardinality, workshopTemplate, questionTemplate, defaultName, defaultDescription );
		this.solutionCriteria = solutionCriteria;
	}

	public List< String > getSolutionCriteria()
	{
		return solutionCriteria;
	}

	public void setSolutionCriteria( List< String > solutionCriteria )
	{
		this.solutionCriteria = solutionCriteria;
	}

}
