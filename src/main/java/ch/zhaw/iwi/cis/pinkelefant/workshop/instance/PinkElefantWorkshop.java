package ch.zhaw.iwi.cis.pinkelefant.workshop.instance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.workshop.template.PinkElefantTemplate;

@Entity
public class PinkElefantWorkshop extends WorkshopImpl
{

	@Transient
	private static final long serialVersionUID = 1L;
	private String problem;
	@Column( length = 10000 )
	private String emailText;

	public PinkElefantWorkshop()
	{
		super();
	}

	public PinkElefantWorkshop( String name, String description, PinkElefantTemplate derivedFrom )
	{
		super( name, description, derivedFrom );
		this.problem = derivedFrom.getProblem();
		this.emailText = derivedFrom.getDefaultEmailText();
	}

	public String getProblem()
	{
		return problem;
	}

	public void setProblem( String problem )
	{
		this.problem = problem;
	}

	public String getEmailText()
	{
		return emailText;
	}

	public void setEmailText( String emailText )
	{
		this.emailText = emailText;
	}

}
