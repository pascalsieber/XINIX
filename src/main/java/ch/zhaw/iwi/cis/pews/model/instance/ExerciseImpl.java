package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class ExerciseImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private WorkshopImpl workshop;

	@ManyToMany( cascade = CascadeType.ALL )
	private Set< PrincipalImpl > participants;

	public ExerciseImpl()
	{
		super();
		this.participants = new HashSet< PrincipalImpl >();
	}

	public ExerciseImpl( String name, String description, WorkflowElementDefinitionImpl definition, WorkshopImpl workshop )
	{
		super( name, description, definition );
		this.workshop = workshop;
		this.participants = new HashSet< PrincipalImpl >();
	}

	public WorkshopImpl getWorkshop()
	{
		return workshop;
	}

	public void setWorkshop( WorkshopImpl workshop )
	{
		this.workshop = workshop;
	}

	public Set< PrincipalImpl > getParticipants()
	{
		return participants;
	}

	public void setParticipants( Set< PrincipalImpl > participants )
	{
		this.participants = participants;
	}

}
