package ch.zhaw.iwi.cis.pinkelefant.workshop.template;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class PinkElefantTemplate extends WorkshopTemplate
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String problem;
	private String defaultEmailText;

	public PinkElefantTemplate()
	{
		super();
	}

	public PinkElefantTemplate( PrincipalImpl owner, String name, String description, String problem, String defaultEmailText )
	{
		super( owner, name, description );
		this.problem = problem;
		this.defaultEmailText = defaultEmailText;
	}

	public String getProblem()
	{
		return problem;
	}

	public void setProblem( String problem )
	{
		this.problem = problem;
	}

	public String getDefaultEmailText()
	{
		return defaultEmailText;
	}

	public void setDefaultEmailText( String defaultEmailText )
	{
		this.defaultEmailText = defaultEmailText;
	}

}
