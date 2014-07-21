package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixImage;

@Entity
public class XinixDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String question;
	private String keyword;

	@ManyToMany
	private Set< XinixImage > images;

	public XinixDefinition()
	{
		super();
		this.images = new HashSet< XinixImage >();
	}

	

	public XinixDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration, WorkshopDefinitionImpl workshopDefinition, String question, String keyword )
	{
		super( owner, timeUnit, duration, workshopDefinition );
		this.question = question;
		this.keyword = keyword;
		this.images = new HashSet< XinixImage >();
	}



	public String getQuestion()
	{
		return question;
	}

	public void setQuestion( String question )
	{
		this.question = question;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}

	public Set< XinixImage > getImages()
	{
		return images;
	}

	public void setImages( Set< XinixImage > images )
	{
		this.images = images;
	}

}
