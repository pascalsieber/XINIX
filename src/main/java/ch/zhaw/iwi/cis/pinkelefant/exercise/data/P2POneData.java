package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String keyword;

	public P2POneData()
	{
		super();
	}

	public P2POneData( PrincipalImpl owner, WorkflowElementImpl workflowElement, String keyword )
	{
		super( owner, workflowElement );
		this.keyword = keyword;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}

}
