package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

@Entity
public class WorkflowElementStatusHistoryElementImpl extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private Date date;

	@Enumerated( EnumType.STRING )
	private WorkflowElementStatusImpl status;

	public WorkflowElementStatusHistoryElementImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkflowElementStatusHistoryElementImpl( Date date, WorkflowElementStatusImpl status )
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
