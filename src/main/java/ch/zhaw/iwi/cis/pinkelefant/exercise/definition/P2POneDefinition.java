package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String picture;
	private String question;

	public P2POneDefinition()
	{
		super();
	}

	public P2POneDefinition(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopDefinitionImpl workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			String picture,
			String question )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
		this.picture = picture;
		this.question = question;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture( String picture )
	{
		this.picture = picture;
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
