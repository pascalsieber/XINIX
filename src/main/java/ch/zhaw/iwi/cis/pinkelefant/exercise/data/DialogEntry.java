package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.output.DialogRole;

@Entity
public class DialogEntry extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@Enumerated( EnumType.STRING )
	private DialogRole role;

	private String text;
	private Integer orderInDialog = null;

	public DialogEntry()
	{
		super();
	}

	public DialogEntry( DialogRole role, String text )
	{
		super();
		this.role = role;
		this.text = text;
	}

	public DialogRole getRole()
	{
		return role;
	}

	public void setRole( DialogRole role )
	{
		this.role = role;
	}

	public String getText()
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}

	public Integer getOrderInDialog()
	{
		return orderInDialog;
	}

	public void setOrderInDialog( Integer orderInDialog )
	{
		this.orderInDialog = orderInDialog;
	}

}
