package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;

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

	public SimplyPrototypingExercise( String name, String description, SimplyPrototypingTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.mimeType = derivedFrom.getMimeType();
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
