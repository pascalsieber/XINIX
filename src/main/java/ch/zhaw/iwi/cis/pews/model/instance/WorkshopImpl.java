package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;

@Entity
public class WorkshopImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "workshop" )
	private Set< SessionImpl > sessions;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "workshop" )
	private List< ExerciseImpl > exercises;

	public WorkshopImpl()
	{
		super();
		this.sessions = new HashSet< SessionImpl >();
		this.exercises = new ArrayList< ExerciseImpl >();
	}

	public WorkshopImpl( Client client, String name, String description, WorkflowElementDefinitionImpl definition )
	{
		super( client, name, description, definition );
		this.sessions = new HashSet< SessionImpl >();
		this.exercises = new ArrayList< ExerciseImpl >();
	}

	public Set< SessionImpl > getSessions()
	{
		return sessions;
	}

	public void setSessions( Set< SessionImpl > sessions )
	{
		this.sessions = sessions;
	}

	public List< ExerciseImpl > getExercises()
	{
		List< ExerciseImpl > result = exercises;
		if ( result.size() > 0 )
		{
			Collections.sort( result, new ExerciseImplComparator() );
		}

		return result;
	}

	public void setExercises( List< ExerciseImpl > exercises )
	{
		this.exercises = exercises;
	}

}
