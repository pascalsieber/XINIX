package ch.zhaw.iwi.cis.pews.service.wrapper;

public class FindAllWrapper
{
	private Class< ? > clazz;
	private String clientID;

	public FindAllWrapper()
	{
		super();
	}

	public FindAllWrapper( Class< ? > clazz, String clientID )
	{
		super();
		this.clazz = clazz;
		this.clientID = clientID;
	}

	public Class< ? > getClazz()
	{
		return clazz;
	}

	public void setClazz( Class< ? > clazz )
	{
		this.clazz = clazz;
	}

	public String getClientID()
	{
		return clientID;
	}

	public void setClientID( String clientID )
	{
		this.clientID = clientID;
	}

}
