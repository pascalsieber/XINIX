package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;

@Entity
public class CompressionExercise extends ExerciseImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;

	@ElementCollection
	private List< String > solutionCriteria;

	public CompressionExercise()
	{
		super();
	}

	public CompressionExercise( String name, String description, WorkflowElementTemplate derivedFrom, WorkshopImpl workshop, String question, List< String > solutionCriteria )
	{
		super( name, description, derivedFrom, workshop );
		this.question = question;
		this.solutionCriteria = solutionCriteria;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
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
