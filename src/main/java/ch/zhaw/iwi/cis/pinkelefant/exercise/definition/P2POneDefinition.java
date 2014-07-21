package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String picture;
	private String theme;

	public P2POneDefinition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	

	public P2POneDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, String picture, String theme )
	{
		super( owner, timeUnit, duration, workshopDefinition );
		this.picture = picture;
		this.theme = theme;
	}



	public String getPicture()
	{
		return picture;
	}

	public void setPicture( String picture )
	{
		this.picture = picture;
	}

	public String getTheme()
	{
		return theme;
	}

	public void setTheme( String theme )
	{
		this.theme = theme;
	}

}
