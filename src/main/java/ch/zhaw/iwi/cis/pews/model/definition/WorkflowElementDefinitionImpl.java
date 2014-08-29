package ch.zhaw.iwi.cis.pews.model.definition;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class WorkflowElementDefinitionImpl extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	public WorkflowElementDefinitionImpl()
	{
		super();
	}

	public WorkflowElementDefinitionImpl( PrincipalImpl owner )
	{
		super( owner );
	}
}
