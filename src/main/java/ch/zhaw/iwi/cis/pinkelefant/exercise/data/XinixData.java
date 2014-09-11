package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection
	private Set< String > associations;

	@ManyToOne
	private XinixImage xinixImage;

	public XinixData()
	{
		super();
		this.associations = new HashSet<>();
	}

	public XinixData( PrincipalImpl owner, WorkflowElementImpl workflowElement, Set< String > associations, XinixImage xinixImage )
	{
		super( owner, workflowElement );
		this.associations = associations;
		this.xinixImage = xinixImage;
	}

	public Set< String > getAssociations()
	{
		return associations;
	}

	public void setAssociations( Set< String > associations )
	{
		this.associations = associations;
	}

	public XinixImage getXinixImage()
	{
		return xinixImage;
	}

	public void setXinixImage( XinixImage xinixImage )
	{
		this.xinixImage = xinixImage;
	}

}
