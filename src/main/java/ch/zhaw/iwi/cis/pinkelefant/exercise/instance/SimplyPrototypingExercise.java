package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SimplyPrototypingExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String mimeType;

	public SimplyPrototypingExercise()
	{
		super();
	}

	public SimplyPrototypingExercise(
			String name,
			String description,
			WorkflowElementTemplate derivedFrom,
			Integer orderInWorkshop,
			WorkshopImpl workshop,
			Set< PrincipalImpl > participants,
			String question,
			String mimeType )
	{
		super( name, description, derivedFrom, orderInWorkshop, workshop, participants, question );
		this.mimeType = mimeType;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
	}

}
