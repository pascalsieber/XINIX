package ch.zhaw.iwi.cis.pews.model.template;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class WorkflowElementTemplate extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	public WorkflowElementTemplate()
	{
		super();
	}

	public WorkflowElementTemplate( PrincipalImpl owner )
	{
		super( owner );
	}
}
