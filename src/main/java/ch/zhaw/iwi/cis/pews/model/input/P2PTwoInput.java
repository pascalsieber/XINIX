package ch.zhaw.iwi.cis.pews.model.input;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class P2PTwoInput extends Input
{
	private String question;
	private List< P2PKeywordInput > cascade1Keywords;

	public P2PTwoInput()
	{
		super();
		this.cascade1Keywords = new ArrayList< P2PKeywordInput >();
	}

	public P2PTwoInput( ExerciseImpl exercise, String question, List< P2PKeywordInput > cascade1Keywords )
	{
		super( exercise );
		this.question = question;
		this.cascade1Keywords = cascade1Keywords;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public List< P2PKeywordInput > getCascade1Keywords()
	{
		return cascade1Keywords;
	}

	public void setCascade1Keywords( List< P2PKeywordInput > cascade1Keywords )
	{
		this.cascade1Keywords = cascade1Keywords;
	}

}
