package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.CompressionTemplate;

@Entity
public class CompressionExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection
	private List< String > solutionCriteria;

	public CompressionExercise()
	{
		super();
	}

	public CompressionExercise( String name, String description, CompressionTemplate derivedFrom, WorkshopImpl workshop )
	{
		super( name, description, derivedFrom, workshop );
		this.solutionCriteria = derivedFrom.getSolutionCriteria();
	}

	public List< String > getSolutionCriteria()
	{
		return solutionCriteria;
	}

	public void setSolutionCriteria( List< String > solutionCriteria )
	{
		this.solutionCriteria = solutionCriteria;
	}

}
