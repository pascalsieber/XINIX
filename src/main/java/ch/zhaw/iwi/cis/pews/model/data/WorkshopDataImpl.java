package ch.zhaw.iwi.cis.pews.model.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkshopDataImpl extends WorkflowElementDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public WorkshopDataImpl()
	{
		super();
	}

	public WorkshopDataImpl( PrincipalImpl owner, WorkflowElementImpl workflowElement )
	{
		super( owner, workflowElement );
	}

}
