package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SessionImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@Enumerated( EnumType.STRING )
	private SessionSynchronizationImpl synchronization;

	@ManyToOne
	private WorkshopImpl workshop;

	@ManyToOne
	private ExerciseImpl currentExercise;

	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
	private Set< Participant > participants;

	@OneToMany( cascade = CascadeType.ALL )
	private Set< Invitation > invitations;

	public SessionImpl()
	{
		super();
		this.participants = new HashSet< Participant >();
		this.invitations = new HashSet< Invitation >();
		this.currentExercise = null;
	}

	public SessionImpl(
			String name,
			String description,
			WorkflowElementTemplate derivedFrom,
			SessionSynchronizationImpl synchronization,
			WorkshopImpl workshop,
			ExerciseImpl currentExercise,
			Set< Participant > participants,
			Set< PrincipalImpl > acceptees,
			Set< Invitation > invitations,
			Set< PrincipalImpl > executers )
	{
		super( name, description, derivedFrom );
		this.synchronization = synchronization;
		this.workshop = workshop;
		this.currentExercise = currentExercise;
		this.participants = participants;
		this.invitations = invitations;
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
		// need this in case we operate on a non-persisted session object
		// for example in SessionServiceImpl.setCurrentExercise() an incomplete session object
		// is used which is constructed as part of the REST service call
		if ( currentExercise != null )
		{
			return currentExercise;
		}

		// if session has no workshop, there can be no exercises
		if ( workshop == null )
		{
			return null;
		}

		// if no currentExercise is set, we assume that session has not kicked off,
		// so we set the first exercise defined in the session's workshop as
		// the currentExercise
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

	public Set< Participant > getParticipants()
	{
		return participants;
	}

	public void setParticipants( Set< Participant > participants )
	{
		this.participants = participants;
	}

	public Set< Invitation > getInvitations()
	{
		return invitations;
	}

	public void setInvitations( Set< Invitation > invitations )
	{
		this.invitations = invitations;
	}

	public SessionSynchronizationImpl getSynchronization()
	{
		return synchronization;
	}

	public void setSynchronization( SessionSynchronizationImpl synchronization )
	{
		this.synchronization = synchronization;
	}

}
