package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class Evaluation extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String solution;
	private int score;

	public Evaluation()
	{
		super();
	}

	public Evaluation( PrincipalImpl owner, String solution, int score )
	{
		super( owner );
		this.solution = solution;
		this.score = score;
	}

	public String getSolution()
	{
		return solution;
	}

	public void setSolution( String solution )
	{
		this.solution = solution;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore( int score )
	{
		this.score = score;
	}

}
