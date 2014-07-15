package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SessionImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private WorkshopImpl workshop;

	@ManyToOne
	private ExerciseImpl currentExercise;

	@ManyToMany
	private Set< PrincipalImpl > participants;

	@ManyToMany
	private Set< PrincipalImpl > acceptees;

	@ManyToMany
	private Set< PrincipalImpl > invitees;

	@ManyToMany
	private Set< PrincipalImpl > executers;

	public SessionImpl()
	{
		super();
		this.participants = new HashSet< PrincipalImpl >();
		this.acceptees = new HashSet< PrincipalImpl >();
		this.invitees = new HashSet< PrincipalImpl >();
		this.executers = new HashSet< PrincipalImpl >();
	}

	public SessionImpl( String name, String description, WorkflowElementDefinitionImpl definition )
	{
		super( name, description, definition );
		this.participants = new HashSet< PrincipalImpl >();
		this.acceptees = new HashSet< PrincipalImpl >();
		this.invitees = new HashSet< PrincipalImpl >();
		this.executers = new HashSet< PrincipalImpl >();
	}

	public WorkshopImpl getWorkshop()
	{
		return workshop;
	}

	public void setWorkshop( WorkshopImpl workshop )
	{
		this.workshop = workshop;
	}

	public ExerciseImpl getCurrentExercise()
	{
		return currentExercise;
	}

	public void setCurrentExercise( ExerciseImpl currentExercise )
	{
		this.currentExercise = currentExercise;
	}

	public Set< PrincipalImpl > getParticipants()
	{
		return participants;
	}

	public void setParticipants( Set< PrincipalImpl > participants )
	{
		this.participants = participants;
	}

	public Set< PrincipalImpl > getAcceptees()
	{
		return acceptees;
	}

	public void setAcceptees( Set< PrincipalImpl > acceptees )
	{
		this.acceptees = acceptees;
	}

	public Set< PrincipalImpl > getInvitees()
	{
		return invitees;
	}

	public void setInvitees( Set< PrincipalImpl > invitees )
	{
		this.invitees = invitees;
	}

	public Set< PrincipalImpl > getExecuters()
	{
		return executers;
	}

	public void setExecuters( Set< PrincipalImpl > executers )
	{
		this.executers = executers;
	}

}
