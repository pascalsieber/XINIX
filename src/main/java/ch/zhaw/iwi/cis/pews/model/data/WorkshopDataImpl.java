package ch.zhaw.iwi.cis.pews.model.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class WorkshopDataImpl extends WorkflowElementDataImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public WorkshopDataImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkshopDataImpl( PrincipalImpl owner )
	{
		super( owner );
		// TODO Auto-generated constructor stub
	}

}
