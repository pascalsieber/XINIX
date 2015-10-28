package ch.zhaw.iwi.cis.pews.model.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.util.comparator.ExerciseTemplateComparator;

@Entity
public class WorkshopTemplate extends WorkflowElementTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "workshopTemplate" )
	private List< ExerciseTemplate > exerciseTemplates;

	public WorkshopTemplate()
	{
		super();
		this.exerciseTemplates = new ArrayList< ExerciseTemplate >();
	}

	public WorkshopTemplate( PrincipalImpl owner, String name, String description )
	{
		super( owner );
		this.name = name;
		this.description = description;
		this.exerciseTemplates = new ArrayList< ExerciseTemplate >();
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

	public List< ExerciseTemplate > getExerciseTemplates()
	{
		List< ExerciseTemplate > result = exerciseTemplates;
		if ( result.size() > 0 )
		{
			Collections.sort( result, new ExerciseTemplateComparator() );
		}
		
		return result;
	}

	public void setExerciseTemplates( List< ExerciseTemplate > exerciseTemplates )
	{
		this.exerciseTemplates = exerciseTemplates;
	}

}
