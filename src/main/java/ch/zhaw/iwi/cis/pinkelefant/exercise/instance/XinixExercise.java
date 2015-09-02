package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.instance.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private XinixImageMatrix images;

	public XinixExercise()
	{
		super();
	}

	public XinixExercise(
			String name,
			String description,
			WorkflowElementTemplate derivedFrom,
			Integer orderInWorkshop,
			WorkshopImpl workshop,
			Set< PrincipalImpl > participants,
			String question,
			XinixImageMatrix images )
	{
		super( name, description, derivedFrom, orderInWorkshop, workshop, participants, question );
		this.images = images;
	}

	public XinixImageMatrix getImages()
	{
		return images;
	}

	public void setImages( XinixImageMatrix images )
	{
		this.images = images;
	}

}
