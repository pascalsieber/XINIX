package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

@Entity
public class CompressionExerciseDataElement extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String solution;
	private String description;

	public CompressionExerciseDataElement()
	{
		super();
	}

	public CompressionExerciseDataElement( String solution, String description )
	{
		super();
		this.solution = solution;
		this.description = description;
	}

	public String getSolution()
	{
		return solution;
	}

	public void setSolution( String solution )
	{
		this.solution = solution;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

}
