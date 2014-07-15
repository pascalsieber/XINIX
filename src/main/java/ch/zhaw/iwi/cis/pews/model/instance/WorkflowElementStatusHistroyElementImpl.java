package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

@Entity
public class WorkflowElementStatusHistroyElementImpl extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private Date date;
	private WorkflowElementStatusImpl status;

	public WorkflowElementStatusHistroyElementImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkflowElementStatusHistroyElementImpl( Date date, WorkflowElementStatusImpl status )
	{
		super();
		this.date = date;
		this.status = status;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}

	public WorkflowElementStatusImpl getStatus()
	{
		return status;
	}

	public void setStatus( WorkflowElementStatusImpl status )
	{
		this.status = status;
	}

}
