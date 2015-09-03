package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.XinixTemplate;

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

	public XinixExercise( String name, String description, XinixTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.images = derivedFrom.getImages();
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
