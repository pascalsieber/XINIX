package ch.zhaw.iwi.cis.pinkelefant.workshop.instance;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkflowElementDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

@Entity
public class PinkElefantWorkshop extends WorkshopImpl
{

	@Transient
	private static final long serialVersionUID = 1L;
	private String problem;

	public PinkElefantWorkshop()
	{
		super();
	}

	public PinkElefantWorkshop( String name, String description, WorkflowElementDefinitionImpl derivedFrom, String problem )
	{
		super( name, description, derivedFrom );
		this.problem = problem;
	}

	public String getProblem()
	{
		return problem;
	}

	public void setProblem( String problem )
	{
		this.problem = problem;
	}

}
