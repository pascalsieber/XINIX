package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

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
	private String association;

	@ManyToOne
	private XinixImage xinixImage;

	public XinixData()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public XinixData( PrincipalImpl owner, WorkflowElementImpl workflowElement, String association, XinixImage xinixImage )
	{
		super( owner, workflowElement );
		this.association = association;
		this.xinixImage = xinixImage;
	}

	public String getAssociation()
	{
		return association;
	}

	public void setAssociation( String association )
	{
		this.association = association;
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
