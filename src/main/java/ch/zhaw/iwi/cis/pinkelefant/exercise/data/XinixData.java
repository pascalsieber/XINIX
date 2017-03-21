package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set< String > associations;

	@ManyToOne
	private MediaObject xinixImage;

	public XinixData()
	{
		this.associations = new HashSet<>();
	}

	public XinixData( PrincipalImpl owner, WorkflowElementImpl workflowElement, Set< String > associations, MediaObject xinixImage )
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

	public MediaObject getXinixImage()
	{
		return xinixImage;
	}

	public void setXinixImage( MediaObject xinixImage )
	{
		this.xinixImage = xinixImage;
	}

}
