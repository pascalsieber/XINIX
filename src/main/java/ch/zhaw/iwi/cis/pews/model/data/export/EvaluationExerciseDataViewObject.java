package ch.zhaw.iwi.cis.pews.model.data.export;

public class EvaluationExerciseDataViewObject extends ExerciseDataViewObject
{
	private boolean evaluated;
	private String solution;
	private String description;
	private int numeberOfVotes;
	private double averageScore;
	private double weightedScore;

	public EvaluationExerciseDataViewObject()
	{
	}

	public EvaluationExerciseDataViewObject(
			String id,
			String workshopID,
			String workshopName,
			String exerciseID,
			String exerciseName,
			String exerciseQuestion,
			String ownerID,
			String ownerLoginName,
			boolean evaluated,
			String solution,
			String description,
			int numeberOfVotes,
			double averageScore,
			double weightedScore )
	{
		super( id, workshopID, workshopName, exerciseID, exerciseName, exerciseQuestion, ownerID, ownerLoginName );
		this.evaluated = evaluated;
		this.solution = solution;
		this.description = description;
		this.numeberOfVotes = numeberOfVotes;
		this.averageScore = averageScore;
		this.weightedScore = weightedScore;
	}

	public boolean isEvaluated()
	{
		return evaluated;
	}

	public void setEvaluated( boolean evaluated )
	{
		this.evaluated = evaluated;
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

	public int getNumeberOfVotes()
	{
		return numeberOfVotes;
	}

	public void setNumeberOfVotes( int numeberOfVotes )
	{
		this.numeberOfVotes = numeberOfVotes;
	}

	public double getAverageScore()
	{
		return averageScore;
	}

	public void setAverageScore( double averageScore )
	{
		this.averageScore = averageScore;
	}

	public double getWeightedScore()
	{
		return weightedScore;
	}

	public void setWeightedScore( double weightedScore )
	{
		this.weightedScore = weightedScore;
	}

}
