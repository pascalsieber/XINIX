package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class You2MeDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;
	private String counterQuestion;

	public You2MeDefinition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public You2MeDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, String question, String counterQuestion )
	{
		super( owner, timeUnit, duration, workshopDefinition );
		this.question = question;
		this.counterQuestion = counterQuestion;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public String getCounterQuestion()
	{
		return counterQuestion;
	}

	public void setCounterQuestion( String counterQuestion )
	{
		this.counterQuestion = counterQuestion;
	}

}
