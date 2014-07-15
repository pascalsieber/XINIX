package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;

@Entity
public class WorkshopImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set< SessionImpl > sessions;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List< ExerciseImpl > exercises;

	public WorkshopImpl()
	{
		super();
		this.sessions = new HashSet<SessionImpl>();
		this.exercises = new ArrayList<ExerciseImpl>();
	}

	public WorkshopImpl( String name, String description, WorkflowElementDefinitionImpl definition )
	{
		super( name, description, definition );
		this.sessions = new HashSet<SessionImpl>();
		this.exercises = new ArrayList<ExerciseImpl>();
	}
	
	

}
