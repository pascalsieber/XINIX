package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class You2MeData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String questionOne;
	private String questionTwo;
	private String responseOne;
	private String responseTwo;

	public You2MeData()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public You2MeData( PrincipalImpl owner, WorkflowElementImpl workflowElement, String questionOne, String questionTwo, String responseOne, String responseTwo )
	{
		super( owner, workflowElement );
		this.questionOne = questionOne;
		this.questionTwo = questionTwo;
		this.responseOne = responseOne;
		this.responseTwo = responseTwo;
	}

	public String getQuestionOne()
	{
		return questionOne;
	}

	public void setQuestionOne( String questionOne )
	{
		this.questionOne = questionOne;
	}

	public String getQuestionTwo()
	{
		return questionTwo;
	}

	public void setQuestionTwo( String questionTwo )
	{
		this.questionTwo = questionTwo;
	}

	public String getResponseOne()
	{
		return responseOne;
	}

	public void setResponseOne( String responseOne )
	{
		this.responseOne = responseOne;
	}

	public String getResponseTwo()
	{
		return responseTwo;
	}

	public void setResponseTwo( String responseTwo )
	{
		this.responseTwo = responseTwo;
	}

}
