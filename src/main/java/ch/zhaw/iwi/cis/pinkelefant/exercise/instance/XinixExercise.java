package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixImageMatrixTemplate;

@Entity
public class XinixExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	@ManyToOne
	private XinixImageMatrixTemplate images;

	public XinixExercise()
	{
		super();
	}

	public XinixExercise( String name, String description, WorkflowElementTemplate derivedFrom, WorkshopImpl workshop, String question, XinixImageMatrixTemplate images )
	{
		super( name, description, derivedFrom, workshop );
		this.question = question;
		this.images = images;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public XinixImageMatrixTemplate getImages()
	{
		return images;
	}

	public void setImages( XinixImageMatrixTemplate images )
	{
		this.images = images;
	}

}
