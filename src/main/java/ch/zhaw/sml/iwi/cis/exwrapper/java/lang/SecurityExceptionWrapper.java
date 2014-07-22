package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class SecurityExceptionWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public SecurityExceptionWrapper( Exception e )
	{
		super( e );
	}
}
