package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class CompressionExerciseData extends ExerciseDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List< CompressionExerciseDataElement > solutions;

	public CompressionExerciseData()
	{
		super();
		this.solutions = new ArrayList<>();
	}

	public CompressionExerciseData( PrincipalImpl owner, WorkflowElementImpl workflowElement, List< CompressionExerciseDataElement > solutions )
	{
		super( owner, workflowElement );
		this.solutions = solutions;
	}

	public List< CompressionExerciseDataElement > getSolutions()
	{
		return solutions;
	}

	public void setSolutions( List< CompressionExerciseDataElement > solutions )
	{
		this.solutions = solutions;
	}

}
