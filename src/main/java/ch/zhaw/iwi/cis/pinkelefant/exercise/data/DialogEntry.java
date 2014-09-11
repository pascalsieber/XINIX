package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;

@Entity
public class DialogEntry extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private DialogRole role;
	private String text;

	@ManyToOne
	private You2MeExerciseData you2MeExerciseDataObject;

	public DialogEntry()
	{
		super();
	}

	public DialogEntry( DialogRole role, String text, You2MeExerciseData you2MeExerciseDataObject )
	{
		super();
		this.role = role;
		this.text = text;
		this.you2MeExerciseDataObject = you2MeExerciseDataObject;
	}

	public DialogRole getRole()
	{
		return role;
	}

	public void setRole( DialogRole role )
	{
		this.role = role;
	}

	public String getText()
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}

	public You2MeExerciseData getYou2MeExerciseDataObject()
	{
		return you2MeExerciseDataObject;
	}

	public void setYou2MeExerciseDataObject( You2MeExerciseData you2MeExerciseDataObject )
	{
		this.you2MeExerciseDataObject = you2MeExerciseDataObject;
	}

}
