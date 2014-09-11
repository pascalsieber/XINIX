package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixImageMatrix extends WorkflowElementDefinitionImpl
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

	public XinixImageMatrix( PrincipalImpl owner, Set< XinixImage > xinixImages )
	{
		super( owner );
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
