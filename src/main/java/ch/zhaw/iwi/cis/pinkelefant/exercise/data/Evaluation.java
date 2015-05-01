package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class Evaluation extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CompressionExerciseDataElement solution;

	@Embedded
	private Score score;

	public Evaluation()
	{
		super();
	}

	public Evaluation( PrincipalImpl owner, CompressionExerciseDataElement solution, Score score )
	{
		super( owner );
		this.solution = solution;
		this.score = score;
	}

	public CompressionExerciseDataElement getSolution()
	{
		return solution;
	}

	public void setSolution( CompressionExerciseDataElement solution )
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
}
