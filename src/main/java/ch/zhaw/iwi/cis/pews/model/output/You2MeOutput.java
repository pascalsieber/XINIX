package ch.zhaw.iwi.cis.pews.model.output;

import java.util.List;

public class You2MeOutput extends Output
{
	private List< DialogEntry > dialogEntries;

	public You2MeOutput()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public You2MeOutput( List< DialogEntry > dialogEntries )
	{
		super();
		this.dialogEntries = dialogEntries;
	}

	public List< DialogEntry > getDialogEntries()
	{
		return dialogEntries;
	}

	public void setDialogEntries( List< DialogEntry > dialogEntries )
	{
		this.dialogEntries = dialogEntries;
	}

}
