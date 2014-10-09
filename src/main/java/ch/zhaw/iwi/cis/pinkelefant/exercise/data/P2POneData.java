package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2POneData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToMany( cascade = CascadeType.ALL )
	private List< P2POneKeyword > keywords;

	public P2POneData()
	{
		super();
		this.keywords = new ArrayList< P2POneKeyword >();
	}

	public P2POneData( PrincipalImpl owner, WorkflowElementImpl workflowElement, List< String > keywordStrings )
	{
		super( owner, workflowElement );
		this.keywords = new ArrayList< P2POneKeyword >();

		for ( String string : keywordStrings )
		{
			keywords.add( new P2POneKeyword( owner, string ) );
		}
	}

	public List< P2POneKeyword > getKeywords()
	{
		return keywords;
	}

	public void setKeywords( List< P2POneKeyword > keywords )
	{
		this.keywords = keywords;
	}

}
