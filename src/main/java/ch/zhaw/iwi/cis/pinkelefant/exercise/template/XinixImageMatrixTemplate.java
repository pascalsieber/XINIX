package ch.zhaw.iwi.cis.pinkelefant.exercise.template;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;
import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;

@Entity
public class XinixImageMatrixTemplate extends ExerciseTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToMany
	private List< XinixImage > xinixImages;

	public XinixImageMatrixTemplate()
	{
		super();
		this.xinixImages = new ArrayList<>();
	}

	public XinixImageMatrixTemplate(
			PrincipalImpl owner,
			TimeUnit timeUnit,
			int duration,
			WorkshopTemplate workshopDefinition,
			boolean timed,
			boolean sharing,
			boolean skippable,
			List< XinixImage > xinixImages )
	{
		super( owner, timeUnit, duration, workshopDefinition, timed, sharing, skippable );
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
