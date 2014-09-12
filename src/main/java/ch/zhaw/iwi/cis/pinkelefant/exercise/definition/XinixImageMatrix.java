package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.HashSet;
import java.util.Set;
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
	private Set< XinixImage > xinixImages;

	public XinixImageMatrix()
	{
		super();
		this.xinixImages = new HashSet<>();
	}

	public XinixImageMatrix( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, Set< XinixImage > xinixImages )
	{
		super( owner, timeUnit, duration, workshopDefinition );
		this.xinixImages = xinixImages;
	}

	public Set< XinixImage > getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( Set< XinixImage > xinixImages )
	{
		this.xinixImages = xinixImages;
	}

}
