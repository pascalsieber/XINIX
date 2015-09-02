package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String picture;

	public P2POneExercise()
	{
		super();
	}

	public P2POneExercise(
			String name,
			String description,
			WorkflowElementTemplate derivedFrom,
			Integer orderInWorkshop,
			WorkshopImpl workshop,
			Set< PrincipalImpl > participants,
			String question,
			String picture )
	{
		super( name, description, derivedFrom, orderInWorkshop, workshop, participants, question );
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
