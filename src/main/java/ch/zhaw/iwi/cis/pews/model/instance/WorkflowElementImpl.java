package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.data.WorkflowElementDataImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class WorkflowElementImpl extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	@OneToMany
	private Set< WorkflowElementStatusHistroyElementImpl > statusHistory;

	private String currentState;

	@ManyToOne
	private WorkflowElementDefinitionImpl definition;

	@OneToMany( cascade = CascadeType.ALL )
	private Set< WorkflowElementDataImpl > data;

	public WorkflowElementImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkflowElementImpl( String name, String description, WorkflowElementDefinitionImpl definition )
	{
		super();
		this.name = name;
		this.description = description;
		this.statusHistory = new HashSet< WorkflowElementStatusHistroyElementImpl >();
		this.definition = definition;
		this.data = new HashSet< WorkflowElementDataImpl >();
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

	public Set< WorkflowElementStatusHistroyElementImpl > getStatusHistory()
	{
		return statusHistory;
	}

	public void setStatusHistory( Set< WorkflowElementStatusHistroyElementImpl > statusHistory )
	{
		this.statusHistory = statusHistory;
	}

	public String getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState( String currentState )
	{
		this.currentState = currentState;
	}

	public WorkflowElementDefinitionImpl getDefinition()
	{
		return definition;
	}

	public void setDefinition( WorkflowElementDefinitionImpl definition )
	{
		this.definition = definition;
	}

	public Set< WorkflowElementDataImpl > getData()
	{
		return data;
	}

	public void setData( Set< WorkflowElementDataImpl > data )
	{
		this.data = data;
	}

}
