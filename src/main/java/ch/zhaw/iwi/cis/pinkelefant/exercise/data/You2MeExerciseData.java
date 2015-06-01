package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class You2MeExerciseData extends CompressableExerciseData
{
	@Transient
	private static final long serialVersionUID = 1L;

	@OneToMany( cascade = CascadeType.ALL )
	@OrderColumn
	private List< DialogEntry > dialog;

	public You2MeExerciseData()
	{
		super();
		this.dialog = new ArrayList<>();
	}

	public You2MeExerciseData( PrincipalImpl owner, WorkflowElementImpl workflowElement, List< DialogEntry > dialog )
	{
		super( owner, workflowElement );
		this.dialog = dialog;
	}

	public List< DialogEntry > getDialog()
	{
		return dialog;
	}

	public void setDialog( List< DialogEntry > dialog )
	{
		this.dialog = dialog;
	}

}
