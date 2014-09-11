package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.HashSet;
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
	private Set< String > answers;

	@ManyToMany
	private Set< String > selectedKeywords;

	public P2PTwoData()
	{
		super();
		this.selectedKeywords = new HashSet<>();
		this.answers = new HashSet<>();
	}

	public P2PTwoData( PrincipalImpl owner, WorkflowElementImpl workflowElement, Set< String > answers, Set< String > selectedKeywords )
	{
		super( owner, workflowElement );
		this.answers = answers;
		this.selectedKeywords = selectedKeywords;
	}

	public Set< String > getAnswers()
	{
		return answers;
	}

	public void setAnswers( Set< String > answers )
	{
		this.answers = answers;
	}

	public Set< String > getSelectedKeywords()
	{
		return selectedKeywords;
	}

	public void setSelectedKeywords( Set< String > selectedKeywords )
	{
		this.selectedKeywords = selectedKeywords;
	}

}
