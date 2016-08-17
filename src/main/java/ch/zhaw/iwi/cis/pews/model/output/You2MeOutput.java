package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.DialogEntry;

public class You2MeOutput extends Output
{
	private List<DialogEntry> dialog;

	public You2MeOutput()
	{
		super();
	}

	public You2MeOutput( String exerciseID, List<DialogEntry> dialog )
	{
		super( exerciseID );
		this.dialog = dialog;
	}

	public List<DialogEntry> getDialog()
	{
		return dialog;
	}

	public void setDialog( List<DialogEntry> dialog )
	{
		this.dialog = dialog;
	}

}
