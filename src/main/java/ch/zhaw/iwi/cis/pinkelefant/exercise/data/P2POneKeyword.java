package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneKeyword extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String keyword;

	public P2POneKeyword()
	{
		super();
	}

	public P2POneKeyword( PrincipalImpl owner, String keyword )
	{
		super( owner );
		this.keyword = keyword;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}

}
