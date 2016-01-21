package ch.zhaw.iwi.cis.pews.model.input;

import javax.persistence.Lob;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

//@Entity
public class CachedInput extends WorkshopObject
{

	@Transient
	private static final long serialVersionUID = 1L;

	private String sessionID;

	@Lob
	private byte[] inputBlob;

	public CachedInput()
	{
		super();
	}

	public CachedInput( String sessionID, byte[] inputBlob )
	{
		super();
		this.sessionID = sessionID;
		this.inputBlob = inputBlob;
	}

	public String getSessionID()
	{
		return sessionID;
	}

	public void setSessionID( String sessionID )
	{
		this.sessionID = sessionID;
	}

	public byte[] getInputBlob()
	{
		return inputBlob;
	}

	public void setInputBlob( byte[] inputBlob )
	{
		this.inputBlob = inputBlob;
	}
}
