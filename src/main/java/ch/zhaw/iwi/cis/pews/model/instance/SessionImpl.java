package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SessionImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private WorkshopImpl workshop;

	@ManyToOne( cascade = CascadeType.ALL )
	private ExerciseImpl currentExercise;

	@OneToMany( cascade = CascadeType.ALL )
	private Set< PrincipalImpl > participants;

	@ManyToMany
	private Set< PrincipalImpl > acceptees;

	@OneToMany( cascade = CascadeType.ALL )
	private Set< Invitation > invitations;

	@ManyToMany( cascade = CascadeType.ALL )
	private Set< PrincipalImpl > executers;

	public SessionImpl()
	{
		super();
		this.participants = new HashSet< PrincipalImpl >();
		this.acceptees = new HashSet< PrincipalImpl >();
		this.invitations = new HashSet< Invitation >();
		this.executers = new HashSet< PrincipalImpl >();
	}

	public SessionImpl( String name, String description, WorkflowElementDefinitionImpl definition, WorkshopImpl workshop )
	{
		super( name, description, definition );
		this.participants = new HashSet< PrincipalImpl >();
		this.acceptees = new HashSet< PrincipalImpl >();
		this.invitations = new HashSet< Invitation >();
		this.executers = new HashSet< PrincipalImpl >();
		this.workshop = workshop;
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
		if ( currentExercise == null && workshop.getExercises().size() > 0 )
		{
			setCurrentExercise( getWorkshop().getExercises().get( 0 ) );
		}
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

	public Set< Invitation > getInvitations()
	{
		return invitations;
	}

	public void setInvitations( Set< Invitation > invitations )
	{
		this.invitations = invitations;
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
