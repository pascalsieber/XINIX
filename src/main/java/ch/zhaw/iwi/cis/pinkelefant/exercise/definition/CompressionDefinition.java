package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class CompressionDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	@ElementCollection
	private List< String > solutionCriteria;

	public CompressionDefinition()
	{
		super();
		this.solutionCriteria = new ArrayList<>();
	}

	public CompressionDefinition(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopDefinitionImpl workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			String question,
			List< String > solutionCriteria )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.question = question;
		this.solutionCriteria = solutionCriteria;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
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
