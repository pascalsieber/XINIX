package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2PTwoDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	public P2PTwoDefinition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public P2PTwoDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration, String question )
	{
		super( owner, timeUnit, duration );
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
