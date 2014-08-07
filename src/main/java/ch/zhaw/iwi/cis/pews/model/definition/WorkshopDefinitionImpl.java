package ch.zhaw.iwi.cis.pews.model.definition;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkshopDefinitionImpl extends WorkflowElementDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	@OneToMany( cascade = CascadeType.ALL )
	private Set< ExerciseDefinitionImpl > exerciseDefinitions;

	public WorkshopDefinitionImpl()
	{
		super();
		this.exerciseDefinitions = new HashSet< ExerciseDefinitionImpl >();
	}

	public WorkshopDefinitionImpl( Client client, PrincipalImpl owner, String name, String description )
	{
		super( client, owner );
		this.name = name;
		this.description = description;
		this.exerciseDefinitions = new HashSet< ExerciseDefinitionImpl >();
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

	public Set< ExerciseDefinitionImpl > getExerciseDefinitions()
	{
		return exerciseDefinitions;
	}

	public void setExerciseDefinitions( Set< ExerciseDefinitionImpl > exerciseDefinitions )
	{
		this.exerciseDefinitions = exerciseDefinitions;
	}

}
