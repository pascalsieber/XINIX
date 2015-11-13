package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class SimplePrototypingData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;
	
	@OneToOne( cascade = CascadeType.ALL )
	private MediaObject mediaObject;

	public SimplePrototypingData()
	{
		super();
	}

	public SimplePrototypingData( PrincipalImpl owner, WorkflowElementImpl workflowElement, MediaObject mediaObject )
	{
		super( owner, workflowElement );
		this.mediaObject = mediaObject;
	}

	public MediaObject getMediaObject()
	{
		return mediaObject;
	}

	public void setMediaObject( MediaObject mediaObject )
	{
		this.mediaObject = mediaObject;
	}

}
