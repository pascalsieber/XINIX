package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.data.WorkflowElementDataImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class WorkflowElementImpl extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private double elapsedSeconds;

	@OrderColumn( name = "idx_status" )
	@OneToMany( cascade = CascadeType.ALL )
	private List< WorkflowElementStatusHistoryElementImpl > statusHistory;

	private String currentState;

	@ManyToOne
	private WorkflowElementDefinitionImpl derivedFrom;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "workflowElement" )
	private List< WorkflowElementDataImpl > data;

	public WorkflowElementImpl()
	{
		super();
		this.statusHistory = new ArrayList< WorkflowElementStatusHistoryElementImpl >();
		this.data = new ArrayList< WorkflowElementDataImpl >();
		this.setCurrentState( WorkflowElementStatusImpl.NEW );
		this.elapsedSeconds = 0;
	}

	public WorkflowElementImpl( String name, String description, WorkflowElementDefinitionImpl derivedFrom )
	{
		this.name = name;
		this.description = description;
		this.statusHistory = new ArrayList< WorkflowElementStatusHistoryElementImpl >();
		this.derivedFrom = derivedFrom;
		this.data = new ArrayList< WorkflowElementDataImpl >();
		this.setCurrentState( WorkflowElementStatusImpl.NEW );
		this.elapsedSeconds = 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public List< WorkflowElementStatusHistoryElementImpl > getStatusHistory()
	{
		return statusHistory;
	}

	public void setStatusHistory( List< WorkflowElementStatusHistoryElementImpl > statusHistory )
	{
		this.statusHistory = statusHistory;
	}

	public String getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState( WorkflowElementStatusImpl currentState )
	{
		statusHistory.add( new WorkflowElementStatusHistoryElementImpl( new Date(), currentState ) );
		this.currentState = currentState.toString();
	}

	public WorkflowElementDefinitionImpl getDerivedFrom()
	{
		return derivedFrom;
	}

	public void setDerivedFrom( WorkflowElementDefinitionImpl derivedFrom )
	{
		this.derivedFrom = derivedFrom;
	}

	public List< WorkflowElementDataImpl > getData()
	{
		return data;
	}

	public void setData( List< WorkflowElementDataImpl > data )
	{
		this.data = data;
	}

	public double getElapsedSeconds()
	{
		return elapsedSeconds;
	}

	public void setElapsedSeconds( double ellapsedSeconds )
	{
		this.elapsedSeconds = ellapsedSeconds;
	}
}
