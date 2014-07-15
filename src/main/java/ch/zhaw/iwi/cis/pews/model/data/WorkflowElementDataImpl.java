package ch.zhaw.iwi.cis.pews.model.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkflowElementDataImpl extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	public WorkflowElementDataImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkflowElementDataImpl( PrincipalImpl owner )
	{
		super( owner );
		// TODO Auto-generated constructor stub
	}

}
