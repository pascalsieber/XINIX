package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.P2POneTemplate;

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

	public P2POneExercise( String name, String description, P2POneTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.picture = derivedFrom.getPicture();
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
