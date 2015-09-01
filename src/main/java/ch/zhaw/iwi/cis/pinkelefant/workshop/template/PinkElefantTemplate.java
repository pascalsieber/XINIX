package ch.zhaw.iwi.cis.pinkelefant.workshop.template;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkElefantTemplate extends WorkshopDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String problem;

	public PinkElefantTemplate()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public PinkElefantTemplate( PrincipalImpl owner, String name, String description, String problem )
	{
		super( owner, name, description );
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
