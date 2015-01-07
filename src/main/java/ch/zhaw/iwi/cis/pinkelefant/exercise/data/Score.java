package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

//TODO: discuss with John, what the parent class should be

@Embeddable
public class Score extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private int score;

	public Score()
	{
		super();
	}

	public Score( PrincipalImpl owner, int score )
	{
		super( owner );
		this.score = score;
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
