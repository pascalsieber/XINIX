package ch.zhaw.iwi.cis.pews.model.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkflowElementDataImpl extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	// using this for ordering of insertion
	@JsonIgnore
	private Date timestamp;

	@ManyToOne
	private WorkflowElementImpl workflowElement;

	public WorkflowElementDataImpl()
	{
		super();
		this.timestamp = new Date();
	}

	public WorkflowElementDataImpl( PrincipalImpl owner, WorkflowElementImpl workflowElement )
	{
		super( owner );
		this.workflowElement = workflowElement;
		this.timestamp = new Date();
	}

	public WorkflowElementImpl getWorkflowElement()
	{
		return workflowElement;
	}

	public void setWorkflowElement( WorkflowElementImpl workflowElement )
	{
		this.workflowElement = workflowElement;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp( Date timestamp )
	{
		this.timestamp = timestamp;
	}

}
