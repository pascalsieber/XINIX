package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class You2MeDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection( fetch = FetchType.EAGER )
	private List< String > questions;

	public You2MeDefinition()
	{
		super();
		this.questions = new ArrayList<>();
	}

	public You2MeDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, List< String > questions )
	{
		super( owner, timeUnit, duration, workshopDefinition );
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
