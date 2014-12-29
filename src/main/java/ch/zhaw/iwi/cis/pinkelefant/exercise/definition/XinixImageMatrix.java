package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;

@Entity
public class XinixImageMatrix extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToMany
	private List< XinixImage > xinixImages;

	public XinixImageMatrix()
	{
		super();
		this.xinixImages = new ArrayList<>();
	}

	public XinixImageMatrix( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, List< XinixImage > xinixImages )
	{
		super( owner, timeUnit, duration, workshopDefinition );
		this.xinixImages = xinixImages;
	}

	public List< XinixImage > getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( List< XinixImage > xinixImages )
	{
		this.xinixImages = xinixImages;
	}

}
