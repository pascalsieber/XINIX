package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class CompressionData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String title;
	private String description;

	public CompressionData()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public CompressionData( Client client, PrincipalImpl owner, WorkflowElementImpl workflowElement, String title, String description )
	{
		super( client, owner, workflowElement );
		this.title = title;
		this.description = description;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

}
