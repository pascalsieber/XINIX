package ch.zhaw.iwi.cis.pews.model.template;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkshopTemplate extends WorkflowElementTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "workshopDefinition" )
	private Set< ExerciseTemplate > exerciseDefinitions;

	public WorkshopTemplate()
	{
		super();
		this.exerciseDefinitions = new HashSet< ExerciseTemplate >();
	}

	public WorkshopTemplate( PrincipalImpl owner, String name, String description )
	{
		super( owner );
		this.name = name;
		this.description = description;
		this.exerciseDefinitions = new HashSet< ExerciseTemplate >();
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

	public Set< ExerciseTemplate > getExerciseDefinitions()
	{
		return exerciseDefinitions;
	}

	public void setExerciseDefinitions( Set< ExerciseTemplate > exerciseDefinitions )
	{
		this.exerciseDefinitions = exerciseDefinitions;
	}

}
