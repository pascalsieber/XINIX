package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;

public class You2MeOutput extends Output
{
	private List< DialogEntry > dialog;

	public You2MeOutput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public You2MeOutput( List< DialogEntry > dialog )
	{
		super();
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
