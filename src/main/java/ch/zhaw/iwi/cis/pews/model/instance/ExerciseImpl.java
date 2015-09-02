package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.template.WorkflowElementTemplate;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class ExerciseImpl extends WorkflowElementImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private Integer orderInWorkshop = null;

	@ManyToOne
	private WorkshopImpl workshop;

	@ManyToMany( cascade = CascadeType.ALL )
	private Set< PrincipalImpl > participants;

	private String question;

	public ExerciseImpl()
	{
		super();
		this.participants = new HashSet< PrincipalImpl >();
	}

	public ExerciseImpl( String name, String description, WorkflowElementTemplate derivedFrom, Integer orderInWorkshop, WorkshopImpl workshop, Set< PrincipalImpl > participants, String question )
	{
		super( name, description, derivedFrom );
		this.orderInWorkshop = orderInWorkshop;
		this.workshop = workshop;
		this.participants = participants;
		this.question = question;
	}

	public WorkshopImpl getWorkshop()
	{
		return workshop;
	}

	public void setWorkshop( WorkshopImpl workshop )
	{
		this.workshop = workshop;
	}

	public Set< PrincipalImpl > getParticipants()
	{
		return participants;
	}

	public void setParticipants( Set< PrincipalImpl > participants )
	{
		this.participants = participants;
	}

	public Integer getOrderInWorkshop()
	{
		return orderInWorkshop;
	}

	public void setOrderInWorkshop( Integer orderInWorkshop )
	{
		this.orderInWorkshop = orderInWorkshop;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

}
