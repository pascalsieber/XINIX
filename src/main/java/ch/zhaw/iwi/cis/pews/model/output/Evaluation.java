package ch.zhaw.iwi.cis.pews.model.output;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;

@Entity
public class Evaluation extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String solution;
	
	@Embedded
	private Score score;

	@ManyToOne
	private EvaluationExerciseData evaluationExerciseDataObject;

	public Evaluation()
	{
		super();
	}

	public Evaluation( PrincipalImpl owner, String solution, Score score, EvaluationExerciseData evaluationExerciseDataObject )
	{
		super( owner );
		this.solution = solution;
		this.score = score;
		this.evaluationExerciseDataObject = evaluationExerciseDataObject;
	}

	public String getSolution()
	{
		return solution;
	}

	public void setSolution( String solution )
	{
		this.solution = solution;
	}

	public Score getScore()
	{
		return score;
	}

	public void setScore( Score score )
	{
		this.score = score;
	}

	public EvaluationExerciseData getEvaluationExerciseDataObject()
	{
		return evaluationExerciseDataObject;
	}

	public void setEvaluationExerciseDataObject( EvaluationExerciseData evaluationExerciseDataObject )
	{
		this.evaluationExerciseDataObject = evaluationExerciseDataObject;
	}

}
