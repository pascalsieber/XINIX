package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixImage extends ExerciseDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String url;

	public XinixImage()
	{
		super();
	}

	public XinixImage( Client client, PrincipalImpl owner, WorkflowElementImpl workflowElement, String url )
	{
		super( client, owner, workflowElement );
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

}
