package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2PTwoData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String answer;

	@ManyToOne
	private P2POneData post2paperOneDataOne;

	@ManyToOne
	private P2POneData post2paperOneDataTwo;

	public P2PTwoData()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public P2PTwoData( PrincipalImpl owner, WorkflowElementImpl workflowElement, String answer, P2POneData post2paperOneDataOne, P2POneData post2paperOneDataTwo )
	{
		super( owner, workflowElement );
		this.post2paperOneDataOne = post2paperOneDataOne;
		this.post2paperOneDataTwo = post2paperOneDataTwo;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer( String answer )
	{
		this.answer = answer;
	}

	public P2POneData getPost2paperOneDataOne()
	{
		return post2paperOneDataOne;
	}

	public void setPost2paperOneDataOne( P2POneData post2paperOneDataOne )
	{
		this.post2paperOneDataOne = post2paperOneDataOne;
	}

	public P2POneData getPost2paperOneDataTwo()
	{
		return post2paperOneDataTwo;
	}

	public void setPost2paperOneDataTwo( P2POneData post2paperOneDataTwo )
	{
		this.post2paperOneDataTwo = post2paperOneDataTwo;
	}

}
