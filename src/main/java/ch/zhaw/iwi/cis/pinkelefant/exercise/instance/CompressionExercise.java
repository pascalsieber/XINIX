package ch.zhaw.iwi.cis.pinkelefant.exercise.instance;

import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

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

	public CompressionExercise(
			String name,
			String description,
			WorkflowElementTemplate derivedFrom,
			Integer orderInWorkshop,
			WorkshopImpl workshop,
			Set< PrincipalImpl > participants,
			String question,
			List< String > solutionCriteria )
	{
		super( name, description, derivedFrom, orderInWorkshop, workshop, participants, question );
		this.solutionCriteria = solutionCriteria;
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
