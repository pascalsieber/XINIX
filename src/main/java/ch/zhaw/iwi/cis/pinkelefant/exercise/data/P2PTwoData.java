package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class P2PTwoData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ElementCollection
	private List< String > answers;

	@ManyToMany
	private Set< P2POneKeyword > selectedKeywords;

	public P2PTwoData()
	{
		super();
		this.selectedKeywords = new HashSet<>();
		this.answers = new ArrayList<>();
	}

	public P2PTwoData( PrincipalImpl owner, WorkflowElementImpl workflowElement, List< String > answers, Set< P2POneKeyword > selectedKeywords )
	{
		super( owner, workflowElement );
		this.answers = answers;
		this.selectedKeywords = selectedKeywords;
	}

	public List< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( List< String > answers )
	{
		this.answers = answers;
	}

	public Set< P2POneKeyword > getSelectedKeywords()
	{
		return selectedKeywords;
	}

	public void setSelectedKeywords( Set< P2POneKeyword > selectedKeywords )
	{
		this.selectedKeywords = selectedKeywords;
	}

}
