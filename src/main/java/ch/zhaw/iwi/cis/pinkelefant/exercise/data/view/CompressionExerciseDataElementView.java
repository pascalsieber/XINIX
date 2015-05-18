package ch.zhaw.iwi.cis.pinkelefant.exercise.data.view;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;

/**
 * 
 * this is not a persistent entity. this class is merely used as a "view object" to include information of classes CompressionExerciseData and CompressionexerciseDataElement.
 * 
 * Please note, this is a quick and dirty workaround for the iPad client and needs to be cleaned up at some point!!!
 *
 */

public class CompressionExerciseDataElementView extends ExerciseDataImpl
{
	private static final long serialVersionUID = 1L;
	private String solution;
	private String description;
	private CompressionExerciseData compressionExerciseData;

	public CompressionExerciseDataElementView()
	{
		super();
	}

	public CompressionExerciseDataElementView( PrincipalImpl owner, WorkflowElementImpl workflowElement, String solution, String description, CompressionExerciseData compressionExerciseData )
	{
		super( owner, workflowElement );
		this.solution = solution;
		this.description = description;
		this.compressionExerciseData = compressionExerciseData;
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

	public CompressionExerciseData getCompressionExerciseData()
	{
		return compressionExerciseData;
	}

	public void setCompressionExerciseData( CompressionExerciseData compressionExerciseData )
	{
		this.compressionExerciseData = compressionExerciseData;
	}

}
