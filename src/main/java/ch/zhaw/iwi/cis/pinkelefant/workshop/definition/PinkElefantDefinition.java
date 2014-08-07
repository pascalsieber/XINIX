package ch.zhaw.iwi.cis.pinkelefant.workshop.definition;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkElefantDefinition extends WorkshopDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String problem;

	public PinkElefantDefinition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public PinkElefantDefinition( Client client, PrincipalImpl owner, String name, String description, String problem )
	{
		super( client, owner, name, description );
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
