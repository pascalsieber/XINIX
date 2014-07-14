package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class NoSuchMethodExceptionWrapper extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public NoSuchMethodExceptionWrapper( Exception e )
	{
		super( e );
	}
}
