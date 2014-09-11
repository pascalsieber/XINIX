package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SimplePrototypingData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	@Lob
	private byte[] blob;

	public SimplePrototypingData()
	{
		super();
	}

	public SimplePrototypingData( PrincipalImpl owner, WorkflowElementImpl workflowElement, byte[] blob )
	{
		super( owner, workflowElement );
		this.blob = blob;
	}

	public byte[] getBlob()
	{
		return blob;
	}

	public void setBlob( byte[] blob )
	{
		this.blob = blob;
	}

}
