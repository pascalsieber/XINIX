package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;

@Entity
public class PosterExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	@Column( length = 2000 )
	private String title;
	@Column( length = 20000 )
	private String description;

	public PosterExercise()
	{
		super();
	}

	public PosterExercise( String name, String description, WorkflowElementTemplate derivedFrom, WorkshopImpl workshop, String title, String description2 )
	{
		super( name, description, derivedFrom, workshop );
		this.title = title;
		description = description2;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

}
