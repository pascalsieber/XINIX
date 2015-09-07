package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressableExerciseData;

public class CompressionInput extends Input
{
	private String question;
	private List< String > solutionCriteria;
	private List< CompressableExerciseData > compressableExerciseData;

	public CompressionInput()
	{
		super();
		this.solutionCriteria = new ArrayList<>();
		this.compressableExerciseData = new ArrayList<>();
	}

	public CompressionInput( ExerciseImpl exercise, String question, List< String > solutionCriteria, List< CompressableExerciseData > compressableExerciseData )
	{
		super( exercise );
		this.question = question;
		this.solutionCriteria = solutionCriteria;
		this.compressableExerciseData = compressableExerciseData;
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

	public List< CompressableExerciseData > getCompressableExerciseData()
	{
		return compressableExerciseData;
	}

	public void setCompressableExerciseData( List< CompressableExerciseData > compressableExerciseData )
	{
		this.compressableExerciseData = compressableExerciseData;
	}

}
