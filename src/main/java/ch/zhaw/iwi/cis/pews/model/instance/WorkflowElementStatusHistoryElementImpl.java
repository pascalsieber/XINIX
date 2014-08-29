package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@Entity
public class WorkflowElementStatusHistoryElementImpl extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private Date date;

	@Enumerated( EnumType.STRING )
	private WorkflowElementStatusImpl status;

	public WorkflowElementStatusHistoryElementImpl()
	{
		super();
	}

	public WorkflowElementStatusHistoryElementImpl( Date date, WorkflowElementStatusImpl status )
	{
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
