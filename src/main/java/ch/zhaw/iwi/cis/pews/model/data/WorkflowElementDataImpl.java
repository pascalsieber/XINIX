package ch.zhaw.iwi.cis.pews.model.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkflowElementDataImpl extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private WorkflowElementImpl workflowElement;

	public WorkflowElementDataImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkflowElementDataImpl( PrincipalImpl owner, WorkflowElementImpl workflowElement )
	{
		super( owner );
		this.workflowElement = workflowElement;
	}

	public WorkflowElementImpl getWorkflowElement()
	{
		return workflowElement;
	}

	public void setWorkflowElement( WorkflowElementImpl workflowElement )
	{
		this.workflowElement = workflowElement;
	}

}
